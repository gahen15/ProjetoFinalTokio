package br.com.fiap.interfacegrafica;

import java.awt.CardLayout;
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
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JScrollPane;

public class Main {

    private JFrame frame;
    private JPanel contentPanel; // Painel para o conteúdo da direita
    private CardLayout cardLayout; // Layout para trocar os painéis à direita

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

        // Criar o painel de conteúdo à direita e configurar o CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBounds(160, 0, 1056, 713); // Ajuste do tamanho do painel à direita
        contentPanel.setOpaque(false); // Deixar o painel de conteúdo transparente
        frame.getContentPane().add(contentPanel);
        
        // Criar os painéis de conteúdo (páginas)
        JPanel homePanel = new JPanel();
        homePanel.setOpaque(false); // Garantir que o painel do conteúdo também seja transparente
        homePanel.setLayout(null);
        JLabel lblHome = new JLabel("HOME");
        lblHome.setForeground(new Color(255, 255, 255));
        lblHome.setFont(new Font("Segoe UI", Font.BOLD, 50));
        lblHome.setBounds(435, 11, 156, 43);
        homePanel.add(lblHome);
        
        JPanel cadastroPanel = new JPanel();
        cadastroPanel.setOpaque(false); // Garantir que o painel do conteúdo também seja transparente
        cadastroPanel.setLayout(null);
        JLabel lblCadastroDeClientes = new JLabel("CADASTRO DE CLIENTES");
        lblCadastroDeClientes.setForeground(new Color(255, 255, 255));
        lblCadastroDeClientes.setFont(new Font("Segoe UI", Font.BOLD, 50));
        lblCadastroDeClientes.setBounds(236, 11, 610, 47);
        cadastroPanel.add(lblCadastroDeClientes);

        JPanel segurosPanel = new JPanel();
        segurosPanel.setOpaque(false); // Garantir que o painel do conteúdo também seja transparente
        segurosPanel.setLayout(null);
        JLabel lblSeguros = new JLabel("SEGUROS");
        lblSeguros.setForeground(new Color(255, 255, 255));
        lblSeguros.setFont(new Font("Segoe UI", Font.BOLD, 50));
        lblSeguros.setBounds(400, 0, 243, 67);
        segurosPanel.add(lblSeguros);

        JPanel relatoriosPanel = new JPanel();
        relatoriosPanel.setOpaque(false); // Garantir que o painel do conteúdo também seja transparente
        relatoriosPanel.setLayout(null);
        JLabel lblDados = new JLabel("DADOS");
        lblDados.setBounds(440, 0, 175, 67);
        lblDados.setForeground(new Color(255, 255, 255));
        lblDados.setFont(new Font("Segoe UI", Font.BOLD, 50));
        relatoriosPanel.add(lblDados);

        // Adicionar os painéis de conteúdo ao painel principal
        contentPanel.add(homePanel, "Home");
        contentPanel.add(cadastroPanel, "Cadastro");
        contentPanel.add(segurosPanel, "Seguros");
        contentPanel.add(relatoriosPanel, "Relatórios");

        // Criar os painéis "CadastroJuridica" e "CadastroFisica"
        JPanel cadastroJuridicaPanel = new JPanel();
        cadastroJuridicaPanel.setOpaque(false);
        cadastroJuridicaPanel.setLayout(null);
        JLabel lblCadastroJuridica = new JLabel("CADASTRO JURÍDICO");
        lblCadastroJuridica.setForeground(new Color(255, 255, 255));
        lblCadastroJuridica.setFont(new Font("Segoe UI", Font.BOLD, 50));
        lblCadastroJuridica.setBounds(253, 11, 516, 47);
        cadastroJuridicaPanel.add(lblCadastroJuridica);

        JPanel cadastroFisicaPanel = new JPanel();
        cadastroFisicaPanel.setOpaque(false);
        cadastroFisicaPanel.setLayout(null);
        JLabel lblCadastroFisica = new JLabel("CADASTRO FÍSICO");
        lblCadastroFisica.setForeground(new Color(255, 255, 255));
        lblCadastroFisica.setFont(new Font("Segoe UI", Font.BOLD, 50));
        lblCadastroFisica.setBounds(299, 11, 442, 47);
        cadastroFisicaPanel.add(lblCadastroFisica);

        // Adicionar os painéis ao contentPanel
        contentPanel.add(cadastroJuridicaPanel, "CadastroJuridica");
        
        JPanel painelCadastroJuridico = new JPanel();
        painelCadastroJuridico.setBounds(50, 101, 910, 537);
        cadastroJuridicaPanel.add(painelCadastroJuridico);
        painelCadastroJuridico.setLayout(null);
        contentPanel.add(cadastroFisicaPanel, "CadastroFisica");
        
        JPanel painelCadastroFisico = new JPanel();
        painelCadastroFisico.setLayout(null);
        painelCadastroFisico.setBounds(50, 101, 910, 537);
        cadastroFisicaPanel.add(painelCadastroFisico);

        // Botões de navegação
        JButton shutdownButton = new JButton("");
        shutdownButton.setToolTipText("Sair");
        shutdownButton.setBounds(1130, 603, 60, 60);
        frame.getContentPane().add(shutdownButton);
        shutdownButton.setIcon(new ImageIcon(Main.class.getResource("/resources/images/off.png")));
        shutdownButton.setOpaque(false); // Deixa o fundo transparente
        shutdownButton.setBorderPainted(false); // Remove a borda do botão
        shutdownButton.setContentAreaFilled(false);
        
        JButton btnHome = new JButton("");
        btnHome.setToolTipText("Home");
        btnHome.setIcon(new ImageIcon(Main.class.getResource("/resources/images/Homebotão.png")));
        btnHome.setBounds(37, 164, 105, 100);
        frame.getContentPane().add(btnHome);

        JButton btnCadastro = new JButton("");
        btnCadastro.setToolTipText("Cadastrar Cliente");
        btnCadastro.setIcon(new ImageIcon(Main.class.getResource("/resources/images/CADASTRO DE CLIENRTESPlus.jpg")));
        btnCadastro.setBounds(37, 286, 105, 100);
        frame.getContentPane().add(btnCadastro);
        
        JButton botaoJuridica = new JButton(""); 
        botaoJuridica.setIcon(new ImageIcon(Main.class.getResource("/resources/images/botãoJurídica.png"))); 
        botaoJuridica.setBounds(171, 164, 312, 415); 
        cadastroPanel.add(botaoJuridica); 
        
        JButton botaoFisica = new JButton(""); 
        botaoFisica.setIcon(new ImageIcon(Main.class.getResource("/resources/images/botãoFísica.png"))); 
        botaoFisica.setBounds(583, 164, 312, 415); 
        cadastroPanel.add(botaoFisica); 

        JButton btnSeguros = new JButton("");
        btnSeguros.setToolTipText("Seguros");
        btnSeguros.setIcon(new ImageIcon(Main.class.getResource("/resources/images/SegurosBotão.png")));
        btnSeguros.setBounds(37, 406, 105, 100);
        frame.getContentPane().add(btnSeguros);

        JButton btnRelatorios = new JButton("");
        btnRelatorios.setToolTipText("Dados");
        btnRelatorios.setIcon(new ImageIcon(Main.class.getResource("/resources/images/Lupa.jpg")));
        btnRelatorios.setBounds(37, 529, 105, 100);
        frame.getContentPane().add(btnRelatorios);
    
        // Adicionar ação nos botões para mudar o painel de conteúdo
        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "Home");
            }
        });

        btnCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "Cadastro");
            }
        });

        btnSeguros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "Seguros");
            }
        });

        btnRelatorios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "Relatórios");
            }
        });

        botaoJuridica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "CadastroJuridica");
            }
        });

        botaoFisica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "CadastroFisica");
            }
        });

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
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
            }
        }
    }
}
