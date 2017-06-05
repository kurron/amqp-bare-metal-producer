package org.kurron.bare.metal.producer

import groovy.util.logging.Slf4j
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.core.MessageDeliveryMode
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.ConfigurableApplicationContext

import java.time.Duration
import java.util.concurrent.Executors

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

    private static Message createMessage(byte[] payload,
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
        def threadCount = Optional.ofNullable(arguments.getOptionValues('thread-count')).orElse(['32'])

        def numberOfMessages = messageCount.first().toInteger()
        def payloadSize = messageSize.first().toInteger()
        def poolSize = threadCount.first().toInteger()

        log.info "Publishing ${numberOfMessages} messages with a payload size of ${payloadSize} to the broker"

        def sequence = Observable.fromIterable ( [1..numberOfMessages].first() )

        log.info( 'Using a scheduler with a thread pool size of {}', poolSize )
        def pool = Executors.newFixedThreadPool( poolSize )
        def scheduler = Schedulers.from( pool )

        def mapper = {
            def payload = new byte[payloadSize]
            def message = createMessage(payload, "application/octet-stream")
            def callable = {
                theTemplate.send(theConfiguration.exchange, theConfiguration.routingKey, message)
                Observable.empty()
            }
            Observable.fromCallable(callable).subscribeOn(scheduler)
        }

        long start = System.currentTimeMillis()
        def completed = sequence.flatMap(mapper).count().blockingGet()
        long stop = System.currentTimeMillis()

        long duration = stop - start
        def durationISO = Duration.ofMillis( duration )
        log.info('Published {} messages in {}', completed, durationISO as String )

        log.info 'Publishing complete'
        pool.shutdown()
        theContext.close()
    }
}
