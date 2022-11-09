package br.com.zoro.kafkapub.controller

import br.com.zoro.kafkapub.avro.PaymentSent
import br.com.zoro.kafkapub.consumer.dto.PaymentEventDTO
import br.com.zoro.kafkapub.producer.PaymentSendProducer
import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.messaging.core.QueueMessagingTemplate
import org.slf4j.LoggerFactory
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RestApi (
    val paymentSendProducer: PaymentSendProducer,
    val objectMapper:ObjectMapper
){
    val log = LoggerFactory.getLogger(this.javaClass)

    @GetMapping(value = ["/v1/post"])
    fun postTopic(){
        val dto = PaymentEventDTO(namePayer = "Armando",
            nameReceiver = "Rebeca",
            e2e = "E12312312",
            amount = "123.12",
            situation = true)
        val string = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto)
        log.info("DTO: {}", dto)

        log.info("Jackson: {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto))

        try {
            val msg = MessageBuilder.withPayload<PaymentEventDTO>(dto).build()
            log.info( "{}", msg)
            log.info("Mensagem postada na fila({}}) com sucesso.", "publisher-queue")
        } catch (e: Exception) {
            log.error( "{}", e.message)
            //throw SqsException(e.message)
        }

        paymentSendProducer.send(PaymentSent("Armando","Igor", "E123123131","12.12", true))
    }
}