package net.sigmachatguys;

import net.sigmachatguys.guiscreen.SMainConsole;
import net.sigmachatguys.sigmaserve.SServe;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main
{
    private static SMainConsole mainConsole = null;
    public static void main(String[] args)
    {
        mainConsole = new SMainConsole();
    }

    public static SMainConsole getMainConsole()
    {
        return mainConsole;
    }
}