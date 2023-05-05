package com.joonseolee.kafka

import io.kotest.core.spec.style.BehaviorSpec

class SimpleProducerSyncTest : BehaviorSpec({
    given("simpleProducerSync") {
        `when`("동작할때") {
            then("성공한다") {
                val simpleProducerSync = SimpleProducerSync()
                simpleProducerSync.execute("simpleProducerSync~~...")
            }
        }
    }
})
