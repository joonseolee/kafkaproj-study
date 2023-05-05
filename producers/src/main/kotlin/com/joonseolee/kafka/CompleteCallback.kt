package com.joonseolee.kafka

import mu.KotlinLogging
import org.apache.kafka.clients.producer.Callback
import org.apache.kafka.clients.producer.RecordMetadata
import java.lang.Exception

class CompleteCallback(var seq: Int) : Callback {
    override fun onCompletion(metadata: RecordMetadata?, exception: Exception?) {
        if (exception == null) {
            logger.info { "#### seq: ${this.seq}, partition: ${metadata?.partition()}, offset: ${metadata?.offset()}" }
        } else {
            logger.error { "에러 발생~~! ${exception.stackTrace}" }
        }
    }

    companion object {
        private val logger = KotlinLogging.logger("CompleteCallback")
    }
}