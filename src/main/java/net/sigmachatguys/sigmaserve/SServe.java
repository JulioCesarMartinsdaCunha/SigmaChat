package net.sigmachatguys.sigmaserve;

import net.sigmachatguys.guiscreen.SMainConsole;
import net.sigmachatguys.messagemanage.SMessageManage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SServe
{
    static ServerSocket serveSocket = null;
    static Socket socketClient = null;

    static boolean serveOn = false;
    static boolean chatting = false;

    static Scanner scan = new Scanner(System.in);

    static int mainPort = 12345;

    public static void initializeServe(SMainConsole mainConsole)
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(mainPort);
            mainConsole.sendMessageToTerminal("Servidor inicializado!", true);
            while(true)
            {
                socketClient = serverSocket.accept();
                InputStreamReader isr = new InputStreamReader(socketClient.getInputStream());
                OutputStreamWriter osw = new OutputStreamWriter(socketClient.getOutputStream());
                BufferedReader br = new BufferedReader(isr);
                BufferedWriter bw = new BufferedWriter(osw);

                chatting = true;

                Thread th = new Thread(){
                    @Override
                    public void run()
                    {
                        while (chatting)
                        {
                            try
                            {
                                String msgRecebida = br.readLine();
                                mainConsole.sendMessageToTerminal(msgRecebida);

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                };
                th.start();

                SMessageManage mainManage = mainConsole.getMessageManage();
                mainConsole.sendMessageToTerminal("Cliente connectado!", true);
                while(chatting)
                {
                    if(mainManage.isHaveNewMessage())
                    {
                        System.out.println("Tem uma nova mensagem!");
                        String newMessage = mainManage.getLastMessage().getMessage();

                        bw.write(newMessage);
                        bw.newLine();
                        bw.flush();
                    }

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void commandStopServe()
    {

    }
}
