package com.joonseolee.kafka

import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration
import kotlin.math.log


class ConsumerCommit : TestCallback {
    override fun execute() {
        val topicName = "pizza-topic"
        val kafkaConsumer = KafkaConnector.generateKafkaConsumer {
            it.setProperty(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "60000")
            it.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group_03")
            it.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false")
        }
        kafkaConsumer.subscribe(listOf(topicName))

        val currentThread = Thread.currentThread()
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                logger.info { "main program starts to the exit by calling wakeup" }
//                kafkaConsumer.wakeup()

                runCatching {
                    currentThread.join()
                }.onFailure { logger.error { "occurred an error $it" } }
            }
        })

        // pollAutoCommit(kafkaConsumer)
        pollCommitSync(kafkaConsumer)
    }

    private fun pollCommitSync(kafkaConsumer: KafkaConsumer<String, String>) {
        var loopCount = 0
        runCatching {
            while (true) {
                val consumerRecords = kafkaConsumer.poll(Duration.ofMillis(1000))
                logger.info { ">>> looCount: ${loopCount++} consumerRecords count: ${consumerRecords.count()}" }
                for (record in consumerRecords) {
                    logger.info { "record key: ${record.key()}, partition: ${record.partition()}," +
                            "record offset: ${record.offset()}, record value: ${record.value()}" }
                }

                runCatching {
                    if (consumerRecords.count() < 1) {
                        return
                    }

                    kafkaConsumer.commitSync()
                    logger.info { "commit sync has been called" }
                }.onFailure { logger.error { it } }
            }
        }.onFailure { logger.error { "error while polling $it" } }
            .also {
                logger.info { "get into the also step" }
                kafkaConsumer.close()
            }
    }

    private fun pollAutoCommit(kafkaConsumer: KafkaConsumer<String, String>) {
        var loopCount = 0
        runCatching {
            while (true) {
                val consumerRecords = kafkaConsumer.poll(Duration.ofMillis(1000))
                logger.info { ">>> looCount: ${loopCount++} consumerRecords count: ${consumerRecords.count()}" }
                for (record in consumerRecords) {
                    logger.info { "record key: ${record.key()}, partition: ${record.partition()}," +
                            "record offset: ${record.offset()}, record value: ${record.value()}" }
                }

                runCatching {
                    logger.info { "main thread is sleeping ${10000}" }
                    Thread.sleep(10000L)
                }.onFailure { "error 발생 $it" }
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