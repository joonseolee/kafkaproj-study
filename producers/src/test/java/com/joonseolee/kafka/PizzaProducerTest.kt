package com.joonseolee.kafka

import io.kotest.core.spec.style.BehaviorSpec

class PizzaProducerTest : BehaviorSpec({
    given("PizzaProducerTest") {
        `when`("호출할때") {
            then("성공한다") {
                val pizzaProducer = PizzaProducer()
                pizzaProducer.execute("pizza 테스트")
            }
        }
    }
})
