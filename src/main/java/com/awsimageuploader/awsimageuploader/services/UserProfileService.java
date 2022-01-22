package com.awsimageuploader.awsimageuploader.services;

import com.awsimageuploader.awsimageuploader.domains.UserProfile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface UserProfileService {

    List<UserProfile> getUserProfiles();

    void uploadUserProfileImage(String userProfileId, MultipartFile file);

    byte[] downloadUserProfileImage(String userProfileId);

    UserProfile addUser(UserProfile user);

}
