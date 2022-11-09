package br.com.zoro.kafkapub.producer

import br.com.zoro.kafkapub.avro.PaymentSent
import br.com.zoro.kafkapub.configuration.PAYMENT_SENT
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Repository

@Repository
class PaymentSendProducer(val paymentSend: KafkaTemplate<String, PaymentSent>) {

    val log = LoggerFactory.getLogger(this.javaClass)

    fun send(message: PaymentSent) {
        val response = paymentSend.send(PAYMENT_SENT, message.e2e.toString(), message)
        log.info("Enviando com sucesso a mensagem: {}", response.get().producerRecord)
    }

}