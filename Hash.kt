import HashSettings

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
