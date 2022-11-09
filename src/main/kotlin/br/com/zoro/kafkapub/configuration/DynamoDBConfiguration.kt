package br.com.zoro.kafkapub.configuration

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary


@Configuration
class DynamoDBConfiguration {

    @Value("\${cloud.aws.region.static}")
    private val region: String? = null

    @Value("\${cloud.aws.dynamodb.endpoint}")
    private val dynamoDbEndpointUrl: String? = null

    @Value("\${cloud.aws.dynamodb.access-key}")
    private val accessKey: String? = null

    @Value("\${cloud.aws.dynamodb.secret-key}")
    private val secretKey: String? = null


//    @Bean(destroyMethod = "shutdown")
//    @Primary
//    fun dynamoDB(): AmazonDynamoDB? {
//        return AmazonDynamoDBClientBuilder.standard()
//            .build()
//    }

    @Bean
    fun dynamoDBMapper(): DynamoDBMapper? {
        return DynamoDBMapper(amazonDynamoDB())
    }

    @Bean(name = ["amazonDynamoDB"])
    fun amazonDynamoDB(): AmazonDynamoDB? {
        return AmazonDynamoDBClientBuilder.standard()
            .withCredentials(getCredentialsProvider())
            .withEndpointConfiguration(getEndpointConfiguration(dynamoDbEndpointUrl!!))
            .build()
    }

    private fun getEndpointConfiguration(url: String): EndpointConfiguration? {
        return EndpointConfiguration(url, region)
    }

    private fun getCredentialsProvider(): AWSStaticCredentialsProvider? {
        return AWSStaticCredentialsProvider(getBasicAWSCredentials())
    }

    private fun getBasicAWSCredentials(): BasicAWSCredentials? {
        return BasicAWSCredentials(accessKey, secretKey)
    }
}