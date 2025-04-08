package net.sigmachatguys;

import net.sigmachatguys.guiscreen.SMainConsole;
import net.sigmachatguys.sigmaclient.SClient;

public class MainClient
{
    public static void main(String[] args)
    {
        SClient.connect(new SMainConsole());
    }
}
