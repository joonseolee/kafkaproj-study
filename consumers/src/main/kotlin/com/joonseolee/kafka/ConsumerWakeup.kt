package com.joonseolee.kafka

import mu.KotlinLogging
import java.time.Duration
import kotlin.math.log


class ConsumerWakeup : TestCallback {
    override fun execute() {
        val topicName = "basic-topic"
        val kafkaConsumer = KafkaConnector.generateKafkaConsumer()
        kafkaConsumer.subscribe(listOf(topicName))

        val currentThread = Thread.currentThread()
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                logger.info { "main program starts to the exit by calling wakeup" }
                kafkaConsumer.wakeup()

                runCatching {
                    currentThread.join()
                }.onFailure { logger.error { "occurred an error $it" } }
            }
        })

        runCatching {
            while (true) {
                val consumerRecords = kafkaConsumer.poll(Duration.ofMillis(1000))
                for (record in consumerRecords) {
                    logger.info { "record key: ${record.key()}, record value: ${record.value()}, partition: ${record.partition()}" }
                }
            }
        }.onFailure { logger.error { "error while polling $it" } }
            .also {
                logger.info { "get into the also step" }
                kafkaConsumer.close()
            }
    }

    companion object {
        private val logger = KotlinLogging.logger("SimpleConsumer")
    }
}