package br.com.zoro.kafkapub.consumer

import br.com.zoro.kafkapub.consumer.dto.PaymentEventDTO
import br.com.zoro.kafkapub.service.impl.PaymentSentService
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy
import io.awspring.cloud.messaging.listener.annotation.SqsListener
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class QueueConsumer(
    private val paymentSentService: PaymentSentService
) {
    val log = LoggerFactory.getLogger(this.javaClass)

    @SqsListener(value = ["publisher-queue"], deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    fun listen(payment: PaymentEventDTO) {
        log.info("Recebendo mensagem mensagem: {}", payment)
        paymentSentService.processPaymentSent(payment)
    }

}