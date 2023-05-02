package com.joonseolee.kafka

import io.kotest.core.spec.style.BehaviorSpec

class SimpleProducerTest : BehaviorSpec({
    given("SimpleProducer") {
        `when`("동작할때") {
            then("정상적으로 kafka 서버에 메시지 전송한다") {
                val simpleProducer = SimpleProducer()
                simpleProducer.execute("simpleProducer!")
            }
        }
    }
})
