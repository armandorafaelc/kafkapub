package br.com.zoro.kafkapub.configuration

import com.amazonaws.services.sqs.AmazonSQSAsync
import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.messaging.config.QueueMessageHandlerFactory
import io.awspring.cloud.messaging.core.QueueMessagingTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.converter.MessageConverter
import org.springframework.messaging.handler.annotation.support.PayloadArgumentResolver
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver
import java.util.*


@Configuration
class CustomSqsConfiguration {
    @Bean
    fun queueMessagingTemplate(
        amazonSQSAsync: AmazonSQSAsync?
    ): QueueMessagingTemplate {
        return QueueMessagingTemplate(amazonSQSAsync)
    }

    @Bean
    fun queueMessageHandlerFactory(): QueueMessageHandlerFactory? {
        val factory = QueueMessageHandlerFactory()
        val messageConverter = MappingJackson2MessageConverter()
        val  mutableList = ArrayList<HandlerMethodArgumentResolver>()

        //set strict content type match to false
        messageConverter.isStrictContentTypeMatch = false
        mutableList.add(PayloadArgumentResolver(messageConverter))
        factory.setArgumentResolvers(mutableList)
        return factory
    }

    private fun jackson2MessageConverter(mapper: ObjectMapper): MessageConverter {
        val converter = MappingJackson2MessageConverter()
        converter.objectMapper = mapper
        return converter
    }
}