package net.sigmachatguys.client;

public class ClientCommands
{
    public static final String COMMAND_CONNECT_CHAT = "connect-chat";
    public static final String COMMAND_DISCONNECT_CHAT = "disconnect-chat";

    public static void printAllCommands()
    {
        System.out.println(COMMAND_CONNECT_CHAT);
        System.out.println(COMMAND_DISCONNECT_CHAT);
    }

    public static String getTextedCommands()
    {
        String[] args = new String[] {COMMAND_CONNECT_CHAT, COMMAND_DISCONNECT_CHAT};
        String message = "";
        for(String i : args)
        {
            message += i + "\n";
        }
        return message;
    }
}
