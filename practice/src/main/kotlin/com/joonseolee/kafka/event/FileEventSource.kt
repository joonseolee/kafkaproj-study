package com.joonseolee.kafka.event

import mu.KotlinLogging
import java.io.File
import java.io.RandomAccessFile

class FileEventSource(val updateInterval: Long,
                      val file: File,
                      val eventHandler: EventHandler
) : Runnable {
    private var keepRunning = true
    private var filePointer: Long = 0

    override fun run() {
        runCatching {
            while (this.keepRunning) {
                Thread.sleep(this.updateInterval)
                val len = this.file.length()

                if (this.filePointer == len) {
                    continue
                }

                if (len < this.filePointer) {
                    logger.info { "file was reset as filePointer is longer than file length" }
                }

                readAppendAndSend()
            }
        }.onFailure {
            logger.error { it }
        }
    }

    private fun readAppendAndSend() {
        val randomAccessFile = RandomAccessFile(this.file, "r")
        randomAccessFile.seek(this.filePointer)
        var line = randomAccessFile.readLine()
        while (line != null) {
            sendMessage(line)
            line = randomAccessFile.readLine()
        }

        this.filePointer = randomAccessFile.filePointer
    }

    private fun sendMessage(line: String) {
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

        val messageEvent = MessageEvent(key, value.toString())
        this.eventHandler.onMessage(messageEvent)
    }

    companion object {
        private val logger = KotlinLogging.logger("FileEventSource")
    }
}