package br.com.zoro.kafkapub.configuration

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.transfer.TransferManager
import com.amazonaws.services.s3.transfer.TransferManagerBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URI

@Configuration
@EnableConfigurationProperties(S3Properties::class)
class S3Configuration(
    private val properties: S3Properties
) {

    @Bean(destroyMethod = "shutdown")
    fun amazonS3():AmazonS3?{
        val builder = AmazonS3Client.builder()

        this.properties.endpoint?.let {
            builder.withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(it.toString(), null))
        }?:builder.withRegion(Regions.SA_EAST_1)
        this.properties.pathStyleAccessEnable?.let {
            builder.withPathStyleAccessEnabled(it)
        }
        return builder.build()
    }

    @Bean
    fun transferManager(amazonS3: AmazonS3): TransferManager =
        TransferManagerBuilder.standard().withS3Client(amazonS3).build()
}

@ConstructorBinding
@ConfigurationProperties(prefix = "cloud.aws.s3")
data class S3Properties(val endpoint: URI? = null, val pathStyleAccessEnable: Boolean? = null)

@ConstructorBinding
@ConfigurationProperties(prefix = "storage")
data class BucketProperties(val bucket: String? = null)