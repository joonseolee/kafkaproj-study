package com.joonseolee.kafka

import io.kotest.core.spec.style.BehaviorSpec

class ConsumerMTopicRebalancerTest : BehaviorSpec({
    given("ConsumerMTopicRebalancerTest") {
        `when`("실행하면") {
            then("성공한다") {
                val counterbalance = ConsumerMTopicRebalancer()
                counterbalance.execute()
            }
        }
    }
})
