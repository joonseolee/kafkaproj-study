package com.joonseolee.kafka.event

import mu.KotlinLogging
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord

class FileEventHandler(
    private val kafkaProducer: KafkaProducer<String, String>,
    private val topicName: String,
    private val sync: Boolean
) : EventHandler {
    override fun onMessage(messageEvent: MessageEvent) {
        val producerRecord = ProducerRecord(topicName, messageEvent.key, messageEvent.value)

        if (this.sync) {
            val recordMetadata = kafkaProducer.send(producerRecord).get()
            logger.info { """
                #### record metadata received ####\n
                partition: ${recordMetadata.partition()}\n
                offset: ${recordMetadata.offset()}\n
                timestamp: ${recordMetadata.timestamp()}
                """ }
            return
        }

        kafkaProducer.send(producerRecord) { metadata, exception ->
            if (exception != null) {
                logger.info {
                    """
                        #### record metadata received ####\n
                        partition: ${metadata.partition()}\n
                        offset: ${metadata.offset()}\n
                        timestamp: ${metadata.timestamp()}
                    """
                }
            }
        }
    }

    companion object {
        private val logger = KotlinLogging.logger("FileEventHandler")
    }
}