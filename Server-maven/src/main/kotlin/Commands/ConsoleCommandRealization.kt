package Commands
import BaseClasses.Coordinates
import BaseClasses.HumanBeing
import BaseClasses.Mood
import BaseClasses.Car
import ConsoleRead
import Second_sem.lab5.Kotlin.BaseClasses.makeLocalDateTime
import cl
import com.google.gson.internal.LinkedTreeMap
import dateOfInitialization
import line
import invoker
import listOfData
import listOfHumanBeing
import ongoing
import scriptlist
import response
import java.util.*
import java.io.File
import Result
import com.google.gson.JsonArray
import connection
import refactor
import username1
import java.sql.SQLException
import java.time.LocalDateTime
import java.sql.Types
class HelpCommand : Command(){
    private fun getHelp():String{
        val txt = """
            help : вывести справку по доступным командам
            info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
            show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
            add {element} : добавить новый элемент в коллекцию
            update id {element} : обновить значение элемента коллекции, id которого равен заданному
            remove_by_id id : удалить элемент из коллекции по его id
            clear : очистить коллекцию
            save : сохранить коллекцию в файл
            execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
            exit : завершить программу (без сохранения в файл)
            remove_first : удалить первый элемент из коллекции
            remove_head : вывести первый элемент коллекции и удалить его
            add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
            count_by_minutes_of_waiting minutesOfWaiting : вывести количество элементов, значение поля minutesOfWaiting которых равно заданному
            count_less_than_mood mood : вывести количество элементов, значение поля mood которых меньше заданного
            print_ascending : вывести элементы коллекции в порядке возрастания
            """.trimIndent();
        return txt
    }

    override fun execute() {
        response=getHelp()
    }
}
class InfoCommand : Command(){
    private fun getInfo():String{
        val txt = """
            Class of collection: LinkedList
            Count of units: ${listOfHumanBeing.size}}
            Date of initialization: $dateOfInitialization
            """.trimIndent()
        return txt
    }

    override fun execute() {
        response=getInfo()
    }
}

class ShowCommand : Command() {
    var txt: String =""
    private fun getShow():String{
        txt = listOfHumanBeing.toString()
        return txt
    }

    override fun execute() {
        response=getShow()
    }
}

class AddCommand : Command() {
    //constructor(list: List<Any?>): this(makeLinkedTreeMap(list))

    override fun execute() {
        val unit = HumanBeing(refactor)
        println(unit)
        listOfData.add(unit.makeLinkedTreeMap())
        println(unit.makeLinkedTreeMap())
        listOfHumanBeing.add(unit)
        response=Result.SUCCESS.getResult()
    }
}   
class UpdateCommand : Command() {

    override fun execute() {
        if (line != "0.0") {
            val idToUpdate = line.toIntOrNull()
            println(idToUpdate)
            if (idToUpdate != null) {
                val matchingUnit = listOfHumanBeing.find { it.id == idToUpdate }
                println(listOfHumanBeing)
                if (matchingUnit != null) {
                    if (matchingUnit.owner == username1) {
                        val coordinatesArray = refactor["coordinates"] as JsonArray
                        val coordinatesList = coordinatesArray.map { it.asNumber }.toList()
                        val newUnit = HumanBeing(
                            id = idToUpdate,
                            name = refactor["name"].toString(),
                            coordinates = Coordinates(),
                            creationDate = makeLocalDateTime(
                                refactor["creationDate"] as? List<Int> ?: listOf(
                                    LocalDateTime.now().year, LocalDateTime.now().monthValue,
                                    LocalDateTime.now().dayOfMonth, LocalDateTime.now().hour,
                                    LocalDateTime.now().minute, LocalDateTime.now().second
                                )
                            ),
                            realHero = refactor["realHero"].toString().toBoolean(),
                            hasToothpick = refactor["hasToothpick"].toString().toBoolean(),
                            impactSpeed = refactor["impactSpeed"].toString().toLong(),
                            soundtrackName = refactor["soundtrackName"].toString(),
                            minutesOfWaiting = refactor["minutesOfWaiting"].toString().toDouble(),
                            mood = (refactor["mood"].toString().replace("\"", "").replace("\"", "")).let {
                                Mood.valueOf(
                                    it
                                )
                            },
                            car = Car(refactor["car"].toString())
                        )
                        listOfHumanBeing.add(newUnit)
                        listOfHumanBeing.remove(matchingUnit)
                        println(listOfHumanBeing)
                        response = Result.SUCCESS.getResult()
                    } else {
                        response = "There is no unit with such id. Please, try another id"
                    }

                } else {
                    response = "Invalid id"
                }
            } else {
                response = "Incorrect input."
            }
        }else{
            println("not owner")
        }
    }
}
class RemoveByIdCommand : Command() {

    override fun execute() {
        try {
            val idToDelete = line.toInt()
            val query = "DELETE FROM people WHERE id = ?"

            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.setInt(1, idToDelete) // Устанавливаем значение параметра
            val rowsAffected = preparedStatement.executeUpdate()

            if (rowsAffected > 0) {
                response = "Successfully removed unit with ID $idToDelete from the database."
            } else {
                response = "No record found with ID $idToDelete in the database."
            }
        } catch (e: NumberFormatException) {
            response = "Invalid ID"
        } catch (e: SQLException) {
            println("SQL Exception: ${e.message}")
            response = "Error while removing ${e.message}"
        }
    }
}
class ClearCommand : Command(){
    override fun execute() {
        try {
            val query = "DELETE FROM people"
            val preparedStatement = connection.prepareStatement(query)
            val rowsAffected = preparedStatement.executeUpdate()
            if (rowsAffected > 0) {
                listOfData.clear()
                listOfHumanBeing.clear()
                response=successResult().toString()
            } else {
                response = errorResult().toString()
            }
        } catch (e: SQLException) {
            response = "Error while clearing  ${e.message}"
        }
    }
}
class SaveCommand : Command(){
    override fun execute() {
        val query = "DELETE FROM people"
        val preparedStatement = connection.prepareStatement(query)
        val rowsAffected = preparedStatement.executeUpdate()
        if (rowsAffected > 0) {
            listOfData.clear()
            listOfHumanBeing.clear()
            response=successResult().toString()
        } else {
            response = errorResult().toString()
        }
        val list = LinkedList<LinkedTreeMap<String, Any?>>()
        for (unit in listOfHumanBeing){
            list.add(unit.makeLinkedTreeMap())
        }
        insertUnits(list)
        response=Result.SUCCESS.getResult()
    }
    fun insertUnits(units: List<LinkedTreeMap<String, Any?>>) {
        connection.autoCommit = false
        val sql = "INSERT INTO people (name, coordinates, creationdate, realhero, hastoothpick, impactspeed, soundtrackname, minutesofwaiting, mood, car, owner) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
        val statement = connection.prepareStatement(sql)
        try {
            for (unit in units) {
                statement.setString(1, unit["name"].toString())
                val coordinates = unit["coordinates"] as? Coordinates
                val coordinatesList = coordinates?.getCoordinates() ?: emptyList()
                val javaArray = coordinatesList.map { it?.toDouble() ?: 0 }.toTypedArray()
                val array = connection.createArrayOf("NUMERIC", javaArray)
                statement.setArray(2, array)
                statement.setString(3, unit["creationDate"].toString())
                statement.setBoolean(4, unit["realHero"] as Boolean)
                statement.setBoolean(5, unit["hasToothpick"] as Boolean)
                val impactSpeed = unit["impactSpeed"]
                if (impactSpeed != null) {
                    statement.setLong(6, impactSpeed.toString().toLong())
                } else {
                    statement.setNull(6, Types.BIGINT)
                }
                statement.setString(7, unit["soundtrackName"].toString())

                val minutesOfWaiting = unit["minutesOfWaiting"]
                if (minutesOfWaiting != null) {
                    statement.setDouble(8, minutesOfWaiting.toString().toDouble())
                } else {
                    statement.setNull(8, Types.DOUBLE)
                }

                statement.setString(9, unit["mood"].toString().uppercase())
                statement.setString(10, unit["car"].toString())
                statement.setString(11, unit["owner"].toString())
                statement.addBatch()
            }
            statement.executeBatch()
            connection.commit()
        } catch (e: SQLException) {
            e.printStackTrace()
            connection.rollback()
        }
    }


}
class ScriptExecuteCommand : Command(){
    override fun execute() {
        val cr = ConsoleRead()
        val script= "$line.txt"
        if (File(script).canRead() && File(script).isFile) {
            val file = File(script)
            scriptlist +=script
            file.forEachLine { lines ->
                cr.read(lines.trim())
                cr.checkCommand(cr.command)
                    if (cr.command.contains("script") && (scriptlist.contains("$line.txt") || "$line.txt"==script)) {
                        println("Recursive Execution Detected")
                        scriptlist = emptyArray()
                    } else {
                        cl.commands[cr.command]?.let { invoker.setCommand(it) }
                        invoker.executeCommand()
                    }
                }
                if(!scriptlist.contains(script)){
                    scriptlist +=script}
        }else{
            response=("No such Script or no such access rights")
        }
    }
}



class ExitCommand : Command(){
    override fun execute() {
        ongoing = false
    }
}


class RemoveFirstCommand : Command() {
    override fun execute() {
        try {
            val query = "DELETE FROM people WHERE id = (SELECT id FROM people LIMIT 1) RETURNING *"

            val preparedStatement = connection.prepareStatement(query)
            val resultSet = preparedStatement.executeQuery()

            response = if (resultSet.next()) { "Successfully removed the first record from the database." }
            else { "No records found in the database to remove." }

        } catch (e: SQLException) {
            response = "Error while executing the remove operation: ${e.message}"
        }
    }
}

class RemoveHeadCommand : Command() {
    override fun execute() {
        try {
            val query = "DELETE FROM people WHERE id = (SELECT id FROM people LIMIT 1) RETURNING *"

            val preparedStatement = connection.prepareStatement(query)
            val resultSet = preparedStatement.executeQuery()

            response = if (resultSet.next()) { "Successfully removed the first record from the database." }
            else { "No records found in the database to remove." }

        } catch (e: SQLException) {
            response = "Error while executing the remove operation: ${e.message}"
        }
    }
}


class AddIfMaxCommand : Command(){
    //constructor(list: List<Any?>) : this(makeLinkedTreeMap(list))
    override fun execute() {
        val unit = HumanBeing(refactor)
        val maxImpactSpeedValue = listOfHumanBeing.maxByOrNull { it.impactSpeed!! }?.impactSpeed ?: 0
        if(maxImpactSpeedValue < (refactor["impactSpeed"].toString().toLong())) {
                listOfData.add(unit.makeLinkedTreeMap())
                listOfHumanBeing.add(unit)
                response=Result.SUCCESS.getResult()
            }else
            {
                response=("Not max speed. Max speed is:$maxImpactSpeedValue")
            }
    }}

class CountByMinutesOfWaiting : Command(){
    private fun getCountByMinutesOfWaiting(minutesOfWaiting: Double) : Int{
        var count = 0
        for(unit in listOfHumanBeing){
            if(unit.minutesOfWaiting ==minutesOfWaiting){
                count++
            }
        }
        return count
    }
    override fun execute() {
        response=getCountByMinutesOfWaiting(line.toDouble()).toString()
    }

}


class CountLessThanMood: Command(){

    private fun getCountLessThanMood(mood: Mood):Int{
        var count = 0
        for (unit in listOfHumanBeing){
            if(unit.mood != null){
                if(unit.mood < mood){
                    count++
                }
            }
        }
        return count
    }
    private fun checkMood():Boolean{
        for (mood in Mood.values()) {
            if (mood.name == line.uppercase()) {
                return true
            } else{
                response=("No such mood")
                errorResult()
            }
        }
        return false}
    override fun execute() {
        if(checkMood()){
            response=getCountLessThanMood(Mood.valueOf(line.uppercase())).toString()
}
    }
}

class PrintAscending : Command(){
    override fun execute() {
        listOfHumanBeing.sort()
        response=listOfHumanBeing.toString()
    }
}