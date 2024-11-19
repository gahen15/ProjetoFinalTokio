package br.com.fiap.interfacegrafica;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

    private JFrame frame;
 

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main window = new Main();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Main() {
        initialize();
        frame.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(0, 0, 1216, 713);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        
        // Criar e adicionar o painel de fundo
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        frame.setContentPane(backgroundPanel);
        frame.getContentPane().setLayout(null);

        // Botões
        JButton shutdownButton = new JButton("");
        shutdownButton.setToolTipText("Sair");
       
        shutdownButton.setBounds(1130, 603, 60, 60);
        frame.getContentPane().add(shutdownButton);
        shutdownButton.setIcon(new ImageIcon(Main.class.getResource("/resources/images/off.png")));
        JButton btnNewButton = new JButton("");
        btnNewButton.setToolTipText("Home");
        btnNewButton.setIcon(new ImageIcon(Main.class.getResource("/resources/images/Homebotão.png")));
        shutdownButton.setOpaque(false); // Deixa o fundo transparente
        shutdownButton.setBorderPainted(false); // Remove a borda do botão
        shutdownButton.setContentAreaFilled(false);
       
        btnNewButton.setBounds(37, 164, 105, 100);
        frame.getContentPane().add(btnNewButton);

        JButton btnNewButton_1 = new JButton("");
        btnNewButton_1.setToolTipText("Cadastrar Usuario\r\n");
        btnNewButton_1.setIcon(new ImageIcon(Main.class.getResource("/resources/images/CADASTRO DE CLIENRTESPlus.jpg")));
        btnNewButton_1.setBounds(37, 286, 105, 100);
        frame.getContentPane().add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("");
        btnNewButton_2.setToolTipText("Seguros");
        btnNewButton_2.setIcon(new ImageIcon(Main.class.getResource("/resources/images/SegurosBotão.png")));
        btnNewButton_2.setBounds(37, 406, 105, 100);
        frame.getContentPane().add(btnNewButton_2);

        JButton btnNewButton_3 = new JButton("");
        btnNewButton_3.setToolTipText("Dados");
        btnNewButton_3.setIcon(new ImageIcon(Main.class.getResource("/resources/images/RelatóriosBotão.png")));
        btnNewButton_3.setBounds(37, 529, 105, 100);
        frame.getContentPane().add(btnNewButton_3);
    
    
        shutdownButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				
			}
        	
        });
    
    }

    // Classe do painel de fundo
    class BackgroundPanel extends JPanel {
        private BufferedImage backgroundImage;

        public BackgroundPanel() {
            try {
                // Carregar a imagem de fundo corretamente
                backgroundImage = ImageIO.read(getClass().getResource("/resources/images/background.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Desenhar a imagem de fundo
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
        
    }

   
}
