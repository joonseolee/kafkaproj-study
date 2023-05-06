package com.joonseolee.kafka

import mu.KotlinLogging
import java.time.Duration


class SimpleConsumer : TestCallback {
    override fun execute() {
        val topicName = "basic-topic"
        val kafkaConsumer = KafkaConnector.generateKafkaConsumer()
        kafkaConsumer.subscribe(listOf(topicName))

        while (true) {
            val consumerRecords = kafkaConsumer.poll(Duration.ofMillis(1000))
            for (record in consumerRecords) {
                logger.info { "record key: ${record.key()}, record value: ${record.value()}, partition: ${record.partition()}" }
            }
        }
    }

    companion object {
        private val logger = KotlinLogging.logger("SimpleConsumer")
    }
}