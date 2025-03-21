package net.sigmachatguys.sigmaclient;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SigmaClient
{
    static Scanner scan = new Scanner(System.in);
    static Socket socketClient = null;
    static boolean connected = false;

    static String mainIp = "localhost";
    static int mainPort = 12345;

    public static void connect()
    {
        try
        {
            socketClient = new Socket(mainIp, mainPort);

            InputStreamReader isr = new InputStreamReader(socketClient.getInputStream());
            OutputStreamWriter osw = new OutputStreamWriter(socketClient.getOutputStream());
            BufferedReader br = new BufferedReader(isr);
            BufferedWriter bw = new BufferedWriter(osw);

            connected = true;
            System.out.println("Conectado!");
            while(connected)
            {
                String msgEnviar = scan.nextLine();

                bw.write(msgEnviar);
                bw.newLine();
                bw.flush();

                String msgRecebida = br.readLine();
                System.out.println("Servidor: "+msgRecebida);
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void disconnect()
    {

    }
}
