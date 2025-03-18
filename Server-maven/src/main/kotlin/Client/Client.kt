package src.main.kotlin.Client

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketTimeoutException
class Client {
    companion object {
        private val SERVER_PORT = 12345
        private val TIMEOUT = 5000
        @JvmStatic
        fun main(args: Array<String>) {
            var serverAddress: InetAddress?
            var serverPort: Int
            val responseBuffer = ByteArray(1024)
            val socket = DatagramSocket()
            socket.soTimeout = TIMEOUT

            while (true) {
                try {
                    serverAddress = InetAddress.getLocalHost()
                    serverPort = SERVER_PORT
                    // Send a ping message
                    val pingMessage = "ping".toByteArray()
                    val pingPacket = DatagramPacket(pingMessage, pingMessage.size, serverAddress, serverPort)
                    socket.send(pingPacket)
                    // Wait for a response
                    val responsePacket = DatagramPacket(responseBuffer, responseBuffer.size)
                    socket.receive(responsePacket)
                    // Check that the response is valid
                    val responseData = String(responsePacket.data, 0, responsePacket.length)
                    if (responseData == "pong") {
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
            while (true) {
                println("Enter a message to send to the server (or 'exit' to quit): ")
                val message = readLine()
                if (message == "exit") {
                    socket.close()
                    break
                }
                val requestPacket =
                    message?.let { DatagramPacket(message.toByteArray(), it.length, serverAddress, serverPort) }
                socket.send(requestPacket)

                val responsePacket = DatagramPacket(ByteArray(1024), 1024)
                try {
                    socket.receive(responsePacket)
                    val responseData = String(responsePacket.data, 0, responsePacket.length)
                    println("Server response: $responseData")
                    responsePacket.length = responseBuffer.size
                } catch (e: SocketTimeoutException) {
                    println("Server is not responding")
                }
            }
        }
    }
}