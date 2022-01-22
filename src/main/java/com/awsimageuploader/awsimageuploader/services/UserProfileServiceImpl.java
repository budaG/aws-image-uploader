package com.awsimageuploader.awsimageuploader.services;

import com.awsimageuploader.awsimageuploader.bucket.AwsBuckets;
import com.awsimageuploader.awsimageuploader.domains.UserProfile;
import com.awsimageuploader.awsimageuploader.repos.UserProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService{
    private final UserProfileRepo userProfileRepo;
    private final S3Service s3Service;

    @Autowired
    public UserProfileServiceImpl(S3Service s3Service, UserProfileRepo userProfileRepo) {
        this.s3Service = s3Service;
        this.userProfileRepo = userProfileRepo;
    }

    @Override
    public List<UserProfile> getUserProfiles() {
        return userProfileRepo.findAll();
    }

    @Override
    public void uploadUserProfileImage(final String userProfileId, final MultipartFile file) {
        isFile(file);
        isImage(file);

        UserProfile user = getUserProfile(userProfileId);
        Map<String, String> metadata = getMetaData(file);

        String path = String.format("%s/%s", AwsBuckets.PROFILE_IMAGE.getName(), user.getUserName());
        String fileName = String.format("%s-%s", file.getOriginalFilename(), user.getUserProfileId());

        try{
            s3Service.saveObject(path, fileName, Optional.of(metadata), file.getInputStream());
            user.setUserProfileImageLink(fileName);
            userProfileRepo.saveAndFlush(user);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public byte[] downloadUserProfileImage(String userProfileId) {
        UserProfile user = getUserProfile(userProfileId);
        String path = String.format("%s/%s",
                AwsBuckets.PROFILE_IMAGE.getName(),
                user.getUserName());
        return s3Service.downloadObject(path, user.getUserProfileImageLink());
    }

    @Override
    public UserProfile addUser(UserProfile user) {
        user.setUserProfileId(UUID.randomUUID().toString());
        try{
            return userProfileRepo.saveAndFlush(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("User name must be unique");
        } catch (Exception e) {
            throw new IllegalStateException("Invalid request", e);
        }

    }

    private UserProfile getUserProfile(final String userProfileId) {
        UserProfile user = getUserProfiles().stream()
                .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
                .findFirst()
                .orElseThrow(()-> new IllegalStateException(String.format("User profile %s not found", userProfileId)));
        return user;
    }

    private Map<String, String> getMetaData(final MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private void isImage(MultipartFile file) {
        if(!file.getContentType().contains("image/")) throw new IllegalStateException("Content type must be image/* format");
    }

    private void isFile(MultipartFile file) {
        if(file.isEmpty()) throw new IllegalStateException("File is not provided");
    }
}
