import java.lang.Math.*
import HashMapClose
import java.util.*


val random = Random()

fun rand(from: Int, to: Int) : Int {
    return random.nextInt(to - from) + from
}

fun test() {
    val n = rand(100000, 300000)
    println("N size = $n")
    val mm = HashMap(300, 1)
    var historyData: MutableSet<Pair<String, String>> = mutableSetOf()

    var asked = 0
    var added = 0
    var cantadd = 0
    var delled = 0
    var wrongAns = 0

    for (index in 0 until n) {
        if (index % 1000 == 0){
            println("Step $index")
        }
        val type = rand(1, 4)
        if (type == 1) { //insert
            val key = rand(0, 100).toString()
            val value = rand(0, 100).toString()
            val result = mm.insert(key, value)
            if (!result) {
                cantadd++
            } else {
                var del = Pair("", "")
                for (item in historyData) {
                    if (item.first == key) {
                        del = item
                    }
                }
                historyData.remove(del)
                historyData.add(Pair(key, value))
            }
            added++
        }
        if (type == 2) { //erase
            val key = rand(0, 100).toString()
            val value = rand(0, 100).toString()
            //mm.erase(key, value)
            //historyData.remove(Pair(key, value))
            delled++
        }
        if (type == 3) { //Get by Key
            val key = rand(0, 100).toString()
            var pp = mm.response2Pair(mm.getByKey(key))
            if (pp.first == null){
                for (item in historyData){
                    if (item.first == key){
                        wrongAns++
                        println("Er1")
                    }
                }
            }
            else if (historyData.indexOf(pp) == -1){
                wrongAns++
            }
            asked++;
        }
        if (type == 4) { //Get by Value
            val value = rand(0, 100).toString()
            val lol = mm.getByVal(value)
            for (item in lol) {
                if (historyData.indexOf(mm.response2Pair(item)) == -1) {
                    wrongAns++
                    println("Er2")
                }
            }
            var count = 0
            for (item in historyData){
                if (item.second == value){
                    count++;
                }
            }
            if (count != lol.size){
                wrongAns++
                println("Er3")
            }
            asked++;
        }
    }

    println("Cheked $asked times")
    println("Added $added times")
    println("Added error $cantadd times")
    println("Erased $delled times")
    println("Wrong Ans $wrongAns times")
}

fun main(args: Array<String>) {

    test()

}
