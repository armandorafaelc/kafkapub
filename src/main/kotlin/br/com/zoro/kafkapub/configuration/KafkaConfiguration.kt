package br.com.zoro.kafkapub.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin

@Configuration
@EnableKafka
class KafkaConfiguration {

//    @Bean
//    fun appTopics(): KafkaAdmin.NewTopics? {
//        return KafkaAdmin.NewTopics(
//            TopicBuilder.name(PAYMENT_SENT).build(),
//        )
//    }
}
const val PAYMENT_SENT = "payment_sent_topic"