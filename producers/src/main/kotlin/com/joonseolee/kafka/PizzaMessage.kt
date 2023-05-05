package com.joonseolee.kafka

import com.github.javafaker.Faker
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class PizzaMessage {
    private val pizzaNames = listOf(
        "Potato Pizza", "Cheese Pizza",
        "Cheese Garlic Pizza", "Super Supreme", "Peperoni"
    )

    private val pizzaShop = listOf(
        "A001", "B001", "C001",
        "D001", "E001", "F001", "G001", "H001", "I001", "J001", "K001", "L001", "M001", "N001",
        "O001", "P001", "Q001"
    )

    private fun getRandomValueFromList(list: List<String>, random: Random): String {
        val size = list.size
        val index: Int = random.nextInt(size)
        return list[index]
    }

    //random한 피자 메시지를 생성하고, 피자가게 명을 key로 나머지 정보를 value로 하여 Hashmap을 생성하여 반환.
    fun produce(faker: Faker, random: Random, id: Int): HashMap<String, String>? {
        val shopId = getRandomValueFromList(pizzaShop, random)
        val pizzaName = getRandomValueFromList(pizzaNames, random)
        val ordId = "ord$id"
        val customerName = faker.name().fullName()
        val phoneNumber = faker.phoneNumber().phoneNumber()
        val address = faker.address().streetAddress()
        val now = LocalDateTime.now()
        val message = String.format(
            "order_id:%s, shop:%s, pizza_name:%s, customer_name:%s, phone_number:%s, address:%s, time:%s",
            ordId,
            shopId,
            pizzaName,
            customerName,
            phoneNumber,
            address,
            now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.KOREAN))
        )

        val messageMap = HashMap<String, String>()
        messageMap["key"] = shopId
        messageMap["message"] = message
        return messageMap
    }
}