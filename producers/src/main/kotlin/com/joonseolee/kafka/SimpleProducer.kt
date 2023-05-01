package com.joonseolee.kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Properties

fun main(args: Array<String>) {
    val topicName = "simple-topic"

    val props = Properties()
    props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:32181")
    props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
    props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)

    val kafkaProducer = KafkaProducer<String, String>(props)

    val producerRecord = ProducerRecord<String, String>(topicName, "hello world")

    kafkaProducer.send(producerRecord)
    kafkaProducer.flush()
    kafkaProducer.close()
}