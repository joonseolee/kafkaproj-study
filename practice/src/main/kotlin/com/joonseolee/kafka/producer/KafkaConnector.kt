package com.joonseolee.kafka.producer

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.IntegerSerializer
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*
import java.util.function.Consumer

class KafkaConnector {

    companion object {
        fun generateKafkaProducer(): KafkaProducer<String, String> {
            val props = Properties()
            props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
            props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
            props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)

            return KafkaProducer<String, String>(props)
        }

        fun generateKafkaProducer(propertyFunc: Consumer<Properties>): KafkaProducer<String, String> {
            val props = Properties()
            props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
            props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
            props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)

            propertyFunc.accept(props)
            return KafkaProducer<String, String>(props)
        }

        fun generateIntKafkaProducer(): KafkaProducer<Int, String> {
            val props = Properties()
            props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
            props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer::class.java.name)
            props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)

            return KafkaProducer<Int, String>(props)
        }
    }
}