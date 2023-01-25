package br.com.zoro.kafkapub.repository.impl

import br.com.zoro.kafkapub.consumer.dto.PaymentEventDTO
import br.com.zoro.kafkapub.exceptions.PersistenceDBException
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.lang.RuntimeException

internal class PaymentSentRepositoryImplTest {

    private val dynamoDBMapper: DynamoDBMapper = mock(DynamoDBMapper::class.java)

    private var paymentSentRepositoryImpl: PaymentSentRepositoryImpl = mock(PaymentSentRepositoryImpl::class.java)

    @BeforeEach
    fun setup() {
        paymentSentRepositoryImpl = PaymentSentRepositoryImpl(dynamoDBMapper)
    }

    @Test
    fun `Deve salvar evento com sucesso no banco de dados`() {
        val paymentEventDTO = PaymentEventDTO(
            namePayer = "Joao",
            nameReceiver = "Pedro",
            e2e = "E123123123",
            amount = "12.12",
            situation = true,
            dateTime = "2018-01-25T07:41:02.296Z"
        )

        val domain = paymentEventDTO.mapToPaymentSentDomain()

        assertAll({paymentSentRepositoryImpl.save(domain)})
        assertThat(domain.id, notNullValue())
    }

    @Test
    fun `Deve falhar ao salvar evento no banco de dados`() {
        val paymentEventDTO = PaymentEventDTO(
            namePayer = "Joao",
            nameReceiver = "Pedro",
            e2e = "E123123123",
            amount = "12.12",
            situation = true,
            dateTime = "2018-01-25T07:41:02.296Z"
        )

        val domain = paymentEventDTO.mapToPaymentSentDomain()

        `when`(dynamoDBMapper.save(domain)).thenThrow(RuntimeException::class.java)
        assertThrows(
            PersistenceDBException::class.java
        ) { paymentSentRepositoryImpl.save(domain) }
    }
}