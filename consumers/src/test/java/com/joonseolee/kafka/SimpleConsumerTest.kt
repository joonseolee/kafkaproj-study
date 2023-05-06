package com.joonseolee.kafka

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class SimpleConsumerTest : BehaviorSpec({
    given("SimpleConsumerTest") {
        `when`("실행할때") {
            then("성공한다") {
                val simpleConsumer = SimpleConsumer()
                simpleConsumer.execute()
            }
        }
    }
})
