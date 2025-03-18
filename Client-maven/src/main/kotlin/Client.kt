import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketTimeoutException
import Commands.CommandList
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.concurrent.Executors

class Client {
    companion object {
        const val url = "jdbc:postgresql://localhost:5432/studs"
        private val SERVER_PORT = 12345
        private val TIMEOUT = 5000
        var scriptlist = emptyArray<String>()
        var ongoing = true
        var line: String = ""
        var username = ""
        var passw = ""
        val cl = CommandList()
        val users = mutableListOf<User>()
        private val gson=Gson()
        var refactor = LinkedTreeMap<String, Any?>()
        private val threadPool = Executors.newFixedThreadPool(5)
        @JvmStatic
        fun main(args: Array<String>) {
            println(refactor)
            val cr = ConsoleRead()
            cl.setCommands()
            var serverAddress: InetAddress?
            var serverPort: Int
            val responseBuffer = ByteArray(4096)
            val socket = DatagramSocket()
            var accesCheck = true
            socket.soTimeout = TIMEOUT
            while (true) {
                try {
                    serverAddress = InetAddress.getLocalHost()
                    serverPort = SERVER_PORT
                    // Send a ping message
                    val check = JsonObject()
                    check.addProperty("command", "ping")
                    val json = gson.toJson(check)
                    val pingPacket = DatagramPacket(json.toByteArray(), json.length, serverAddress, serverPort)
                    socket.send(pingPacket)
                    val responsePacket = DatagramPacket(responseBuffer, responseBuffer.size)
                    socket.receive(responsePacket)
                    val responseData = String(responsePacket.data, 0, responsePacket.length)
                    println(responseData)
                    if (responseData.isNotEmpty()) {
                        break
                    } else {
                        throw Exception("Invalid response from server")
                    }
                } catch (e: Exception) {
                    println("Server is not responding")
                    Thread.sleep(TIMEOUT.toLong())
                }
            }
            if (serverAddress != null) {
                println("Server is running at ${serverAddress.hostAddress}:${serverPort}")
            }
            while (ongoing) {
                while (accesCheck) {
                    println("Choose login or register(log/reg)")
                    val line = readln().trim()
                    if (line == "log") {
                        println("enter login:")
                        username =readln().trim()
                        println("enter password:")
                        passw = readln().trim()
                        if (!authenticateUser(username,passw)) {
                        continue}else{println("authentication successful")
                            accesCheck = false}
                    } else if (line == "reg") {
                        println("enter username:")
                        username = readln().trim()
                        println("enter password:")
                        passw = readln().trim()
                        if (!registerUser(username, passw)) {
                            continue
                        }else{accesCheck = false}
                    } else {
                        println("should be log or reg")
                        continue
                    }
                }
                scriptlist = emptyArray()
                refactor.clear()
                line=""
                println("Введите команду")
                cr.read(readln().trim())
                if (!cr.checkCommand(cr.command)) {
                    continue
                }
                val message = cr.command
                if (message == "exit") {
                    socket.close()
                    break
                }
                val jsonObject = LinkedTreeMap<String, Any>()
                jsonObject["command"] = cr.command
                jsonObject["username"] = username // Replace 'yourUsername' with the actual username
                jsonObject["password"] = passw
                if (refactor.isEmpty()) {
                    jsonObject["param"] = line
                    println(1)
                }else if (refactor.isNotEmpty() && line.isNotEmpty()) {
                    val param = LinkedTreeMap<String, Any?>()
                    param["line"] = line
                    param["refactor"] = refactor
                    jsonObject["param"] = param
                } else if(refactor.isNotEmpty() && line.isEmpty()){
                    jsonObject["param"] = refactor
                }

                if (cr.command.contains("script")) {
                    serverAddress = InetAddress.getLocalHost()
                    serverPort = SERVER_PORT
                    threadPool.submit {
                        val json = cr.commandsArray
                        val jsonstring = json.toString()
                        val requestPacket = DatagramPacket(
                            jsonstring.toByteArray(),
                            jsonstring.toByteArray().size,
                            serverAddress,
                            serverPort
                        )
                        socket.send(requestPacket)
                        println(jsonstring)
                    }
                } else {
                    serverAddress = InetAddress.getLocalHost()
                    serverPort = SERVER_PORT
                    threadPool.submit {
                        val json = gson.toJson(jsonObject)
                        val requestPacket =
                            DatagramPacket(json.toByteArray(), json.toByteArray().size, serverAddress, serverPort)
                        socket.send(requestPacket)
                        println(json)
                    }
                }

                val buffer = ByteArray(16384)
                try {
                    val packet = DatagramPacket(buffer, buffer.size)
                    socket.receive(packet)
                    val Data = String(packet.data, 0, packet.length)
                    // val receivedString = receivedData.substring(0, receivedData.length - terminationSequence.length)
                    println(Data)
                    val responseJson = gson.fromJson(Data, JsonElement::class.java)
                    if (responseJson.isJsonArray) {
                        val jsonArray = responseJson as JsonArray
                        for (element in jsonArray) {
                            val responseCommand = element.asJsonObject.get("answer").asString
                            println("Received message from server: $responseCommand")
                        }
                    } else if (responseJson.isJsonObject) {
                        val jsonObject = responseJson as JsonObject
                        val responseCommand = jsonObject.get("answer").asString
                        println("Received message from server: $responseCommand")
                    }
                } catch (e: SocketTimeoutException) {
                    println("Server is not responding")
                }
            }
            }
        }
    }