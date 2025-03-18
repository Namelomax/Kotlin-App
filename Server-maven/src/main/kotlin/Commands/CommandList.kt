package Commands

import java.util.HashMap

class CommandList {
    val commands = HashMap<String, Command>()
    fun setCommands() {
        commands["add"] = AddCommand()
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
        commands["execute_script"] = ScriptExecuteCommand()
    }
}