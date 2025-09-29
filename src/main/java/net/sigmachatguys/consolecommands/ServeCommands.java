package net.sigmachatguys.consolecommands;

public class ServeCommands
{
    public static final String COMMAND_START_CHAT = "start-chat";
    public static final String COMMAND_STOP_CHAT = "stop-chat";
    public static final String COMMAND_CLOSE_CHAT = "close-chat";

    public static String getServeCommands()
    {
        String[] args = new String[] {COMMAND_START_CHAT, COMMAND_STOP_CHAT, COMMAND_CLOSE_CHAT};
        String text = "";
        for(String i : args)
        {
            text += i + "\n";
        }
        return text;
    }

    public static void printAllCommands()
    {
        System.out.println(COMMAND_START_CHAT);
        System.out.println(COMMAND_STOP_CHAT);
        System.out.println(COMMAND_CLOSE_CHAT);
    }
}
