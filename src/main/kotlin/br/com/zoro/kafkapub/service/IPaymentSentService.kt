package br.com.zoro.kafkapub.service


import br.com.zoro.kafkapub.consumer.dto.PaymentEventDTO

interface IPaymentSentService {
    fun processPaymentSent(paymentSentDTO: PaymentEventDTO)
}