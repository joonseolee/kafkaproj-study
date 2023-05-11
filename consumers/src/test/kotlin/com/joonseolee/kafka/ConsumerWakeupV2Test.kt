package com.joonseolee.kafka

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ConsumerWakeupV2Test : BehaviorSpec({
    given("ConsumerWakeupV2Test") {
        `when`("실행하면") {
            then("성공한다") {
                val consumerWakeupV2 = ConsumerWakeupV2()
                consumerWakeupV2.execute()
            }
        }
    }
})
