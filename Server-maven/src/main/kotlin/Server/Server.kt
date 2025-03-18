package Server

import cl
import java.net.DatagramPacket
import java.net.DatagramSocket

class Server {

    /*companion object {
        var pathToCollection = ""
        var pathToId = ".\\Id.txt"
        var scriptlist = emptyArray<String>()
        var dateOfInitialization = LocalDateTime.of(
            LocalDateTime.now().year,
            LocalDateTime.now().monthValue, LocalDateTime.now().dayOfMonth,
            LocalDateTime.now().hour, LocalDateTime.now().minute, LocalDateTime.now().second
        )
        var listOfData = LinkedList<LinkedTreeMap<String, Any?>>()
        var listOfHumanBeing = LinkedList<HumanBeing>()
        var ongoing = true
        var line: String = ""
        val cl = CommandList()
        var doubleparam = 0.0
        val invoker = Invoker()
        private const val PORT = 12345
        var response =""
        private const val PORT = 12345
        @JvmStatic
        fun main(args: Array<String>) {
            val socket = DatagramSocket(PORT)
            var start : Boolean =true
            while (true) {
                // receive a UDP packet from the client
                val packet = DatagramPacket(ByteArray(1024), 1024)
                if (start){
                    println("Server is ready")
                    start=false}
                socket.receive(packet)
                val message = Message("Hello, server!", System.currentTimeMillis())

                // convert the packet to a string
                val message = String(packet.data, 0, packet.length)
                println("Received message from client: $message")
                cl.commands[message]?.let { invoker.setCommand(it) }
                invoker.executeCommand()
                // send a response back to the client
                val address = packet.address
                response = when (message) {
                    "ping" -> "pong"
                    else -> message
                }
                val responsePacket = DatagramPacket(response.toByteArray(), response.length, address, packet.port)
                socket.send(responsePacket)
                println("Sent response to client: $response")
            }
        }
        }*/
    }