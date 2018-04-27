class HashMapCell(val hashesData: Array<Long>, val key: String, val data: String)
class HashMapResponse(val success: Boolean, val key: String, val value: String, val error: String = "")