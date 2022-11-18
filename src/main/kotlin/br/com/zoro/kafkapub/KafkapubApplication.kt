package br.com.zoro.kafkapub

import br.com.zoro.kafkapub.configuration.BucketProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@EnableConfigurationProperties(BucketProperties::class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
class KafkapubApplication

fun main(args: Array<String>) {
	runApplication<KafkapubApplication>(*args)
}
