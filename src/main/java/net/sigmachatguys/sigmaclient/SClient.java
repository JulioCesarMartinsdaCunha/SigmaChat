package net.sigmachatguys.sigmaclient;

import net.sigmachatguys.SGeneralCommands;
import net.sigmachatguys.guiscreen.SMainConsole;
import net.sigmachatguys.messagemanage.SMessageManage;
import net.sigmachatguys.sigmaserve.SServeCommands;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SClient
{
    static Scanner scan = new Scanner(System.in);
    static Socket socketClient = null;
    static boolean connected = false;

    static final String pingComan = ":ping:";

    static String mainIp = "localhost";
    static int mainPort = 12345;

    public static void connect(SMainConsole mainConsole)
    {
        Thread principal = new Thread(() -> {
            try
            {
                socketClient = new Socket(mainIp, mainPort);

                InputStreamReader isr = new InputStreamReader(socketClient.getInputStream());
                OutputStreamWriter osw = new OutputStreamWriter(socketClient.getOutputStream());
                BufferedReader br = new BufferedReader(isr);
                BufferedWriter bw = new BufferedWriter(osw);

                connected = true;

                Thread th = new Thread(() -> {

                    while(connected)
                    {
                        try
                        {
                            //FECHAR CONEXÃO COM O CLIENTE COMPLETA! FAZER FECHAR CONEXÃO FORÇADA DO SERVER!
                            String msgRecebida = br.readLine();

                            if(msgRecebida == null)
                            {
                                connected = false;
                                return;
                            }

                            if(!msgRecebida.equals(pingComan))
                            {
                                mainConsole.sendMessageToTerminal(msgRecebida);
                            }
                        }
                        catch (IOException e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                });
                th.start();

                SMessageManage mainManage = mainConsole.getMessageManage();
                mainConsole.sendMessageToTerminal("Conectado!", true);
                while(connected)
                {
                    if(mainManage.isHaveNewMessage())
                    {
                        String message = mainManage.getLastMessage().getMessage();
                        bw.write(message);
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

                System.out.println("Saiu!");
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                mainConsole.sendMessageToTerminal("O host fechou a conexão!", true);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            finally {
                mainConsole.sendMessageToTerminal("Conexão com o host finalizada!", true);
            }
        });
        principal.start();
    }

    private static void processCommandFromServe(String command)
    {
        String[] args = command.split(" ");
        if(!args[0].equals(SGeneralCommands.PREFIX))
        {
            return;
        }
        switch(args[1])
        {
            case SServeCommands.COMMAND_STOP_CHAT:
                try
                {
                    socketClient.close();
                    connected = false;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    public static void disconnect()
    {
        connected = false;
    }
}
