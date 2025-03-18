
enum class Result(result: String) {
    SUCCESS("Success"),
    ERROR("Error");

    fun getResult(): String = name
}