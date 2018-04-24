class HashSetting(val power: Long, val module: Long)

class HashSettings {
    var hashSettings: Array<HashSetting> = arrayOf()

    init {
        hashSettings += HashSetting(149, 1000000007)
        hashSettings += HashSetting(211, 1000000349)
        hashSettings += HashSetting(173, 1000000181)
        hashSettings += HashSetting(229, 1000000207)
        hashSettings += HashSetting(251, 1000000021)
    }
}

class Hash(private val settings: HashSetting) {
    private var pPow = LongArray(0)
    private var currSize = 1
    private var mod: Long = 1

    fun getHash(str: String): Long {
        var pPower: Long = 1
        var hash: Long = 0
        for (index in 0 until str.length) {
            hash = (hash + (str[index] - '?' + 1) * pPower) % settings.module
            pPower = (pPower * settings.power) % settings.module
        }

        return hash
    }
}
