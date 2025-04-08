package net.sigmachatguys;

import net.sigmachatguys.guiscreen.SMainConsole;
import net.sigmachatguys.sigmaserve.SServe;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MainServe
{
    public static void main(String[] args)
    {
        SServe.initializeServe(new SMainConsole());
    }
}