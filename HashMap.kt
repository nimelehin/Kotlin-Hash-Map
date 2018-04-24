import Hash

class HashMapCell(val hashesData: Array<Long>, val key: String, val data: String)
class HashMapResponse(val success: Boolean, val key: String, val value: String, val error: String = "")

class HashMap(tableSize: Int = 10000, private var useHashes: Int = 2) {
    private var zeroHashesArray: Array<Long> = arrayOf()
    private var hashFunctions: Array<Hash> = arrayOf()
    private var hashTableKey = Array(tableSize, { HashMapCell(zeroHashesArray, "", "") })
    private val usedCellKey = Array(tableSize, { 0 })
    private var hashTableVal = Array(tableSize, { HashMapCell(zeroHashesArray, "", "") })
    private val usedCellVal = Array(tableSize, { 0 })
    private val hashTableSize = tableSize
    private var currentIteration = 1
    private var historyData: MutableSet<Pair<String, String>> = mutableSetOf()

    init {
        val hashSettings = HashSettings()
        useHashes = Math.max(Math.min(useHashes, hashSettings.hashSettings.size), 1)

        for (index in 0 until useHashes) {
            hashFunctions += Hash(hashSettings.hashSettings[index])
            zeroHashesArray += 0
        }

        hashTableKey = Array(tableSize, { HashMapCell(zeroHashesArray, "", "") })
        hashTableVal = Array(tableSize, { HashMapCell(zeroHashesArray, "", "") })
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

    private fun posInsert(getBy: String, hashesData: Array<Long>): Int {
        var hashTable = Array(0, { HashMapCell(zeroHashesArray, "", "") })
        var usedCell = Array(0, { 0 })

        if (getBy == "key") {
            hashTable = hashTableKey
            usedCell = usedCellKey
        }
        if (getBy == "val") {
            hashTable = hashTableVal
            usedCell = usedCellVal
        }

        var index: Int = (hashesData[0] % hashTableSize).toInt()
        val startPosition = index
        while (! canStopSearchPositionInsert(hashTable[index], usedCell[index], hashesData)) {
            index ++
            if (index == hashTableSize) {
                index = 0
            }

            if (startPosition == index) {
                return - 1
            }
        }
        return index
    }

    private fun posGet(getBy: String, hashesData: Array<Long>): Int {
        var hashTable = Array(0, { HashMapCell(zeroHashesArray, "", "") })
        var usedCell = Array(0, { 0 })

        if (getBy == "key") {
            hashTable = hashTableKey
            usedCell = usedCellKey
        }
        if (getBy == "val") {
            hashTable = hashTableVal
            usedCell = usedCellVal
        }

        var index: Int = (hashesData[0] % hashTableSize).toInt()
        val startPosition = index
        while (! canStopSearchPositionGet(hashTable[index], usedCell[index], hashesData)) {
            index ++
            if (index == hashTableSize) {
                index = 0
            }

            if (startPosition == index) {
                return - 1
            }
        }

        if (usedCell[index] != currentIteration) {
            index = - 1
        }
        return index
    }

    private fun insertByKey(key: String, value: String): Boolean {
        val hashesData = calcHash(key)

        val index = posInsert("key", hashesData)

        if (index == - 1) {
            return false
        }

        hashTableKey[index] = HashMapCell(hashesData, key, value)
        usedCellKey[index] = currentIteration

        return true
    }

    private fun insertByVal(key: String, value: String): Boolean {
        val hashesData = calcHash(value)

        val index = posInsert("val", hashesData)

        if (index == - 1) {
            return false
        }

        hashTableVal[index] = HashMapCell(hashesData, value, key)
        usedCellVal[index] = currentIteration

        return true
    }

    fun insert(key: String, value: String): Boolean {
        eraseByKey(key)
        historyData.add(Pair(key, value))
        return insertByVal(key, value) && insertByKey(key, value)
    }

    fun getByKey(key: String): HashMapResponse {
        val hashesData = calcHash(key)

        val index = posGet("key", hashesData)

        if (index == - 1) {
            return HashMapResponse(false, "", "", "no such key")
        }

        return HashMapResponse(true, key, hashTableKey[index].data)
    }

    fun getByVal(value: String): HashMapResponse {
        val hashesData = calcHash(value)

        val index = posGet("val", hashesData)

        if (index == - 1) {
            return HashMapResponse(false, "", "", "no such value")
        }

        return HashMapResponse(true, hashTableVal[index].data, value)
    }

    fun erase(key: String, value: String, keyPosInTablePred: Int = - 1, valPosInTablePred: Int = - 1) {
        var keyPosInTable = keyPosInTablePred
        if (keyPosInTable == - 1) {
            val keyHashesData = calcHash(key)
            keyPosInTable = posGet("key", keyHashesData)
        }

        var valPosInTable = valPosInTablePred
        if (valPosInTable == - 1) {
            val valHashesData = calcHash(value)
            valPosInTable = posGet("val", valHashesData)
        }

        if (valPosInTable != - 1) {
            hashTableVal[valPosInTable] = HashMapCell(zeroHashesArray, "", "")
            usedCellVal[valPosInTable] = - currentIteration
        }
        if (keyPosInTable != - 1) {
            hashTableKey[keyPosInTable] = HashMapCell(zeroHashesArray, "", "")
            usedCellKey[keyPosInTable] = - currentIteration
        }

        historyData.remove(Pair(key, value))
    }

    fun eraseByKey(key: String) {
        val hashesData = calcHash(key)

        val keyPosInTable = posGet("key", hashesData)

        if (keyPosInTable != - 1) {
            val value = hashTableKey[keyPosInTable].data
            erase(key, value, keyPosInTable, - 1)
        }
    }

    fun eraseByVal(value: String) {
        val hashesData = calcHash(value)

        val valPosInTable = posGet("val", hashesData)

        if (valPosInTable != - 1) {
            val key = hashTableVal[valPosInTable].data
            erase(key, value, - 1, valPosInTable)
        }
    }

    fun clear() {
        historyData.clear()
        currentIteration ++
    }

    fun findByKey(key: String): Array<HashMapResponse> {
        var result = emptyArray<HashMapResponse>()
        for (item in historyData) {
            if (item.first.indexOf(key) != - 1) {
                result += HashMapResponse(true, item.first, item.second)
            }
        }
        return result
    }

    fun findByVal(key: String): Array<HashMapResponse> {
        var result = emptyArray<HashMapResponse>()
        for (item in historyData) {
            if (item.second.indexOf(key) != - 1) {
                result += HashMapResponse(true, item.first, item.second)
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