package br.com.zoro.kafkapub.configuration

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class DynamoDBConfiguration {

    @Bean(destroyMethod = "shutdown")
    @Primary
    fun dynamoDB(): AmazonDynamoDB? {
        return AmazonDynamoDBClientBuilder.standard()
            .build()
    }

    @Bean
    fun dynamoDBMapper(): DynamoDBMapper? {
        return DynamoDBMapper(dynamoDB())
    }
}