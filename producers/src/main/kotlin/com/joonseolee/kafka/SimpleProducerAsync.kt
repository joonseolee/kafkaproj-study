package com.joonseolee.kafka

import mu.KotlinLogging
import org.apache.kafka.clients.producer.ProducerRecord

class SimpleProducerAsync : TestCallback {
    override fun execute(message: String) {
        val topicName = "simple-topic"
        val kafkaProducer = KafkaConnector.generateKafkaProducer()
        val producerRecord = ProducerRecord<String, String>(topicName, message)

        kafkaProducer.send(producerRecord) { metadata, exception ->
            if (exception == null) {
                logger.info {
                    "#### record metadata received ####\n" +
                            "partition: " + metadata.partition() + "\n" +
                            "offset: " + metadata.offset() + "\n" +
                            "timestamp: " + metadata.timestamp()
                }
            } else {
                logger.error { "에러 발생~~! ${exception.stackTrace}" }
            }
        }
    }

    companion object {
        private val logger = KotlinLogging.logger("simpleProducerAsync")
    }
}