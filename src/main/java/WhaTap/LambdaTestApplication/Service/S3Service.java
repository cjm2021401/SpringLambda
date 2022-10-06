package WhaTap.LambdaTestApplication.Service;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.WebIdentityTokenCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Service
public class S3Service {
    private final AmazonS3 s3Client;
    public S3Service(){
        WebIdentityTokenCredentialsProvider awsCredentialProvider = WebIdentityTokenCredentialsProvider.builder()
                .roleArn(System.getenv("AWS_ROLE_ARN"))
                .roleSessionName(System.getenv("AWS_ROLE_SESSION_NAME"))
                .webIdentityTokenFile(System.getenv("AWS_WEB_IDENTITY_TOKEN_FILE"))
                .build();
        s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(awsCredentialProvider)
                .build();
    }

    public List<String> GetS3Object(){
        ListObjectsV2Request listObjectsV2Request=new ListObjectsV2Request();
        listObjectsV2Request.setBucketName("whatap-demo");
        ListObjectsV2Result result = s3Client.listObjectsV2(listObjectsV2Request);
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        List<String> keys = new ArrayList<>();
        for(S3ObjectSummary ss : objects){
            keys.add(ss.getKey());
        }
        return keys;
    }

    public String MakeSingedUrl(String bucket, String key){
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = Instant.now().toEpochMilli();
        expTimeMillis += 1000 * 60 * 3;
        expiration.setTime(expTimeMillis);
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, key)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);
        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

}
