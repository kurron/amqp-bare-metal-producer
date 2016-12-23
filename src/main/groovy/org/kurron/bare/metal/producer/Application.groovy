package org.kurron.bare.metal.producer

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableConfigurationProperties( ApplicationProperties )
class Application {

	static void main(String[] args) {
		SpringApplication.run Application, args
	}

	@Bean
	CustomApplicationRunner customApplicationRunner() {
		new CustomApplicationRunner()
	}

	@Bean
	DirectExchange exchange( ApplicationProperties configuration ) {
		new DirectExchange( configuration.exchange, true, false )
	}

	@Bean
	Queue queue( ApplicationProperties configuration ) {
		new Queue( configuration.queue, true )
	}

	@Bean
	Binding binding( ApplicationProperties configuration, Queue queue, DirectExchange exchange ) {
		BindingBuilder.bind( queue ).to( exchange ).with( configuration.routingKey )
	}
}
