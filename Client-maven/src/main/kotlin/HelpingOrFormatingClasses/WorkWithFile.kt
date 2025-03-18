package HelpingOrFormatingClasses
import com.google.gson.*
import com.google.gson.internal.LinkedTreeMap
import java.io.*
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


fun readFromFile(pathToFile: String): String {
    var txt = ""
    try {
        txt = Scanner( File(pathToFile)).useDelimiter("\\Z").next()
    } catch (e: FileNotFoundException) {
        throw FileNotFoundException("It seems like you don't have such file")
    }
    return txt
}


fun convertJSONtoLinkedList(txt: String) : LinkedList<LinkedTreeMap<String, Any?>> {
    return Gson().fromJson(txt, LinkedList::class.java) as LinkedList<LinkedTreeMap<String, Any?>>
}

fun writeInJSONFile(pathToFile: String, data: LinkedList<LinkedTreeMap<String, Any?>>) {
    val gson = GsonBuilder().serializeNulls().setPrettyPrinting().registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        .create()
    val jsonString = gson.toJson(data)
    try  {
       val bos = BufferedOutputStream(FileOutputStream(pathToFile))
       bos.write(jsonString.toByteArray())
        bos.flush()
        bos.close()
        //File(pathToFile).writeText(jsonString)
    }
    catch (e: IOException){
        throw IOException(e)
    }
    catch (e: RuntimeException){
        throw RuntimeException(e)
    }
}

class LocalDateTimeAdapter: JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    override fun serialize(src: LocalDateTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        return JsonPrimitive(formatter.format(src))
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalDateTime {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        return LocalDateTime.parse(json?.asString, formatter)
    }
}
fun writeInTxtFile(pathToFile: String, data: String){
    try {
        val fileWriter = FileWriter(pathToFile)
        fileWriter.write(data)
        fileWriter.flush()
        fileWriter.close()
    }
    catch (e: IOException){
        throw IOException(e)
    }
}