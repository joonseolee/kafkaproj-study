package com.joonseolee.kafka.producer

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class FileProducerTest : BehaviorSpec({
    given("FileProducerTest") {
        `when`("호출할때") {
            then("성공한다") {
                val fileProducer = FileProducer()
                fileProducer.execute("Hello park")
            }
        }
    }
})
