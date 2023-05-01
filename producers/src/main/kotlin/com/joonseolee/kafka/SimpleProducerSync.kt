package com.joonseolee.kafka

import mu.KotlinLogging
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Properties

val logger = KotlinLogging.logger("simpleProducerSync")

fun main(args: Array<String>) {
    val topicName = "simple-topic"

    val props = Properties()
    props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
    props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)

    val kafkaProducer = KafkaProducer<String, String>(props)

    val producerRecord = ProducerRecord<String, String>(topicName, "hello world!!!")

    logger.info { "### start ###" }
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