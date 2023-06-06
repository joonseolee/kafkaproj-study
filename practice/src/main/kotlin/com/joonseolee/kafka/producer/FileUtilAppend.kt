package com.joonseolee.kafka.producer

import com.github.javafaker.Faker
import java.io.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class FileUtilAppend {
    // 피자 메뉴를 설정. getRandomValueFromList()에서 임의의 피자명을 출력하는 데 사용.
    private val pizzaNames = listOf(
        "Potato Pizza", "Cheese Pizza",
        "Cheese Garlic Pizza", "Super Supreme", "Peperoni"
    )
//    private static final List<String> pizzaNames = List.of("고구마 피자", "치즈 피자",
//            "치즈 갈릭 피자", "슈퍼 슈프림", "페페로니 피자");

    //    private static final List<String> pizzaNames = List.of("고구마 피자", "치즈 피자",
    //            "치즈 갈릭 피자", "슈퍼 슈프림", "페페로니 피자");
    // 피자 가게명을 설정. getRandomValueFromList()에서 임의의 피자 가게명을 출력하는데 사용.
    private val pizzaShop = listOf(
        "A001", "B001", "C001",
        "D001", "E001", "F001", "G001", "H001", "I001", "J001", "K001", "L001", "M001", "N001",
        "O001", "P001", "Q001"
    )

    private var orderSeq = 5000
    fun FileUtilAppend() {}

    //인자로 피자명 또는 피자가게 List와 Random 객체를 입력 받아서 random한 피자명 또는 피자 가게 명을 반환.
    private fun getRandomValueFromList(list: List<String>, random: Random): String {
        val size = list.size
        val index: Int = random.nextInt(size)
        return list[index]
    }

    //random한 피자 메시지를 생성하고, 피자가게 명을 key로 나머지 정보를 value로 하여 Hashmap을 생성하여 반환.
    fun produce_msg(faker: Faker, random: Random, id: Int): HashMap<String, String> {
        val shopId = getRandomValueFromList(pizzaShop, random)
        val pizzaName = getRandomValueFromList(pizzaNames, random)
        val ordId = "ord$id"
        val customerName = faker.name().fullName()
        val phoneNumber = faker.phoneNumber().phoneNumber()
        val address = faker.address().streetAddress()
        val now = LocalDateTime.now()
        val message = String.format(
            "%s, %s, %s, %s, %s, %s, %s",
            ordId,
            shopId,
            pizzaName,
            customerName,
            phoneNumber,
            address,
            now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.KOREAN))
        )
        //System.out.println(message);
        val messageMap = HashMap<String, String>()
        messageMap["key"] = shopId
        messageMap["message"] = message
        return messageMap
    }

    fun writeMessage(filePath: String?, faker: Faker, random: Random) {
        try {
            val file = File(filePath)
            if (!file.exists()) {
                file.createNewFile()
            }
            val fileWriter = FileWriter(file, true)
            val bufferedWriter = BufferedWriter(fileWriter)
            val printWriter = PrintWriter(bufferedWriter)
            for (i in 0..49) {
                val message = produce_msg(faker, random, orderSeq++)
                printWriter.println(message["key"] + "," + message["message"])
            }
            printWriter.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}