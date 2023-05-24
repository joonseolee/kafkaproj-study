package com.joonseolee.kafka.producer

interface TestCallback {
    fun execute(message: String)
}