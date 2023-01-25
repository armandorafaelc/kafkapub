package br.com.zoro.kafkapub.consumer

import br.com.zoro.kafkapub.consumer.dto.PaymentEventDTO
import br.com.zoro.kafkapub.exceptions.PersistenceDBException
import br.com.zoro.kafkapub.producer.PaymentSendProducer
import br.com.zoro.kafkapub.repository.IPaymentSentRepository
import br.com.zoro.kafkapub.service.impl.PaymentSentService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

internal class QueueConsumerTest {

    private lateinit var queueConsumer: QueueConsumer

    private var paymentSentService: PaymentSentService = mock(PaymentSentService::class.java)

    private var paymentSentRepository: IPaymentSentRepository = mock(IPaymentSentRepository::class.java)

    private var paymentSendProducer: PaymentSendProducer = mock(PaymentSendProducer::class.java)


    @BeforeEach
    fun setup() {
        paymentSentService = PaymentSentService(paymentSentRepository, paymentSendProducer)
        queueConsumer = QueueConsumer(paymentSentService)
    }

    @Test
    fun `Deve consumir com sucesso mensagem sqs`() {
        val paymentEventDTO = PaymentEventDTO(
            namePayer = "Joao",
            nameReceiver = "Pedro",
            e2e = "E123123123",
            amount = "12.12",
            situation = true,
            dateTime = "2018-01-25T07:41:02.296Z"
        )

        assertAll({ queueConsumer.listen(paymentEventDTO) })
    }


    @Test
    fun `Deve falhar ao consumir com sucesso mensagem sqs e não salvar no banco de dados`() {
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
        ) { queueConsumer.listen(paymentEventDTO) }
    }
}