package com.awsimageuploader.awsimageuploader.resource;

import com.awsimageuploader.awsimageuploader.domains.UserProfile;
import com.awsimageuploader.awsimageuploader.services.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/userProfiles")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserProfileResource {

    private final UserProfileService userProfileService;

    @PostMapping("")
    ResponseEntity<UserProfile> addUserProfile(@RequestBody UserProfile userProfile) {
        UserProfile user = userProfileService.addUser(userProfile);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<UserProfile>> getUserProfiles() {
        List<UserProfile> userProfiles = userProfileService.getUserProfiles();
        return new ResponseEntity<List<UserProfile>>(userProfiles, HttpStatus.OK);
    }

    @PostMapping(value = "/{userProfileId}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadUserProfileImage(@PathVariable("userProfileId") final String userProfileId,
                                       @RequestParam("file") final MultipartFile file) {
        userProfileService.uploadUserProfileImage(userProfileId, file);
    }

    @GetMapping("/{userProfileId}/image/download")
    public byte[] downloadUserProfileImage(@PathVariable("userProfileId") String userProfileId) {
        return userProfileService.downloadUserProfileImage(userProfileId);

    }
}
