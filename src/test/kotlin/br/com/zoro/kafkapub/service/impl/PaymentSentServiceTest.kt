package br.com.zoro.kafkapub.service.impl

import br.com.zoro.kafkapub.consumer.dto.PaymentEventDTO
import br.com.zoro.kafkapub.exceptions.PersistenceDBException
import br.com.zoro.kafkapub.producer.PaymentSendProducer
import br.com.zoro.kafkapub.repository.IPaymentSentRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

internal class PaymentSentServiceTest{

    private lateinit var paymentSentService: PaymentSentService

    private var paymentSentRepository: IPaymentSentRepository = mock(IPaymentSentRepository::class.java)
    private var paymentSendProducer: PaymentSendProducer = mock(PaymentSendProducer::class.java)

    @BeforeEach
    fun setup(){
        paymentSentService = PaymentSentService(paymentSentRepository, paymentSendProducer)
    }

    @Test
    fun `Deve processar evento recebido com sucesso`(){
        val paymentEventDTO = PaymentEventDTO(
            namePayer = "Joao",
            nameReceiver = "Pedro",
            e2e = "E123123123",
            amount = "12.12",
            situation = true
        )
        paymentSentService.processPaymentSent(paymentEventDTO)
    }

    @Test
    fun `Deve falhar processar evento recebido com sucesso`(){
        val paymentEventDTO = PaymentEventDTO(
            namePayer = "Joao",
            nameReceiver = "Pedro",
            e2e = "E123123123",
            amount = "12.12",
            situation = true,
            dateTime = "2018-01-25T07:41:02.296Z"
        )
        val domain = paymentEventDTO.mapToPaymentSentDomain()
        `when`(paymentSentRepository.save(domain)).thenThrow(RuntimeException::class.java)
        Assertions.assertThrows(
            PersistenceDBException::class.java
        ) { paymentSentService.processPaymentSent(paymentEventDTO) }
    }
}