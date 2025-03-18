package Commands
import Result
abstract class Command {
    open fun execute(){}
     fun errorResult() {
        println(Result.ERROR)
    }
      fun successResult() {
        println(Result.SUCCESS)
    }
}