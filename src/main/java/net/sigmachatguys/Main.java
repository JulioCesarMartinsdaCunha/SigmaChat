package net.sigmachatguys;

import net.sigmachatguys.guiscreen.MainConsole;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main
{
    private static MainConsole mainConsole = null;

    public static void main(String[] args)
    {
        mainConsole = new MainConsole();
    }

    public static MainConsole getMainConsole()
    {
        return mainConsole;
    }
}