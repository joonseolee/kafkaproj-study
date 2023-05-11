package com.joonseolee.kafka

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ConsumerWakeupTest : BehaviorSpec({
    given("ConsumerWakeupTest") {
        `when`("실행할때") {
            then("성공한다") {
                val consumerWakeup = ConsumerWakeup()
                consumerWakeup.execute()
            }
        }
    }
})
