package com.joonseolee.kafka

import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerConfig
import java.time.Duration


class ConsumerMTopicRebalancer : TestCallback {
    override fun execute() {
        val kafkaConsumer = KafkaConnector.generateKafkaConsumer {
            it.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
            it.setProperty(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "3")
            it.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group-mtopic")
        }
        kafkaConsumer.subscribe(listOf("topic-p3-t1", "topic-p3-t2"))

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
                    logger.info { "topic: ${record.topic()}, record key: ${record.key()}, partition: ${record.partition()}," +
                            "record offset: ${record.offset()}, record value: ${record.value()}" }
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