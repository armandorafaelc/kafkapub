package br.com.zoro.kafkapub.configuration

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.converter.MessageConverter


@Configuration
class ObjectMapperConfiguration {

    @Bean
    fun objectMapper(): ObjectMapper? {
        return ObjectMapper()
            .findAndRegisterModules()
            .registerModule(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
    }

    @Bean
    fun messageConverter(objectMapper: ObjectMapper?): MessageConverter? {
        val converter = MappingJackson2MessageConverter()
        converter.objectMapper = objectMapper!!
        converter.serializedPayloadClass = String::class.java
        converter.isStrictContentTypeMatch = false
        return converter
    }
}