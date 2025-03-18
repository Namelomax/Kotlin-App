package Commands

class Invoker {

    var command: Command? = null

    @JvmName("setCommand1")
    fun setCommand(command: Command){
        this.command = command
    }

    fun executeCommand(){
        command?.execute()
    }
}