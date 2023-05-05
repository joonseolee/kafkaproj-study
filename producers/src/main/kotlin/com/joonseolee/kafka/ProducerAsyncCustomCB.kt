package com.joonseolee.kafka

import org.apache.kafka.clients.producer.ProducerRecord

class ProducerAsyncCustomCB : TestCallback {
    override fun execute(message: String) {
        val topicName = "multipart-topic"
        val kafkaProducer = KafkaConnector.generateIntKafkaProducer()

        for (i in 0..20) {
            val producerRecord = ProducerRecord(topicName, i, message)
            val callback = CompleteCallback(i)
            kafkaProducer.send(producerRecord, callback)
        }
    }
}