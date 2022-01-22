package com.awsimageuploader.awsimageuploader.services;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

public interface S3Service {

    void saveObject(String path, String fileName, Optional<Map<String, String>> optionalMetaData, InputStream inputStream);
    byte[] downloadObject(String path, String userProfileImageLink);
}
