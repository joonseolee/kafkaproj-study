package com.joonseolee.kafka

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ConsumerCommitTest : BehaviorSpec({
    given("ConsumerCommitTest") {
        `when`("실행하면") {
            then("성공한다") {
                val consumerCommit = ConsumerCommit()
                consumerCommit.execute()
            }
        }
    }
})
