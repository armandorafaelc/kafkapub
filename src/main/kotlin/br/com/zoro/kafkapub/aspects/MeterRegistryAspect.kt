package br.com.zoro.kafkapub.aspects

import io.micrometer.core.instrument.MeterRegistry
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*


@Aspect
@Component
class MeterRegistryAspect(
	private val meterRegistry: MeterRegistry
) {
	val log = LoggerFactory.getLogger(this.javaClass)

	@After("execution(* br.com.zoro.kafkapub.controller..*(..))")
	fun allMethodsController() {
		meterRegistry.counter("ChamadaAPI", "chamadaAPI", UUID.randomUUID().toString())
		log.info("Gerando metrica rest api")
	}

	@After("execution(* br.com.zoro.kafkapub.producer..*(..))")
	fun allMethodsProducer() {
		meterRegistry.counter("ProducerKafka", "producerKafka", UUID.randomUUID().toString())
		log.info("Gerando metrica producer")
	}

	@After("execution(* br.com.zoro.kafkapub.consumer..*(..))")
	fun allMethodsConsumer() {
		meterRegistry.counter("eventosSQS", "eventosSQS", UUID.randomUUID().toString())
		log.info("Gerando metrica consumer")
	}
}