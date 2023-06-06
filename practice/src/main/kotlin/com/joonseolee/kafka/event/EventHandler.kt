package com.joonseolee.kafka.event

interface EventHandler {
    fun onMessage(messageEvent: MessageEvent)
}