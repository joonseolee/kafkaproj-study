package com.joonseolee.kafka

import io.kotest.core.spec.style.BehaviorSpec

class SimpleProducerAsyncTest : BehaviorSpec({
    given("SimpleProducerAsync") {
        `when`("동작할때") {
            then("성공한다") {
                val simpleProducerAsync = SimpleProducerAsync()
                simpleProducerAsync.execute("simpleProducerAsync 테스트!")
            }
        }
    }
})
