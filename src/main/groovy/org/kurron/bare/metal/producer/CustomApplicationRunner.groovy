package org.kurron.bare.metal.producer

import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.core.MessageDeliveryMode
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner

import java.security.SecureRandom

/**
 * Handles command-line arguments.
 */
class CustomApplicationRunner implements ApplicationRunner {

    /**
     * Random number generator.
     */
    private static final SecureRandom SECURE_RANDOM = new SecureRandom()

    /**
     * Handles AMQP communications.
     */
    @Autowired
    protected RabbitTemplate theTemplate


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
        SECURE_RANDOM.nextBytes(buffer)
    }

    private static Message createMessage(byte[] payload,
                                    String contentType) {
        MessageBuilder.withBody(payload)
                .setContentType(contentType)
                .setMessageId(generateMessageID())
                .setTimestamp(generateTimeStamp())
                .setAppId( 'bare-metal-producer' )
                .setCorrelationIdString( generateCorrelationID() )
                .setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT)
                .build()
    }

    @Override
    void run(ApplicationArguments args) throws Exception {
        int numberOfMessages = 20
        int payloadSize = 128
        def messageType = 'bare-metal'

        println "Uploading ${numberOfMessages} messages with a payload size of ${payloadSize} to Mold-E"

        def buffer = new byte[payloadSize]

        numberOfMessages.times {
            if (0 == it % 10) {
                print '.'
            }
            randomize(buffer)
            def message = createMessage(buffer, "application/json;tl-type=${messageType};version=1.0.0")
            theTemplate.send( 'hard-coded-exchange-name', message )
        }
        println()
        'Publishing complete'
    }
}
