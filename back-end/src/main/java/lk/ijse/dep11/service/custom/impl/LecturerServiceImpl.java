package lk.ijse.dep11.service.custom.impl;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import lk.ijse.dep11.entity.Lecturer;
import lk.ijse.dep11.entity.LinkedIn;
import lk.ijse.dep11.entity.Picture;
import lk.ijse.dep11.exception.AppException;
import lk.ijse.dep11.repository.LecturerRepository;
import lk.ijse.dep11.repository.LinkedInRepository;
import lk.ijse.dep11.repository.PictureRepository;
import lk.ijse.dep11.service.custom.LecturerService;
import lk.ijse.dep11.service.util.Transformer;
import lk.ijse.dep11.to.LecturerTO;
import lk.ijse.dep11.to.requestTO.LecturerReqTO;
import lk.ijse.dep11.util.LecturerType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional
public class LecturerServiceImpl implements LecturerService {

    private final LecturerRepository lecturerRepository;
    private final LinkedInRepository linkedInRepository;
    private final PictureRepository pictureRepository;
    private final Transformer transformer;
    private final Bucket bucket;

    public LecturerServiceImpl(LecturerRepository lecturerRepository, LinkedInRepository linkedInRepository, PictureRepository pictureRepository, Transformer transformer, Bucket bucket) {
        this.lecturerRepository = lecturerRepository;
        this.linkedInRepository = linkedInRepository;
        this.pictureRepository = pictureRepository;
        this.transformer = transformer;
        this.bucket = bucket;
    }

    @Override
    public LecturerTO saveLecturer(LecturerReqTO lecturerReqTO){
        Lecturer lecturer = transformer.toLecturer(lecturerReqTO);

        lecturerRepository.save(lecturer);
        if (lecturerReqTO.getLinkedIn() != null) linkedInRepository.save(lecturer.getLinkedIn());

        String signUrl = null;
        if (lecturerReqTO.getPicture() !=null){
            Picture picture = new Picture(lecturer, "lecturers/" + lecturer.getId());
            pictureRepository.save(picture);
//            lecturer.setPicture(picture);
            Blob blobRef = null;
            try {
                 blobRef= bucket.create(picture.getPictureUrl(), lecturerReqTO.getPicture().getInputStream(), lecturerReqTO.getPicture().getContentType());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            signUrl = blobRef.signUrl(10, TimeUnit.MINUTES, Storage.SignUrlOption.withV4Signature()).toString();
        }
        LecturerTO lecturerTO = transformer.fromLecturer(lecturer);
        lecturerTO.setPicture(signUrl);
        return lecturerTO;
    }

    @Override
    public void updateLecturerViaMultipart(LecturerReqTO lecturerReqTO) {
        Lecturer currentLecturer = lecturerRepository.findById(lecturerReqTO.getId()).orElseThrow(() -> new AppException(404, "No lecturer Found fromthis id"));
        System.out.println(currentLecturer);
        Lecturer newLecturer = transformer.toLecturer(lecturerReqTO);
        newLecturer.setLinkedIn(null);
        newLecturer = lecturerRepository.save(newLecturer);

        System.out.println("picture  " + lecturerReqTO.getPicture());
        Blob blobRef = null;
        if (currentLecturer.getPicture() != null){
            //1.delete earlier picture
            blobRef = bucket.get(currentLecturer.getPicture().getPictureUrl());
            pictureRepository.delete(currentLecturer.getPicture());

            //save new picture
            Picture picture = new Picture(newLecturer, "lecturers/" + newLecturer.getId());
            System.out.println(picture);
            newLecturer.setPicture(pictureRepository.save(picture));
        }
        if (currentLecturer.getLinkedIn() !=null){
            linkedInRepository.delete(currentLecturer.getLinkedIn());
            LinkedIn linkedIn = new LinkedIn(newLecturer, lecturerReqTO.getLinkedIn());
            newLecturer.setLinkedIn(linkedInRepository.save(linkedIn));
        }
        //2.update picture

        try {
            if(lecturerReqTO.getPicture()!=null){
                Picture picture = new Picture(newLecturer, "lecturers/" + newLecturer.getId());
                newLecturer.setPicture(pictureRepository.save(picture));
                bucket.create(newLecturer.getPicture().getPictureUrl(),lecturerReqTO.getPicture().getInputStream(),lecturerReqTO.getPicture().getContentType());
            }else if (blobRef !=null){
                System.out.println("blobref not null");
                blobRef.delete();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateLectureDetails(LecturerTO lecturerTO) {
        Lecturer lecturer = lecturerRepository.findById(lecturerTO.getId()).orElseThrow(() -> new AppException(404, "No Lecture found by this id"));
        if (lecturerTO.getLinkedIn() != null){
            linkedInRepository.delete(lecturer.getLinkedIn());
        }

        Lecturer newLecturer = transformer.fromLecturerTO(lecturerTO);
        newLecturer = lecturerRepository.save(newLecturer);

        if(lecturerTO.getLinkedIn() != null){
            LinkedIn linkedIn = new LinkedIn(newLecturer, lecturerTO.getLinkedIn());
            newLecturer.setLinkedIn(linkedInRepository.save(linkedIn));
        }

    }

    @Override
    public void deleteLecturer(Integer id) {
        if(!lecturerRepository.existsById(id)) new AppException(404,"No lecturer found by this id");
        lecturerRepository.deleteById(id);
    }

    @Override
    public LecturerTO getLecturerDetails(Integer id) {
        Lecturer lecturer = lecturerRepository.findById(id).orElseThrow(() -> new AppException(404, "No lecturer found by this id"));
        LecturerTO lecturerTO = transformer.fromLecturer(lecturer);
        if (lecturer.getPicture() != null) lecturerTO.setPicture(bucket.get(lecturer.getPicture().getPictureUrl()).signUrl(5,TimeUnit.MINUTES, Storage.SignUrlOption.withV4Signature()).toString());
        return lecturerTO;
    }

    @Override
    public List<LecturerTO> getAllLecturers(LecturerType lecturerType) {
        List<Lecturer> lecturerList;
        if (lecturerType == null) lecturerList = lecturerRepository.findAll();
        else lecturerList = lecturerRepository.findLecturersByType(lecturerType);

        return lecturerList.stream().map(l->{
            LecturerTO lecturerTO = transformer.fromLecturer(l);
            if (l.getPicture() != null){
                lecturerTO.setPicture(bucket.get(l.getPicture().getPictureUrl()).signUrl(2,TimeUnit.MINUTES,Storage.SignUrlOption.withV4Signature()).toString());
            }
            return lecturerTO;
        }).collect(Collectors.toList());

    }
}
