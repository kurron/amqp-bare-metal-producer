package org.kurron.bare.metal.producer

import groovy.util.logging.Slf4j
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.core.MessageDeliveryMode
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.ConfigurableApplicationContext

import java.util.concurrent.Executors
import java.util.concurrent.ThreadLocalRandom

/**
 * Handles command-line arguments.
 */
@Slf4j
class CustomApplicationRunner implements ApplicationRunner {

    /**
     * Handles AMQP communications.
     */
    @Autowired
    private RabbitTemplate theTemplate

    @Autowired
    private ConfigurableApplicationContext theContext

    @Autowired
    private ApplicationProperties theConfiguration

    private static String generateMessageID() {
        UUID.randomUUID().toString()
    }

    private static String generateCorrelationID() {
        UUID.randomUUID().toString()
    }

    private static Date generateTimeStamp() {
        Calendar.getInstance(TimeZone.getTimeZone('UTC')).time
    }

    private static void randomize(byte[] buffer) {
        ThreadLocalRandom.current().nextBytes(buffer)
    }

    private static void randomizeBytes( int size ) {
        ThreadLocalRandom.current().nextBytes( new byte[size] )
    }

    private static Message createMessage( byte[] payload,
                                         String contentType) {
        MessageBuilder.withBody(payload)
                .setContentType(contentType)
                .setMessageId(generateMessageID())
                .setTimestamp(generateTimeStamp())
                .setAppId('bare-metal-producer')
                .setCorrelationIdString(generateCorrelationID())
                .setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT)
                .build()
    }

    @Override
    void run(ApplicationArguments arguments) {

        def messageCount = Optional.ofNullable(arguments.getOptionValues('number-of-messages')).orElse(['100'])
        def messageSize = Optional.ofNullable(arguments.getOptionValues('payload-size')).orElse(['1024'])

        def numberOfMessages = messageCount.first().toInteger()
        def payloadSize = messageSize.first().toInteger()

        log.info "Uploading ${numberOfMessages} messages with a payload size of ${payloadSize} to broker"


        def messages = (1..numberOfMessages).collect {
            def buffer = new byte[payloadSize]
            randomize(buffer)
            createMessage(buffer, "application/octet-stream")
        }
        log.info "Created ${messages.size()} messages. Sending them to stream."

        def pool = Executors.newFixedThreadPool(64)
        def scheduler = Schedulers.from( pool )
        long start = System.currentTimeMillis()
        Single<Long> completed = Observable.fromIterable( messages )
                                           .flatMap( { Message message -> Observable.fromCallable( { theTemplate.send( theConfiguration.exchange, theConfiguration.routingKey, message ) ; Observable.empty() } ).subscribeOn( scheduler ) } )
                                           .count()
        long stop = System.currentTimeMillis()

        long duration = stop - start
        log.info( 'Published {} messages in {} milliseconds', completed.blockingGet(), duration )

        log.info 'Publishing complete'
        pool.shutdown()
        theContext.close()
    }
}
