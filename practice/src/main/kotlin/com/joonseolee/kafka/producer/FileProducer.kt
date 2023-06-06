package com.joonseolee.kafka.producer

import mu.KotlinLogging
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.io.BufferedReader
import java.io.FileReader

class FileProducer : TestCallback {
    override fun execute(message: String) {
        val topicName = "file-topic"
        val kafkaProducer = KafkaConnector.generateKafkaProducer()
        val filePath = "/Users/1004883/Documents/github-repositories/kafkaproj-study/practice/src/main/resources/pizza_sample.txt"


        sendFileMessage(kafkaProducer, topicName, filePath)

        kafkaProducer.close()
    }

    private fun sendFileMessage(kafkaProducer: KafkaProducer<String, String>, topicName: String, filePath: String) {
        runCatching {
            val fileReader = FileReader(filePath)
            val bufferedReader = BufferedReader(fileReader)

            var line = bufferedReader.readLine()
            while (line != null) {
                val tokens = line.split(",")
                val key = tokens[0]
                val value = StringBuffer()

                for (i in 1 until tokens.size) {
                    if (i != (tokens.size - 1)) {
                        value.append(tokens[i] + ",")
                        continue
                    }

                    value.append((tokens[i]))
                }

                sendMessage(kafkaProducer, topicName, key, value.toString())
                line = bufferedReader.readLine()
            }

        }.onFailure {
            logger.error { "error has been occurred $it" }
        }
    }

    private fun sendMessage(
        kafkaProducer: KafkaProducer<String, String>,
        topicName: String,
        key: String,
        value: String
    ) {
        val producerRecord = ProducerRecord(topicName, key, value)
        logger.info { "key: $key, value: $value" }

        kafkaProducer.send(producerRecord) { metadata, exception ->
            if (exception == null) {
                logger.info {
                    "#### record metadata received ####\n" +
                            "partition: ${metadata.partition()}\n" +
                            "offset: ${metadata.offset()}\n" +
                            "timestamp: ${metadata.timestamp()}"
                }
            } else {
                logger.error { "에러 발생~~! ${exception.stackTrace}" }
            }
        }
    }

    companion object {
        private val logger = KotlinLogging.logger("FileProducer")
    }
}