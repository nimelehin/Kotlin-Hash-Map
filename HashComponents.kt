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
