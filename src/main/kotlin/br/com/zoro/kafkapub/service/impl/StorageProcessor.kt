package br.com.zoro.kafkapub.service.impl

import br.com.zoro.kafkapub.configuration.BucketProperties
import br.com.zoro.kafkapub.service.IStorageProcessor
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.transfer.TransferManager
import com.amazonaws.services.s3.transfer.model.UploadResult
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.io.File

@Service
class StorageProcessor(
    private val transferManager: TransferManager,
    private val bucketProperties: BucketProperties
):IStorageProcessor {
    val log = LoggerFactory.getLogger(this.javaClass)


    override fun senfFile(): UploadResult {
        log.info("Enviando arquivo para storage: {}", bucketProperties.bucket)
        val inputString = "Hello World!"
        val byteArrray = inputString.toByteArray()

        return transferManager.upload(
            PutObjectRequest(
                this.bucketProperties.bucket,
                "Nome",
                ByteArrayInputStream(byteArrray),
                ObjectMetadata().apply {
                    this.contentLength = byteArrray.size.toLong()
                }
            )
        ).waitForUploadResult()
    }
}