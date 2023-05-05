package com.joonseolee.kafka.partitioner

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class PizzaProducerCustomPartitionerTest : BehaviorSpec({
    given("PizzaProducerCustomPartitionerTest") {
        `when`("호출할때") {
            then("성공한다") {
                val pizzaProducerCustomPartitioner = PizzaProducerCustomPartitioner()
                pizzaProducerCustomPartitioner.execute("pizza order!")
            }
        }
    }
})
