package com.awsimageuploader.awsimageuploader.bucket;

public enum AwsBuckets {
    PROFILE_IMAGE("aws-image-uploader-aiu");

    private final String name;

    AwsBuckets(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
