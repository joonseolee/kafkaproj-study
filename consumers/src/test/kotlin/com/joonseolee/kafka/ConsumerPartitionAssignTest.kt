package com.joonseolee.kafka

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ConsumerPartitionAssignTest : BehaviorSpec({
    given("ConsumerPartitionAssignTest") {
        `when`("실행하면") {
            then("성공한다") {
                val consumerPartitionAssign = ConsumerPartitionAssign()
                consumerPartitionAssign.execute()
            }
        }
    }
})
