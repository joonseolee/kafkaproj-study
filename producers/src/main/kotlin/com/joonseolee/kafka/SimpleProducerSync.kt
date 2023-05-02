package com.joonseolee.kafka

import mu.KotlinLogging
import org.apache.kafka.clients.producer.ProducerRecord

class SimpleProducerSync : TestCallback {
    override fun execute(message: String) {
        val topicName = "simple-topic"
        val kafkaProducer = KafkaConnector.generateKafkaProducer()
        val producerRecord = ProducerRecord<String, String>(topicName, message)

        runCatching {
            val recordMetadata = kafkaProducer.send(producerRecord).get()
            logger.info { "#### record metadata received ####\n" +
                    "partition: " + recordMetadata.partition() + "\n" +
                    "offset: " + recordMetadata.offset() + "\n" +
                    "timestamp: " + recordMetadata.timestamp() }
        }.onFailure {
            logger.error { "에러발생! - ${it.stackTrace}" }
        }.also {
            kafkaProducer.close()
        }
    }

    companion object {
        private val logger = KotlinLogging.logger("simpleProducerSync")
    }
}