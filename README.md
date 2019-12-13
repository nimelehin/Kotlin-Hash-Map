# Kotlin-Hash-Map
Hash Map in Kotlin<br>
<br>
Commands in console:<br>
insert(key, value) or I(key, value)<br>
erase(key, value) or E(key, value)<br>
erase_by_key(key) or EK(key)<br>
erase_by_value(value) or EV(value)<br>
get_by_key(key) or GK(key)<br>
get_by_value(value) or GV(value)<br>
clear() or C()<br>
get_all() or ALL()<br>
find_by_key(key) or FK(key)<br>
find_by_value(value) or FV(value)<br>
<br>
HashMap's methods:<br>
insert(key, value) -> Boolean<br>
erase(key, value)<br>
eraseByKey(key)<br>
eraseByVal(value)<br>
getByKey(key) -> HashMapResponse<br>
getByVal(value) -> MutableList of HashMapResponse<br>
clear()<br>
getAll() -> MutableSet of Pair<String, String><br>
findByKey(key) -> MutableList of HashMapResponse<br>
findByVal(value) -> MutableList of HashMapResponse<br>
response2Pair(HashMapResponse) -> Pair<String?, String?> //return a Pair(key, value) or Pair(null, null) if error
print(HashMapResponse) -> String
print(Pair<String, String>) -> String
