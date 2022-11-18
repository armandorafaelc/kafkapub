package br.com.zoro.kafkapub.controller

import br.com.zoro.kafkapub.producer.PaymentSendProducer
import br.com.zoro.kafkapub.service.impl.StorageProcessor
import com.fasterxml.jackson.databind.ObjectMapper
import io.micrometer.core.instrument.MeterRegistry
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class RestApi (
    private val storageProcessor: StorageProcessor
){
    val log = LoggerFactory.getLogger(this.javaClass)

    @GetMapping(value = ["/v1/post"])
    fun postTopic(){

        try {
            val msg = storageProcessor.senfFile()
            log.info( "{}", msg)
            log.info("Mensagem postada na fila({}}) com sucesso.", "publisher-queue")
        } catch (e: Exception) {
            log.error( "{}", e.message)
        }
    }
}