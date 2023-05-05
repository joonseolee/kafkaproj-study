package com.joonseolee.kafka

import mu.KotlinLogging
import org.apache.kafka.clients.producer.ProducerRecord

class ProducerAsyncWithKey : TestCallback {
    override fun execute(message: String) {
        val topicName = "multipart-topic"
        val kafkaProducer = KafkaConnector.generateKafkaProducer()

        for (i in 0..20) {
            val producerRecord = ProducerRecord(topicName, i.toString(), message)

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
    }

    companion object {
        private val logger = KotlinLogging.logger("producerAsyncWithKey")
    }
}