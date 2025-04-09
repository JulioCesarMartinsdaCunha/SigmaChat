package net.sigmachatguys.guiscreen;

import net.sigmachatguys.SigmaGeneralCommands;
import net.sigmachatguys.messagemanage.SMessageManage;
import net.sigmachatguys.sigmaclient.SClient;
import net.sigmachatguys.sigmaclient.SClientCommands;
import net.sigmachatguys.sigmaserve.SServe;
import net.sigmachatguys.sigmaserve.SServeCommands;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SMainConsole extends JFrame {
    private JTextArea textArea;
    private JTextField inputField;
    private static String commandPrefix = "Sigma:";

    private boolean linked = false;

    private SMessageManage messageManage = new SMessageManage();

    public SMainConsole()
    {
        initializeComponents();
        this.setVisible(true);
    }

    private void initializeComponents()
    {
        setTitle("Simulador CMD com Obfuscação");
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
        private long lastMessageTime = 0;
    private void processMessage(String message)
    {
        if(message.isBlank())
        {
            return;
        }
        long tempodeEspera  =   System.currentTimeMillis();
        if (tempodeEspera - lastMessageTime < 5000){
            textArea.append("Aguarde..."+"\n");
            return;
        }
        messageManage.setNewMessage(message);

        //Verifica se a mensagem que ele ta passando é um comando, por meio do prefixo determinado no SigmaGeneralCommands.
        String[] args  = message.split(" ");
        if(args[0].equals(SigmaGeneralCommands.PREFIX))
        {
            processCommand(message);
            return;
        }
        textArea.append("Você: " + message + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    private void processCommand(String command) {
        // Simula a execução de comandos no terminal
        textArea.append("> " + command + "\n");

        // Comandos disponíveis
       switch (command)
       {
           default:
               textArea.append("> " + "Comando Inválido!" +"\n");
           break;

           case SigmaGeneralCommands.COMMAND_HELP:
           break;
           case SigmaGeneralCommands.COMMAND_GET_IP:
           break;
           case SigmaGeneralCommands.COMMAND_SET_IP:
           break;
           case SigmaGeneralCommands.COMMAND_GET_PORT:
           break;
           case SigmaGeneralCommands.COMMAND_SET_PORT:
           break;

           case SServeCommands.COMMAND_START_CHAT:
               SServe.initializeServe(this);
           break;
           case SClientCommands.COMMAND_CONNECT_CHAT:
               SClient.connect(this);
           break;
       }

        // Rola automaticamente para o final do terminal
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public void sendMessageToTerminal(String message)
    {
        textArea.append("Outro Sigma: "+message+"\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public void sendMessageToTerminal(String message, boolean messageServed)
    {
        String messageToSend = messageServed ? "[SERVER]: "+message : "Outro Sigma: "+message;
        textArea.append(messageToSend+"\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public SMessageManage getMessageManage()
    {
        return messageManage;
    }
}
