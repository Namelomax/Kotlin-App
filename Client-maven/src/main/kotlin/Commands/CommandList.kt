package Commands

import java.util.HashMap

class CommandList {
    val commands = arrayListOf<String>()
    fun setCommands() {
        commands.add("add")
        commands.add("add_if_max")
        commands.add("clear")
        commands.add("count_by_minutes_of_waiting")
        commands.add("count_less_than_mood")
        commands.add("help")
        commands.add("exit")
        commands.add("info")
        commands.add("print_ascending")
        commands.add("remove_by_id")
        commands.add("remove_first")
        commands.add("remove_head")
        commands.add("save")
        commands.add("show")
        commands.add("update")
        commands.add("execute_script")
        /*commands["add"] = AddCommand()
        commands["add_if_max"] = AddIfMaxCommand()
        commands["clear"] = ClearCommand()
        commands["count_by_minutes_of_waiting"] = CountByMinutesOfWaiting()
        commands["count_less_than_mood"] = CountLessThanMood()
        commands["help"] = HelpCommand()
        commands["exit"] = ExitCommand()
        commands["info"] = InfoCommand()
        commands["print_ascending"] = PrintAscending()
        commands["remove_by_id"] = RemoveByIdCommand()
        commands["remove_first"] = RemoveFirstCommand()
        commands["remove_head"] = RemoveHeadCommand()
        commands["save"] = SaveCommand()
        commands["show"] = ShowCommand()
        commands["update"] = UpdateCommand()
        commands["execute_script"] = ScriptExecuteCommand()*/
    }
}