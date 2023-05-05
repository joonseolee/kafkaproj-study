package com.joonseolee.kafka

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ProducerAsyncWithKeyTest : BehaviorSpec({
    given("ProducerAsyncWithKeyTest") {
        `when`("동작할때") {
            then("성공한다") {
                val producerAsyncWithKey = ProducerAsyncWithKey()
                producerAsyncWithKey.execute("producerAsyncWithKey 테스트...")
            }
        }
    }
})