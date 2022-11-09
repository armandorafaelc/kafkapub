package br.com.zoro.kafkapub.service.impl

import br.com.zoro.kafkapub.consumer.dto.PaymentEventDTO
import br.com.zoro.kafkapub.producer.PaymentSendProducer
import br.com.zoro.kafkapub.repository.IPaymentSentRepository
import br.com.zoro.kafkapub.service.IPaymentSentService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PaymentSentService(
    private val paymentSentRepository: IPaymentSentRepository,
    private val paymentSendProducer: PaymentSendProducer
    ):IPaymentSentService {
    override fun processPaymentSent(paymentSentDTO: PaymentEventDTO) {
        val log = LoggerFactory.getLogger(this.javaClass)

        log.info("Processando pagamento: {}", paymentSentDTO)
        //Posta Tópico
        paymentSendProducer.send(paymentSentDTO.maptoPaymentSent())

        //Salva dynamoDB
        paymentSentRepository.save(paymentSentDTO.mapToPaymentSentDomain())
    }
}