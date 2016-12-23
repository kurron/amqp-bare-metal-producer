package org.kurron.bare.metal.producer

import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.ExchangeBuilder
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class ProducerApplication {

	static void main(String[] args) {
		SpringApplication.run ProducerApplication, args
	}

	@Bean
	CustomApplicationRunner customApplicationRunner() {
		new CustomApplicationRunner()
	}

	@Bean
	Exchange exchange() {
		ExchangeBuilder.directExchange( 'hard-coded-exchange-name' ).durable().build()
	}
}
