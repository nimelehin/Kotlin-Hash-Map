import java.lang.Math.*
import HashMap


fun main(args: Array<String>) {

    val mm = HashMap(10000, 5)
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
            println("Erase Data (by key & value)")
            print("Key: ")
            val key = readLine() !!
            print("Value: ")
            val value = readLine() !!

            mm.erase(key, value)
        }
        if (s == "erase_by_key") {
            println("Erase Data (by key)")
            print("Key: ")
            val key = readLine() !!

            mm.eraseByKey(key)
        }
        if (s == "get_by_key") {
            println("Get data by key")
            print("Key: ")
            val key = readLine() !!

            val response = mm.getByKey(key)
            val respP = mm.response2Pair(response)

            mm.print(respP)

            //println(mm.printKeyVal(mm.getByKey(key)))
        }
        if (s == "erase_by_value") {
            println("[MESS] - Del Data (by value)")
            print("Value: ")
            val key = readLine() !!
            mm.eraseByVal(key)
        }
        if (s == "get_by_value") {
            println("Get data by value")
            print("Value: ")
            val key = readLine() !!

            mm.print(mm.getByVal(key))
        }
        if (s == "clear") {
            println("Successfully cleared")
            mm.clear()
        }
        if (s == "get_all") {
            mm.getAll()
        }
        if (s == "find_by_key") {
            println("Find by key")
            print("Key: ")
            val key = readLine() !!
            val result = mm.findByKey(key)
            println("Found:")
            for (item in result) {
                mm.print(item)
            }
        }
        if (s == "find_by_value") {
            println("Find by value")
            print("Value: ")
            val key = readLine() !!
            val result = mm.findByVal(key)
            println("Found:")
            for (item in result) {
                mm.print(item)
            }
        }
    }
}