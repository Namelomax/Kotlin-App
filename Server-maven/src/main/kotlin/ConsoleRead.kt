


class ConsoleRead {

    var command: String =""
    private fun checkParam(txt: String) {
        if (txt.isNotEmpty()) {
            if (txt.toDoubleOrNull()!=null) {
                line = txt
            }
        }
    }
    fun read(txt: String) {
        val splittedcommand = txt.split(" ")
        command = splittedcommand[0]
        checkParam(splittedcommand.drop(1).joinToString(","))
    }

    fun checkCommand(command: String): Boolean {
        return if (cl.commands.containsKey(command)) {
            true
        } else {
            println("No such command")
            false
        }
    }
}