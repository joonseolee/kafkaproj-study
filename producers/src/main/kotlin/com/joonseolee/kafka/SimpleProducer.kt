package com.joonseolee.kafka

import org.apache.kafka.clients.producer.ProducerRecord

class SimpleProducer : TestCallback {
    override fun execute(message: String) {
        val topicName = "simple-topic"
        val kafkaProducer = KafkaConnector.generateKafkaProducer()

        val producerRecord = ProducerRecord<String, String>(topicName, message)

        kafkaProducer.send(producerRecord)
        kafkaProducer.flush()
        kafkaProducer.close()
    }
}