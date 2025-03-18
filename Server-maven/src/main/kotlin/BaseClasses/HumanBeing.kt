package BaseClasses

import Second_sem.lab5.Kotlin.BaseClasses.*
import com.google.gson.JsonPrimitive
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.*
import username1
import java.time.LocalDateTime

class HumanBeing(
    val id: Int?, val name: String="DefaultName", val coordinates: Coordinates = Coordinates(),
    val creationDate: LocalDateTime? = LocalDateTime.of(LocalDateTime.now().year,
        LocalDateTime.now().monthValue, LocalDateTime.now().dayOfMonth,
        LocalDateTime.now().hour, LocalDateTime.now().minute, LocalDateTime.now().second),
    val realHero: Boolean = false, val hasToothpick: Boolean = false, val impactSpeed: Long? = 0,
    val soundtrackName: String = "DefaultSoundtrackName", val minutesOfWaiting: Double? = null,
    val mood: Mood? = null, val car: Car? = null, val owner: String="Defaultowner", val password: String="Defaultpassw",) : Comparable<HumanBeing>  {


    constructor(linkedTreeMap: LinkedTreeMap<String, Any?>) : this(
        (linkedTreeMap["id"] as? Int)?.toInt(),
        (linkedTreeMap["name"] as? JsonPrimitive).toString(),
        Coordinates((linkedTreeMap["coordinates"] as? JsonPrimitive)?.asJsonArray),
        makeLocalDateTime(
            linkedTreeMap["creationDate"] as? List<Int> ?:
            listOf(
                LocalDateTime.now().year, LocalDateTime.now().monthValue,
                LocalDateTime.now().dayOfMonth, LocalDateTime.now().hour,
                LocalDateTime.now().minute, LocalDateTime.now().second)
        ),
        (linkedTreeMap["realHero"] as JsonPrimitive).asBoolean,
        (linkedTreeMap["hasToothpick"] as JsonPrimitive).asBoolean,
        (linkedTreeMap["impactSpeed"] as JsonPrimitive).asLong,
        (linkedTreeMap["soundtrackName"] as? JsonPrimitive).toString(),
        (linkedTreeMap["minutesOfWaiting"] as JsonPrimitive).asDouble,
        (linkedTreeMap["mood"] as? String)?.let { Mood.valueOf(it) },
        Car((linkedTreeMap["car"] as? JsonPrimitive).toString()),
        (linkedTreeMap["owner"] as? JsonPrimitive).toString()
    )

    override fun compareTo(other: HumanBeing): Int {
        return other.id?.let { id?.compareTo(it) ?: 0 }!!
    }
    override fun toString(): String {
        return """
            
            id: $id
            name: $name
            coordinates: $coordinates
            creation date: $creationDate
            real hero: $realHero
            has toothpick: $hasToothpick
            impact speed: $impactSpeed
            soundtrack name: $soundtrackName
            minutes of waiting: $minutesOfWaiting
            mood: $mood
            car: $car
            owner: $mood
            password: $car
            """.trimIndent()
    }


    fun makeLinkedTreeMap():LinkedTreeMap<String, Any?>{
        val list = LinkedTreeMap<String, Any?>()
        list["name"] = name
        list["coordinates"] = coordinates
        list["creationDate"] = creationDate
        list["realHero"] = realHero
        list["hasToothpick"] = hasToothpick
        list["impactSpeed"] = impactSpeed
        list["soundtrackName"] = soundtrackName
        list["minutesOfWaiting"] = minutesOfWaiting
        list["mood"] = mood
        list["car"] = car
        list["owner"]=username1
        return list
    }
}