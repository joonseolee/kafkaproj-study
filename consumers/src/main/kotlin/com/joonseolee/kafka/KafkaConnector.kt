package com.joonseolee.kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.util.*
import java.util.function.Consumer

class KafkaConnector {

    companion object {
        fun generateKafkaConsumer(): KafkaConsumer<String, String> {
            return generateKafkaConsumer(null)
        }

        fun generateKafkaConsumer(propertyFunc: Consumer<Properties>?): KafkaConsumer<String, String> {
            val props = Properties()
            props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
            props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
            props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
            props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group_01")

            propertyFunc?.accept(props)
            return KafkaConsumer<String, String>(props)
        }
    }
}