package br.com.zoro.kafkapub.repository

import br.com.zoro.kafkapub.repository.domain.PaymentSentDomain

interface IPaymentSentRepository {
    fun save(paymentSentDomain: PaymentSentDomain)
}