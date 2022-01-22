package com.awsimageuploader.awsimageuploader.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
public class S3ServiceImpl implements S3Service{

    private final AmazonS3 s3;

    @Autowired
    public S3ServiceImpl(AmazonS3 amazonS3) {
        this.s3 = amazonS3;
    }

    @Override
    public void saveObject(final String path, String fileName, Optional<Map<String, String>> optionalMetaData, InputStream inputStream) {
        ObjectMetadata metadata = new ObjectMetadata();
        optionalMetaData.ifPresent(map ->{
            if(!map.isEmpty()) {
                map.forEach(metadata::addUserMetadata);
            }

        });

        try{
            s3.putObject(path, fileName, inputStream, metadata);
        } catch (AmazonServiceException e) {
            throw  new IllegalStateException("Failed to store object to s3", e);
        }
    }

    @Override
    public byte[] downloadObject(String path, String userProfileImageLink) {
        try{
            S3Object object = s3.getObject(path, userProfileImageLink);
            return IOUtils.toByteArray(object.getObjectContent());
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download file from s3", e);
        }
    }
}
