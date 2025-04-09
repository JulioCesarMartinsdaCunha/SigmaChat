package net.sigmachatguys.sigmaserve;

import net.sigmachatguys.SGeneralCommands;
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
                                    String msgRecebida = br.readLine();
                                    if(msgRecebida.split(" ")[0].equals(SGeneralCommands.PREFIX)) mainConsole.processCommand(msgRecebida);
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
                            String newMessage = mainManage.getLastMessage().getMessage();
                            //System.out.println("Tem uma nova mensagem: "+newMessage);
                            bw.write(newMessage);
                            bw.newLine();
                            bw.flush();
                        }
                        //1.3 NECESSARIES!
                        Thread.sleep(100);
                    }
                    System.out.println("Disconectou!");
                    isr.close();
                    osw.close();
                    br.close();
                    bw.close();
                    socketClient.close();

                    mainConsole.sendMessageToTerminal("Cliente desconectado!", true);
                }
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            finally {
                mainConsole.sendMessageToTerminal("Servidor encerrado!", true);
            }
        });
        principal.start();
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
