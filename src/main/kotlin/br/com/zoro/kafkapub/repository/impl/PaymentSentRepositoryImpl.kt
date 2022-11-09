package br.com.zoro.kafkapub.repository.impl

import br.com.zoro.kafkapub.exceptions.PersistenceDBException
import br.com.zoro.kafkapub.repository.IPaymentSentRepository
import br.com.zoro.kafkapub.repository.domain.PaymentSentDomain
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class PaymentSentRepositoryImpl(
    val dynamoDBMapper: DynamoDBMapper):IPaymentSentRepository {

    val log = LoggerFactory.getLogger(this.javaClass)

    override fun save(paymentSentDomain: PaymentSentDomain) {
        log.info("Persistindo pagamento: {}", paymentSentDomain)

        try {
            dynamoDBMapper.save(paymentSentDomain)
        }catch (ex: Exception){
            log.error("Erro ao persistir pagamento: {}", ex.message)
            throw PersistenceDBException("Erro ao persistir no banco de dados: " + ex.message)
        }

    }
}