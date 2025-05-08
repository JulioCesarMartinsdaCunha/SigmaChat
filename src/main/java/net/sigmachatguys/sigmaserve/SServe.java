package net.sigmachatguys.sigmaserve;

import net.sigmachatguys.SGeneralCommands;
import net.sigmachatguys.guiscreen.SMainConsole;
import net.sigmachatguys.messagemanage.SMessageManage;
import net.sigmachatguys.sigmaclient.SClientCommands;

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
    static final String pingComan = ":ping:";

    static int mainPort = 12345;

    public static void initializeServe(SMainConsole mainConsole)
    {
        Thread principal = new Thread(() -> {
            try
            {
                ServerSocket serverSocket = new ServerSocket(mainPort);
                serveOn = true;
                mainConsole.sendMessageToTerminal("Servidor inicializado!", true);
                while(serveOn)
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
                                    System.out.println("Lendo...................!");
                                    String msgRecebida = br.readLine();

                                    if(msgRecebida == null)
                                    {
                                        chatting = false;
                                        return;
                                    }

                                    if(!msgRecebida.equals(pingComan))
                                    {
                                        mainConsole.sendMessageToTerminal(msgRecebida);
                                    }
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
                            String newMessage = mainManage.getLastMessage().getMessage();
                            bw.write(newMessage);
                            bw.newLine();
                            bw.flush();
                        }
                        else
                        {
                            bw.write(pingComan);
                            bw.newLine();
                            bw.flush();
                        }
                        //1.3 NECESSARIES!
                        Thread.sleep(100);
                    }
                    isr.close();
                    osw.close();
                    br.close();
                    bw.close();

                    mainConsole.sendMessageToTerminal("Cliente desconectado!", true);
                }
                serverSocket.close();
            } catch (IOException e) {
                System.out.println("Teste!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            finally {
                mainConsole.sendMessageToTerminal("Servidor encerrado!", true);
            }
        });
        principal.start();
    }

    private static void processCommandFromClient(String command)
    {
        String[] args = command.split(" ");
        if(!args[0].equals(SGeneralCommands.PREFIX))
        {
            return;
        }
        switch(args[1])
        {
            case SClientCommands.COMMAND_DISCONNECT_CHAT:
                try
                {
                    socketClient.close();
                    chatting = false;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    public static void stopServe()
    {
        serveOn = false;
        disconnect();
    }

    public static void disconnect()
    {
        chatting = false;
    }
}
