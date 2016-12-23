package org.kurron.bare.metal.producer

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {

	static void main(String[] args) {
		SpringApplication.run Application, args
	}

	@Bean
	CustomApplicationRunner customApplicationRunner() {
		new CustomApplicationRunner()
	}

	@Bean
	DirectExchange exchange() {
		new DirectExchange( 'hard-coded-exchange-name', true, false )
	}

	@Bean
	Queue queue() {
		new Queue( 'bare-metal-consumer', true )
	}

	@Bean
	Binding binding( Queue queue, DirectExchange exchange ) {
		BindingBuilder.bind( queue ).to( exchange ).with( queue.name )
	}
}
