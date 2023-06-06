package com.joonseolee.kafka.producer

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class FileAppendProducerTest : BehaviorSpec({
    given("FileAppendProducerTest") {
        `when`("execute") {
            then("success") {
                val fileAppendProducer = FileAppendProducer()
                fileAppendProducer.execute("")
            }
        }
    }
})
