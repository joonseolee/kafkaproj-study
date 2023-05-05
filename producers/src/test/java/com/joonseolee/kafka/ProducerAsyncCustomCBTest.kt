package com.joonseolee.kafka

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ProducerAsyncCustomCBTest : BehaviorSpec({
    given("ProducerAsyncCustomCBTest") {
        `when`("동작할때") {
            then("성공한다") {
                val producerAsyncCustomCB = ProducerAsyncCustomCB()
                producerAsyncCustomCB.execute("producerAsyncCustomCBvv")
            }
        }
    }
})