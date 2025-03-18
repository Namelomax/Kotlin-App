import Client.Companion.cl
import Client.Companion.line
import Client.Companion.refactor
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import java.io.File
class ConsoleRead {
    var command: String =""
    val commandsArray = JsonArray()
    val executedScripts = mutableSetOf<String>()
    private fun checkParam(txt: String) {
        if (txt.isNotEmpty()) {
                line = txt
            if (command.contains("script")){
                executeScript("$line.txt",commandsArray)
            }
        }
    }
    fun read(txt: String) {
        val splittedcommand = txt.split(" ")
        command = splittedcommand[0]
        checkParam(splittedcommand.drop(1).joinToString(","))
    }
    fun executeScript(script: String,jsonArray: JsonArray) {
        val scriptFile = File(script)
        val Path = scriptFile.canonicalPath

        if (executedScripts.contains(Path)) {
            println("Skipping script execution to prevent recursion: $Path")
            return
        }

        if (scriptFile.exists() && scriptFile.isFile && scriptFile.canRead()) {
            executedScripts.add(Path)

            scriptFile.forEachLine { line ->
                val trimmedLine = line.trim()

                if (trimmedLine.startsWith("execute_script")) {
                    val parts = trimmedLine.split("\\s+".toRegex())
                    val nestedScript = parts[1]
                    executeScript(nestedScript, jsonArray)
                } else {
                    val commandJson = JsonObject()
                    commandJson.addProperty("command", trimmedLine)
                    jsonArray.add(commandJson)
                    println(jsonArray)
                }
            }

            executedScripts.remove(Path)
        }
    }
    //
    fun checkCommand(command: String): Boolean {
        return if (cl.commands.contains(command)) {
            if (command.contains("add") || command.contains("update")){
                val fillCollection =FillCollection()
            fillCollection.fillCollection()}
            true
        } else {
            println("No such command")
            false
        }
    }
class FillCollection {

    fun fillCollection() {
        var toothPickCheck = true
        var realHeroCheck = true
        var soundtrackCheck = true
        var minutesCheck = true
        var moodCheck = true
        var carCheck = true
        var xcoordinateCheck = true
        var ycoordinateCheck = true
        var nameCheck = true
        var speedCheck = true
        val ar_coord = mutableListOf<Number>()
        println("Enter name")
        while (nameCheck) {
            val line = readln().trim()
            if (line.matches(Regex("[^a-zA-Z]")) || line == "") {
                println("Please enter a correct name.")
                continue
            } else {
                refactor["name"]=line
                nameCheck = false
            }
        }
        println("Enter X-axis coordinates")
        while (xcoordinateCheck) {
            val coordline = readln().trim()
            if (coordline.toDoubleOrNull() == null) {
                println("Please enter number.")
                continue
            } else {
                val x: Number = coordline.toDouble()
                ar_coord.add(x)
                xcoordinateCheck = false
            }
        }

        println("Enter Y-axis coordinates")
        while (ycoordinateCheck) {
            val coordline = readln().trim()
            if (coordline.toDoubleOrNull() == null) {
                println("Please enter number.")
                continue
            } else {
                val y: Number = coordline.toDouble()
                ar_coord.add(y)
                ycoordinateCheck = false
            }
        }
        refactor["coordinates"] = ar_coord
        println("Has toothPick? (yes/no)")
        while (toothPickCheck) {
            val line = readln().trim()
            if (line == "yes") {
                refactor["hasToothpick"] = true
                toothPickCheck = false
            } else if (line == "no") {
                refactor["hasToothpick"] = false
                toothPickCheck = false
            } else {
                println("should be yes or no")
                continue
            }
        }
        println("Is real hero? (yes/no)")
        while (realHeroCheck) {
            val line = readln().trim()
            if (line == "yes") {
                refactor["realHero"] = true
                realHeroCheck = false
            } else if (line == "no") {
                refactor["realHero"] = false
                realHeroCheck = false
            } else {
                println("should be yes or no")
                continue
            }
        }
        println("Enter impactSpeed")
        while (speedCheck) {
            val line = readln().trim()
            if (line.toLongOrNull() == null) {
                println("Please enter a valid number.")
                continue
            } else {
                refactor["impactSpeed"] = line.toLong()
                speedCheck = false
            }
        }
        println("Enter soundtrackName")
        while (soundtrackCheck) {
            val line = readln().trim()
            if (line.matches(Regex("[^a-zA-Z]")) || line == "") {
                println("Please enter a non-numeric value.")
                continue
            } else {
                refactor["soundtrackName"] = line
                soundtrackCheck = false
            }
        }
        println("Enter minutesOfWaiting")
        while (minutesCheck) {
            val line = readln().trim()
            if (line.toDoubleOrNull() == null) {
                println("Please enter a double number.")
                continue
            } else {
                refactor["minutesOfWaiting"] = line.toDouble()
                minutesCheck = false
            }
        }
        println("Enter mood (longing/sadness/sorrow)")
        while (moodCheck) {
            val line = readln().trim().uppercase()
            for (mood in Mood.values()) {
                if (mood.name == line) {
                    moodCheck = false
                    refactor["mood"] = line
                    break
                }
            }
            if (moodCheck) {
                println("No such mood")
                continue
            }
        }
        println("Enter car")
        while (carCheck) {
            val line = readln().trim()
            if (line.matches(Regex("[^a-zA-Z]")) || line == "") {
                println("Please enter a non-numeric value.")
                continue
            } else {
                refactor["car"] = line
                carCheck = false
            }
        }
    }
}
    }