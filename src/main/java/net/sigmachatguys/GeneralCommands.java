package net.sigmachatguys;

import net.sigmachatguys.client.ClientCommands;
import net.sigmachatguys.guiscreen.MainConsole;
import net.sigmachatguys.serve.ServeCommands;

public class GeneralCommands
{
    public static final String COMMAND_SET_IP = "set-ip";
    public static final String COMMAND_SET_PORT = "set-port";
    public static final String COMMAND_GET_IP = "get-ip";
    public static final String COMMAND_GET_PORT = "get-port";

    public static final String COMMAND_HELP = "help";

    public static final String COMMAND_CLOSE = "exit";

    public static final String PREFIX = "->";

    public static String getGeneralCommands()
    {
        return "Commandos Gerais";
    }

    public static void useHelp(MainConsole console)
    {
        String message = "";
        message += "---Help Commands--------------------------------------------------";
        message += "Generals:";
        message += COMMAND_SET_IP;
        message += COMMAND_SET_PORT;
        message += COMMAND_GET_IP;
        message += COMMAND_SET_IP;
        message += COMMAND_HELP;
        message += COMMAND_CLOSE;
        message += "/n";

        message += "Server:";
        message += ServeCommands.getServeCommands();
        message += "/n";

        message += "Client:";
        message += ClientCommands.getTextedCommands();
        message += "/n";

        System.out.println(message);
    }
}
