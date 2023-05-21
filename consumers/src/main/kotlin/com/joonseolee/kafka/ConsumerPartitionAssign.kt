package com.joonseolee.kafka

import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.TopicPartition
import java.time.Duration


class ConsumerPartitionAssign : TestCallback {
    override fun execute() {
        val topicName = "pizza-topic"
        val kafkaConsumer = KafkaConnector.generateKafkaConsumer {
            it.setProperty(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "60000")
            it.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group_pizza_assign_seek")
            it.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false")
        }

        val topicPartition = TopicPartition(topicName, 0)
        kafkaConsumer.assign(listOf(topicPartition))

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