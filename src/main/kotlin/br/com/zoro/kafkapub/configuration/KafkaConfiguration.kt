package br.com.zoro.kafkapub.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka


@Configuration
@EnableKafka
class KafkaConfiguration {
}

const val PAYMENT_SENT = "payment_sent_topic"