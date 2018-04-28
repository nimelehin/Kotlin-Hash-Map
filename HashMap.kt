import Hash
import HashMapClose
import HashMapCell
import HashMapResponse

class HashMap(private val hashTableSize: Int = 10000, private var useHashes: Int = 2) {
    private var zeroHashesArray: Array<Long> = arrayOf()
    private var hashFunctions: Array<Hash> = arrayOf()
    private var hashTableKey = Array(hashTableSize, { HashMapCell(zeroHashesArray, "", "") })
    private val usedCellKey = Array(hashTableSize, { 0 })
    private val hashTableVal = HashMapClose(hashTableSize, useHashes)
    private var currentIteration = 1
    private var historyData: MutableSet<Pair<String, String>> = mutableSetOf()

    init {
        val hashSettings = HashSettings()
        useHashes = Math.max(Math.min(useHashes, hashSettings.hashSettings.size), 1)

        for (index in 0 until useHashes) {
            hashFunctions += Hash(hashSettings.hashSettings[index])
            zeroHashesArray += 0
        }

        hashTableKey = Array(hashTableSize, { HashMapCell(zeroHashesArray, "", "") })
    }

    private fun calcHash(str: String): Array<Long> {
        var result: Array<Long> = arrayOf()
        for (index in 0 until useHashes) {
            result += hashFunctions[index].getHash(str)
        }
        return result
    }

    private fun canStopSearchPositionInsert(hashTableCell: HashMapCell, usedCell: Int, hashesData: Array<Long>): Boolean {
        var equalHashes = true

        for (index in 0 until useHashes) {
            if (hashTableCell.hashesData[index] != hashesData[index]) {
                equalHashes = false
            }
        }

        return (usedCell != currentIteration) or (equalHashes)
    }

    private fun canStopSearchPositionGet(hashTableCell: HashMapCell, usedCell: Int, hashesData: Array<Long>): Boolean {
        var equalHashes = true

        for (index in 0 until useHashes) {
            if (hashTableCell.hashesData[index] != hashesData[index]) {
                equalHashes = false
            }
        }

        return (Math.abs(usedCell) != currentIteration) or (equalHashes)
    }

    private fun posInsert(hashesData: Array<Long>): Int {
        var index: Int = (((hashesData[0] % hashTableSize) + hashTableSize) % hashTableSize).toInt()
        val startPosition = index
        while (!canStopSearchPositionInsert(hashTableKey[index], usedCellKey[index], hashesData)) {
            index++
            if (index == hashTableSize) {
                index = 0
            }

            if (startPosition == index) {
                return - 1
            }
        }
        return index
    }

    private fun posGet(hashesData: Array<Long>): Int {
        var index: Int = (((hashesData[0] % hashTableSize) + hashTableSize) % hashTableSize).toInt()
        val startPosition = index
        while (!canStopSearchPositionGet(hashTableKey[index], usedCellKey[index], hashesData)) {
            index++
            if (index == hashTableSize) {
                index = 0
            }

            if (startPosition == index) {
                return - 1
            }
        }

        if (usedCellKey[index] != currentIteration) {
            index = - 1
        }
        return index
    }

    private fun insertByKey(key: String, value: String): Boolean {
        val hashesData = calcHash(key)

        val index = posInsert(hashesData)

        if (index == - 1) {
            return false
        }

        hashTableKey[index] = HashMapCell(hashesData, key, value)
        usedCellKey[index] = currentIteration

        return true
    }

    fun insert(key: String, value: String): Boolean {
        eraseByKey(key)
        historyData.add(Pair(key, value))
        hashTableVal.insert(value, key)
        return insertByKey(key, value)
    }

    fun getByKey(key: String): HashMapResponse {
        val hashesData = calcHash(key)

        val index = posGet(hashesData)

        if (index == - 1) {
            return HashMapResponse(false, "", "", "no such key")
        }

        return HashMapResponse(true, key, hashTableKey[index].data)
    }

    fun getByVal(value: String): MutableList<HashMapResponse> {
        val getArray = hashTableVal.get(value)
        var resultArray = mutableListOf<HashMapResponse>()

        for (item in getArray) {
            resultArray.add(HashMapResponse(item.success, item.value, item.key, item.error))
        }

        return resultArray
    }

    fun erase(key: String, value: String) {
        if (historyData.indexOf(Pair(key, value)) == -1) {
            return
        }
        val keyHashesData = calcHash(key)
        val keyPosInTable = posGet(keyHashesData)
        if (keyPosInTable != - 1) {
            hashTableKey[keyPosInTable] = HashMapCell(zeroHashesArray, "", "")
            usedCellKey[keyPosInTable] = -currentIteration
        }

        hashTableVal.erase(value, key)
        historyData.remove(Pair(key, value))
    }

    fun eraseByKey(key: String) {
        val hashesData = calcHash(key)
        val keyPosInTable = posGet(hashesData)
        if (keyPosInTable != - 1) {
            erase(key, hashTableKey[keyPosInTable].data)
        }
    }

    fun eraseByVal(value: String) {
        val respData = hashTableVal.get(value)

        for (item in respData) {
            erase(item.value, item.key)
        }
    }

    fun clear() {
        hashTableVal.clear()
        historyData.clear()
        currentIteration++
    }

    fun findByKey(key: String): MutableList<HashMapResponse> {
        var result = mutableListOf<HashMapResponse>()
        for (item in historyData) {
            if (item.first.indexOf(key) != - 1) {
                result.add(HashMapResponse(true, item.first, item.second))
            }
        }
        return result
    }

    fun findByVal(key: String): MutableList<HashMapResponse> {
        var result = mutableListOf<HashMapResponse>()
        for (item in historyData) {
            if (item.second.indexOf(key) != - 1) {
                result.add(HashMapResponse(true, item.first, item.second))
            }
        }
        return result
    }

    fun getAll() {
        println(historyData)
    }

    fun response2Pair(data: HashMapResponse): Pair<String?, String?> {
        if (data.success) {
            return Pair(data.key, data.value)
        }
        return Pair(null, null)
    }

    fun print(data: HashMapResponse) {
        if (data.success) {
            println("(${data.key} -> ${data.value})")
        } else {
            println(data.error)
        }
    }

    fun print(data: Pair<String?, String?>) {
        if ((data.first == null) or (data.second == null)) {
            println("No pair")
        } else {
            println("(${data.first} -> ${data.second})")
        }
    }
}