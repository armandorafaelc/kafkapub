package br.com.zoro.kafkapub.consumer.dto

import br.com.zoro.kafkapub.avro.PaymentSent
import br.com.zoro.kafkapub.repository.domain.PaymentSentDomain
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class PaymentEventDTO(
    @JsonProperty("namePayer")
    val namePayer: String,
    @JsonProperty("nameReceiver")
    val nameReceiver: String,
    @JsonProperty("e2e")
    val e2e: String,
    @JsonProperty("amount")
    val amount: String? = null,
    @JsonProperty("situation")
    val situation: Boolean? = null,
    @JsonProperty("dateTime")
    val dateTime: String? = null
) {
    fun mapToPaymentSentDomain() = PaymentSentDomain(
        namePayer = this.namePayer,
        nameReceiver = this.nameReceiver,
        end2end = this.e2e,
        amount = this.amount,
        dateTime = this.dateTime
    )

    fun maptoPaymentSent() = PaymentSent(this.namePayer, this.nameReceiver, this.e2e, this.amount, true)
}