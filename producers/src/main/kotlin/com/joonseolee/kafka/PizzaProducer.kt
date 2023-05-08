package com.joonseolee.kafka

import com.github.javafaker.Faker
import mu.KotlinLogging
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import java.util.*

class PizzaProducer : TestCallback {

    override fun execute(message: String) {
        val topicName = "pizza-topic"
        val kafkaProducer = KafkaConnector.generateKafkaProducer()
        sendPizzaMessage(kafkaProducer, topicName, -1, 1000, 0, 0, false)
        kafkaProducer.close()
    }

    private fun sendPizzaMessage(
        kafkaProducer: KafkaProducer<String, String>,
        topicName: String,
        iterCount: Int,
        interIntervalMillis: Int,
        intervalMillis: Int,
        intervalCount: Int,
        sync: Boolean
    ) {
        val pizzaMessage = PizzaMessage()
        var iterSeq = 0
        val seed: Long = 2022
        val random = Random(seed)
        val faker: Faker = Faker.instance(random)

        val startTime = System.currentTimeMillis()

        while (iterSeq++ != iterCount) {
            val pMessage = pizzaMessage.produce(faker, random, iterSeq)
            val producerRecord = ProducerRecord(
                topicName,
                pMessage!!["key"], pMessage["message"]
            )
            sendMessage(kafkaProducer, producerRecord, pMessage, sync)
            if (intervalCount > 0 && iterSeq % intervalCount === 0) {
                try {
                    logger.info(
                        "####### IntervalCount:$intervalCount" +
                                " intervalMillis:" + intervalMillis + " #########"
                    )
                    Thread.sleep(intervalMillis.toLong())
                } catch (e: InterruptedException) {
                    logger.error(e.message)
                }
            }
            if (interIntervalMillis > 0) {
                try {
                    logger.info("interIntervalMillis:$interIntervalMillis")
                    Thread.sleep(interIntervalMillis.toLong())
                } catch (e: InterruptedException) {
                    logger.error(e.message)
                }
            }
        }
        val endTime = System.currentTimeMillis()
        val timeElapsed = endTime - startTime
        logger.info { "$timeElapsed millisecond elapsed for $iterCount " }
    }

    private fun sendMessage(
        kafkaProducer: KafkaProducer<String, String>,
        producerRecord: ProducerRecord<String?, String?>,
        pMessage: java.util.HashMap<String, String>,
        sync: Boolean
    ) {
        if (!sync) {
            kafkaProducer.send(producerRecord) { metadata, exception ->
                if (exception == null) {
                    logger.info(
                        "async message:" + pMessage["key"] + " partition:" + metadata.partition() +
                                " offset:" + metadata.offset()
                    )
                } else {
                    logger.error("exception error from broker ")
                }
            }
        } else {
            kotlin.runCatching {
                val metadata: RecordMetadata = kafkaProducer.send(producerRecord).get()
                logger.info(
                    "sync message:" + pMessage["key"] + " partition:" + metadata.partition() +
                            " offset:" + metadata.offset()
                )
            }.onFailure {
                logger.error(it.message)
            }
        }
    }

    companion object {
        private val logger = KotlinLogging.logger("pizzaProducer")
    }
}