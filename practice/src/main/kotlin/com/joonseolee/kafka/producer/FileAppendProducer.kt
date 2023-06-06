package com.joonseolee.kafka.producer

import com.joonseolee.kafka.event.FileEventHandler
import com.joonseolee.kafka.event.FileEventSource
import mu.KotlinLogging
import java.io.File

class FileAppendProducer : TestCallback {
    override fun execute(message: String) {
        val topicName = "file-topic"
        val kafkaProducer = KafkaConnector.generateKafkaProducer()
        val file = File("/Users/1004883/Documents/github-repositories/kafkaproj-study/practice/src/main/resources/pizza_append.txt")
        val fileEventHandler = FileEventHandler(kafkaProducer, topicName, true)
        val fileEventSource = FileEventSource(3000, file, fileEventHandler)
        val thread = Thread(fileEventSource)
        thread.start()

        runCatching {
            thread.join()
        }.onFailure { logger.error { it } }
    }

    companion object {
        private val logger = KotlinLogging.logger("FileAppendProducer")
    }
}