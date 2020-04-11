package com.kanbanedchain.lianatasks.Bucket;

public enum BucketName {

    BACKGROUND_IMAGE("kclts-background-image-upload");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
