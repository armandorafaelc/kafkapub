package br.com.zoro.kafkapub.consumer.dto

import org.assertj.core.api.Assertions
import org.assertj.core.api.HamcrestCondition
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.internal.hamcrest.HamcrestArgumentMatcher

class PaymentEventDTOTest{

    @Test
    fun `deve mapear PaymentEventDTO para PaymentSentDomain com sucesso`(){
        val dto = PaymentEventDTO(
            namePayer = "Joao",
            nameReceiver = "Pedro",
            e2e = "E123123123",
            amount = "12.12",
            situation = true,
            dateTime = "2018-01-25T07:41:02.296Z"
        )

        val domain = dto.mapToPaymentSentDomain()

        assertThat(dto.situation, `is`(true))
        assertThat(domain.end2end, `is`(dto.e2e))
        assertThat(domain.namePayer, `is`(dto.namePayer))
        assertThat(domain.nameReceiver, `is`(dto.nameReceiver))
        assertThat(domain.amount, `is`(dto.amount))
        assertThat(domain.dateTime, notNullValue())
        assertThat(domain.dateTime, `is`(dto.dateTime))

    }

    @Test
    fun `deve mapear PaymentEventDTO para PaymentSent com sucesso`(){
        val dto = PaymentEventDTO(
            namePayer = "Joao",
            nameReceiver = "Pedro",
            e2e = "E123123123",
            amount = "12.12",
            situation = true,
            dateTime = "2018-01-25T07:41:02.296Z"
        )

        val paymentSent = dto.maptoPaymentSent()

        assertThat(paymentSent.e2e, `is`(dto.e2e))
        assertThat(paymentSent.namePayer, `is`(dto.namePayer))
        assertThat(paymentSent.nameReceiver, `is`(dto.nameReceiver))
        assertThat(paymentSent.amount, `is`(dto.amount))
        assertThat(paymentSent.situation, `is`(true))
    }
}