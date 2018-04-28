import Hash
import HashMapCell
import HashMapResponse

class HashMapClose(tableSize: Int = 10000, private var useHashes: Int = 2) {
    private var zeroHashesArray: Array<Long> = arrayOf()
    private var hashFunctions: Array<Hash> = arrayOf()

    private var hashTableKey = Array(tableSize, { mutableListOf<HashMapCell>() })

    private val hashTableSize = tableSize

    init {
        val hashSettings = HashSettings()
        useHashes = Math.max(Math.min(useHashes, hashSettings.hashSettings.size), 1)

        for (index in 0 until useHashes) {
            hashFunctions += Hash(hashSettings.hashSettings[index])
            zeroHashesArray += 0
        }
    }

    private fun calcHash(str: String): Array<Long> {
        var result: Array<Long> = arrayOf()
        for (index in 0 until useHashes) {
            result += hashFunctions[index].getHash(str)
        }
        return result
    }

    private fun pos(hashesData: Array<Long>): Int {
        return (((hashesData[0] % hashTableSize) + hashTableSize) % hashTableSize).toInt()
    }

    fun insert(key: String, value: String): Boolean {
        val hashesData = calcHash(key)
        val index = pos(hashesData)

        hashTableKey[index].add(HashMapCell(hashesData, key, value))

        return true
    }

    fun get(key: String): MutableList<HashMapResponse> {
        val hashesData = calcHash(key)
        val index = pos(hashesData)
        val getArray = hashTableKey[index]
        val resultArray = mutableListOf<HashMapResponse>()
        for (item in getArray) {
            if (item.key == key){
                resultArray.add(HashMapResponse(true, item.key, item.data))
            }
        }
        return resultArray
    }

    fun erase(key: String, value: String) {
        val hashesData = calcHash(key)
        val index = pos(hashesData)
        var itemToRemove = listOf<Int>()
        var currIndex = 0
        for (item in hashTableKey[index]) {
            if (item.key == key && item.data == value){
                itemToRemove += currIndex
            }
            currIndex += 1
        }

        var wasDel = 0
        for (item in itemToRemove) {
            hashTableKey[index].removeAt(item - wasDel)
            wasDel+=1
        }
    }

    fun erase(key: String) {
        val hashesData = calcHash(key)
        val index = pos(hashesData)
        var itemToRemove = listOf<Int>()

        var currIndex = 0
        for (item in hashTableKey[index]) {
            if (item.key == key){
                itemToRemove += currIndex
            }
            currIndex += 1
        }

        var wasDel = 0
        for (item in itemToRemove) {
            hashTableKey[index].removeAt(item - wasDel)
            wasDel+=1
        }
    }

    private fun getAllIn(key: String): MutableList<HashMapResponse> {
        val hashesData = calcHash(key)
        val index = pos(hashesData)
        val getArray = hashTableKey[index]
        val resultArray = mutableListOf<HashMapResponse>()
        for (item in getArray) {
            resultArray.add(HashMapResponse(true, item.key, item.data))
        }
        return resultArray
    }}