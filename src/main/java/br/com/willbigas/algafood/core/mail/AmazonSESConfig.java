package br.com.willbigas.algafood.core.mail;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonSESConfig {

    @Value("${algafood.aws.ses.key}")
    private String key;

    @Value("${algafood.aws.ses.secret-key}")
    private String secretKey;

    @Value("${algafood.aws.ses.region}")
    private String regiao;

    @Bean
    public AmazonSimpleEmailService getAmazonSimpleEmailService() {
        return AmazonSimpleEmailServiceClientBuilder.standard()
                .withCredentials(getAwsCredentialProvider())
                .withRegion(regiao)
                .build();
    }

    private AWSCredentialsProvider getAwsCredentialProvider() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(key, secretKey);
        return new AWSStaticCredentialsProvider(awsCredentials);
    }
}
