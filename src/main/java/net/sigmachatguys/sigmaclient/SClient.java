package net.sigmachatguys.sigmaclient;

import net.sigmachatguys.guiscreen.SMainConsole;
import net.sigmachatguys.messagemanage.SMessageManage;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SClient
{
    static Scanner scan = new Scanner(System.in);
    static Socket socketClient = null;
    static boolean connected = false;

    static String mainIp = "localhost";
    static int mainPort = 12345;

    public static void connect(SMainConsole mainConsole)
    {
        try
        {
            socketClient = new Socket(mainIp, mainPort);

            InputStreamReader isr = new InputStreamReader(socketClient.getInputStream());
            OutputStreamWriter osw = new OutputStreamWriter(socketClient.getOutputStream());
            BufferedReader br = new BufferedReader(isr);
            BufferedWriter bw = new BufferedWriter(osw);

            connected = true;

            Thread th = new Thread(){
                @Override
                public void run() {

                    while(connected)
                    {
                        try
                        {
                            String msgRecebida = br.readLine();
                            mainConsole.sendMessageToTerminal(msgRecebida);
                        }
                        catch (IOException e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                }
            };
            th.start();

            SMessageManage mainManage = mainConsole.getMessageManage();
            mainConsole.sendMessageToTerminal("Conectado!", true);
            while(connected)
            {
                if(mainManage.isHaveNewMessage())
                {
                    String message = mainManage.getLastMessage().getMessage();
                    System.out.println("Nova mensagem!");
                    bw.write(message);
                    bw.newLine();
                    bw.flush();
                }
                //1.3 NECESSARIES!
                Thread.sleep(100);
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void commandDisconnect()
    {

    }
}
