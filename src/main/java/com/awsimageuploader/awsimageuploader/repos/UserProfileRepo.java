package com.awsimageuploader.awsimageuploader.repos;

import com.awsimageuploader.awsimageuploader.domains.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserProfileRepo extends JpaRepository<UserProfile, String> {
}
