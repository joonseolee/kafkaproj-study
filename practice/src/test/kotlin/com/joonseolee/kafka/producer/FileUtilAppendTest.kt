package com.joonseolee.kafka.producer

import com.github.javafaker.Faker
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.util.*

class FileUtilAppendTest : BehaviorSpec({
    given("FileUtilAppendTest") {
        `when`("실행하면") {
            then("성공한다") {
                val fileUtilAppend = FileUtilAppend()
                // seed값을 고정하여 Random 객체와 Faker 객체를 생성.
                val seed: Long = 2022
                val random = Random(seed)
                val faker: Faker = Faker.instance(random)
                //여러분의 절대경로 위치로 변경해 주세요.
                val filePath = "/Users/1004883/Documents/github-repositories/kafkaproj-study/practice/src/main/resources/pizza_append.txt"
                // 100회 반복 수행.
                for (i in 0..999) {
                    //50 라인의 주문 문자열을 출력
                    fileUtilAppend.writeMessage(filePath, faker, random)
                    println("###### iteration:$i file write is done")
                    try {
                        //주어진 기간동안 sleep
                        Thread.sleep(10000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
})
