import java.lang.Math.*
import HashMapClose


fun main(args: Array<String>) {

    val mm = HashMapClose(2, 1)
    while (true) {
        println("-----------------------------------")
        print("Choose an action: ")
        val s = readLine() !!
        if (s == "insert") {
            print("Key: ")
            val key = readLine() !!
            print("Value: ")
            val value = readLine() !!
            val result = mm.insert(key, value)

            if (result) {
                println("Successfully added")
            } else {
                println("Error (maybe there is no empty place)")
            }
        }
        if (s == "erase") {
            println("Erase Data (by key)")
            print("Key: ")
            val key = readLine() !!

            mm.erase(key)
        }
        if (s == "get") {
            println("Get data by key")
            print("Key: ")
            val key = readLine() !!
            val response = mm.get(key)

            for (item in response){
                println("key: ${item.key} value: ${item.value}")
            }
        }
//        if (s == "all") {
//            println("(TEST) ALL")
//            print("Key: ")
//            val key = readLine() !!
//            val response = mm.getAllIn(key)
//
//            for (item in response){
//                println("key: ${item.key} value: ${item.value}")
//            }
//        }
    }
}