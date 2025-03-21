package net.sigmachatguys.guiscreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SigmaMainConsole extends JFrame {
    private JTextArea textArea;
    private JTextField inputField;
    private static String commandPrefix = "Sigma:";


    public SigmaMainConsole() {
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

                // Camada de obfuscação dinâmica
                for (int i = 0; i < getWidth(); i += 40) {
                    for (int j = 0; j < getHeight(); j += 40) {
                        g.setColor(new Color(
                                (int) (Math.random() * 255), // Cor aleatória (R)
                                (int) (Math.random() * 255), // Cor aleatória (G)
                                (int) (Math.random() * 255), // Cor aleatória (B)
                                100 // Transparência
                        ));
                        g.fillRect(i, j, 20, 20); // Quadrados de obfuscação
                    }
                }
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
                processCommand(command);
            }
        });

        // Atualiza o padrão de obfuscação dinamicamente a cada 100ms
        Timer obfuscationTimer = new Timer(100, e -> mainPanel.repaint());
        obfuscationTimer.start();

        setVisible(true);
    }

    private void processCommand(String command) {
        // Simula a execução de comandos no terminal
        textArea.append("> " + command + "\n");

        // Comandos disponíveis
        if (command.equalsIgnoreCase("help")) {
            textArea.append("Comandos disponíveis:\n");
            textArea.append("  - clear: Limpa o terminal\n");
            textArea.append("  - exit: Fecha a aplicação\n");
        } else if (command.equalsIgnoreCase("clear")) {
            textArea.setText("");
        } else if (command.equalsIgnoreCase("exit")) {
            System.exit(0);
        } else {
            textArea.append("Comando desconhecido: " + command + "\n");
        }

        // Rola automaticamente para o final do terminal
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}
