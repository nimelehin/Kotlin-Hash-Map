import java.lang.Math.*
import HashMap


fun main(args: Array<String>) {

    val mm = HashMap(10000, 5)
    while (true) {
        println("-----------------------------------")
        print("Choose an action: ")
        val s = readLine() !!
        if (s == "insert" || s == "I") {
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
        if (s == "erase" || s == "E") {
            println("Erase Data (by key & value)")
            print("Key: ")
            val key = readLine() !!
            print("Value: ")
            val value = readLine() !!

            mm.erase(key, value)
        }
        if (s == "erase_by_key" || s == "EK") {
            println("Erase Data (by key)")
            print("Key: ")
            val key = readLine() !!

            mm.eraseByKey(key)
        }
        if (s == "get_by_key" || s == "GK") {
            println("Get data by key")
            print("Key: ")
            val key = readLine() !!

            val response = mm.getByKey(key)
            val respP = mm.response2Pair(response)

            mm.print(respP)

        }
        if (s == "erase_by_value" || s == "EV") {
            println("[MESS] - Del Data (by value)")
            print("Value: ")
            val key = readLine() !!
            mm.eraseByVal(key)
        }
        if (s == "get_by_value" || s == "GV") {
            println("[MESS] - Get Data (by value)")
            print("Value: ")
            val key = readLine() !!
            val result = mm.getByVal(key)
            for (item in result) {
                mm.print(item)
            }
        }
        if (s == "clear"  || s == "C") {
            println("Successfully cleared")
            mm.clear()
        }
        if (s == "get_all"  || s == "ALL") {
            var gg = mm.getAll()
            for (item in gg){
                println(mm.print(item))
            }
        }
        if (s == "find_by_key" || s == "FK") {
            println("Find by key")
            print("Key: ")
            val key = readLine() !!
            val result = mm.findByKey(key)
            println("Found:")
            for (item in result) {
                mm.print(item)
            }
        }
        if (s == "find_by_value" || s == "FV") {
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