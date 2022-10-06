package WhaTap.LambdaTestApplication.Domain;

public class S3 {
    private String s3;
    private String key;

    public String getS3() {
        return s3;
    }

    public void setS3(String s3) {
        this.s3 = s3;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public S3(String s3, String key) {
        this.s3 = s3;
        this.key = key;
    }
}
