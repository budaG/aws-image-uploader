package com.awsimageuploader.awsimageuploader.datastore;

import com.awsimageuploader.awsimageuploader.domains.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {

    private static final List<UserProfile> USER_PROFILE = new ArrayList<>();

    static {
        USER_PROFILE.add(new UserProfile(UUID.fromString("67001e4e-e853-4cd9-ac1f-3d90c06c7a74").toString(), "ganesh", null));
        USER_PROFILE.add(new UserProfile(UUID.fromString("0a498c7-c64d-485d-946b-21404fe818cf").toString(), "rekha", null));
    }

    public List<UserProfile> getUserProfiles() {
        return USER_PROFILE;
    }
}
