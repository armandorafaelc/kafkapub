package br.com.zoro.kafkapub

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkapubApplication

fun main(args: Array<String>) {
	runApplication<KafkapubApplication>(*args)
}
