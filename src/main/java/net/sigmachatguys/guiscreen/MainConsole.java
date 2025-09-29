package net.sigmachatguys.guiscreen;

import net.sigmachatguys.consolecommands.GeneralCommands;
import net.sigmachatguys.security.DigitalSignature;
import net.sigmachatguys.messagemanage.MessageManage;
import net.sigmachatguys.client.Client;
import net.sigmachatguys.consolecommands.ClientCommands;
import net.sigmachatguys.serve.Serve;
import net.sigmachatguys.consolecommands.ServeCommands;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainConsole extends JFrame {
    private JTextArea textArea;
    private JTextField inputField;
    private static String commandPrefix = "Sigma:";

    private boolean linked = false;

    private MessageManage messageManage = new MessageManage();

    private long lastMessageTime = 0;

    public MainConsole()
    {
        initializeComponents();
        this.setVisible(true);
    }

    private void initializeComponents()
    {
        setTitle("Sigma Chat BETA v1.3.0");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Fundo preto tradicional do terminal
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Área de texto para saída do "terminal"
        textArea = new JTextArea();
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.GREEN);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Campo de entrada de comandos
        inputField = new JTextField();
        inputField.setBackground(Color.BLACK);
        inputField.setForeground(Color.WHITE);
        inputField.setFont(new Font("Consolas", Font.PLAIN, 14));
        inputField.setCaretColor(Color.WHITE);
        mainPanel.add(inputField, BorderLayout.SOUTH);

        add(mainPanel);


        // Listener para processar os comandos inseridos
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = inputField.getText();
                inputField.setText(""); // Limpar o campo de entrada
                processMessage(command);
            }
        });

        // Atualiza o padrão de obfuscação dinamicamente a cada 100ms
        Timer obfuscationTimer = new Timer(100, e -> mainPanel.repaint());
        obfuscationTimer.start();

        setVisible(true);
    }

    private void processMessage(String message){
        if(message.isEmpty())
        {
            return;
        }
        long tempodeEspera  =   System.currentTimeMillis();
        if (tempodeEspera - lastMessageTime < 5000){
            textArea.append("Aguarde..."+"\n");
            return;
        }

        //Verifica se a mensagem que ele ta passando é um comando, por meio do prefixo determinado no SigmaGeneralCommands.
        if(message.split(" ")[0].equals(GeneralCommands.PREFIX))
        {
            processCommand(message);
            return;
        }

        //message
        try
        {
            DigitalSignature.signMessage(message);
        }
        catch(Exception exception)
        {

        }

        messageManage.setNewMessage(message);

        textArea.append("Você: " + message + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    private void processCommand(String message) {
        // Simula a execução de comandos no terminal
        String[] args = message.split(" ");
        textArea.append("> " + message + "\n");

        if(args.length <= 1)
        {
            return;
        }

        // Comandos disponíveis
       switch (args[1])
       {
           default:
               textArea.append("> " + "Comando Inválido!" +"\n");
           break;

           case GeneralCommands.COMMAND_HELP:
               GeneralCommands.useHelp(this);
           break;
           case GeneralCommands.COMMAND_GET_IP:
           break;
           case GeneralCommands.COMMAND_SET_IP:
           break;
           case GeneralCommands.COMMAND_GET_PORT:
           break;
           case GeneralCommands.COMMAND_SET_PORT:
           break;
           case GeneralCommands.COMMAND_CLOSE:

               System.exit(0);
           break;

           case ServeCommands.COMMAND_START_CHAT:
               Serve.initializeServe(this);
           break;
           case ClientCommands.COMMAND_CONNECT_CHAT:
               Client.connect(this);
           break;
           case ServeCommands.COMMAND_STOP_CHAT:
                Serve.stopServe();
           break;
           case ClientCommands.COMMAND_DISCONNECT_CHAT:
               Client.disconnect();
               Serve.disconnect();
           break;
       }

        // Rola automaticamente para o final do terminal
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public void sendMessageToTerminal(String message)
    {
        if (message == null)
        {
            return;
        }
        textArea.append("Outro Sigma: "+message+"\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public void sendMessageToTerminal(String message, boolean messageServed)
    {
        if (message == null)
        {
            return;
        }
        String messageToSend = messageServed ? "[SERVER]: "+message : "Outro Sigma: "+message;
        textArea.append(messageToSend+"\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public MessageManage getMessageManage()
    {
        return messageManage;
    }
}
