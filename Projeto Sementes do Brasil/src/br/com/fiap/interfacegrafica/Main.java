package br.com.fiap.interfacegrafica;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.Button;

public class Main {

    private JFrame frame;
    private JPanel contentPanel; // Painel para o conteúdo da direita
    private CardLayout cardLayout; // Layout para trocar os painéis à direita
    private JTextField nomeField;
    private JTextField emailField;
    private JTextField telefoneField;
    private JTextField enderecoField;
    private JTextField cpfField;
    private JTextField profissaoField;
    private JTextField dataField;
    private JTextField nomeFieldJuridica;
    private JTextField nomeFantasiaFieldJuridica;
    private JTextField cnpjFieldJuridica;
    private JTextField telefoneFieldJuridica;
    private JTextField emailFieldJuridica;
    private JTextField enderecoFieldJuridica;
    private JTextField razaoSocialFieldJuridica;

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
        painelCadastroJuridico.setLayout(null);

        // Defina um tamanho maior para o painel dentro do JScrollPane
        painelCadastroJuridico.setPreferredSize(new Dimension(910, 650)); // Ajuste o tamanho conforme necessário
        painelCadastroJuridico.setBounds(50, 101, 910, 537);

        // Envolver o painel de cadastro jurídico com JScrollPane (somente vertical)
        JScrollPane scrollCadastroJuridica = new JScrollPane(painelCadastroJuridico, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        JLabel nomeLabel_1 = new JLabel("Nome Completo");
        nomeLabel_1.setBounds(10, 21, 841, 30);
        painelCadastroJuridico.add(nomeLabel_1);
        nomeLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 28));
        
        nomeFieldJuridica = new JTextField();
        nomeFieldJuridica.setBounds(10, 51, 841, 30);
        painelCadastroJuridico.add(nomeFieldJuridica);
        nomeFieldJuridica.setFont(new Font("Tahoma", Font.PLAIN, 12));
        nomeFieldJuridica.setColumns(10);
        
        JLabel lblNomeFantasia = new JLabel("Nome Fantasia");
        lblNomeFantasia.setBounds(10, 389, 569, 38);
        painelCadastroJuridico.add(lblNomeFantasia);
        lblNomeFantasia.setFont(new Font("Segoe UI", Font.BOLD, 28));
        
        nomeFantasiaFieldJuridica = new JTextField();
        nomeFantasiaFieldJuridica.setBounds(10, 424, 841, 30);
        painelCadastroJuridico.add(nomeFantasiaFieldJuridica);
        nomeFantasiaFieldJuridica.setColumns(10);
        
        JLabel lblCPF_1 = new JLabel("CNPJ");
        lblCPF_1.setBounds(10, 315, 569, 38);
        painelCadastroJuridico.add(lblCPF_1);
        lblCPF_1.setFont(new Font("Segoe UI", Font.BOLD, 28));
        
        cnpjFieldJuridica = new JTextField();
        cnpjFieldJuridica.setBounds(10, 350, 841, 30);
        painelCadastroJuridico.add(cnpjFieldJuridica);
        cnpjFieldJuridica.setColumns(10);
        
        JLabel lblTelefone_1 = new JLabel("Telefone");
        lblTelefone_1.setBounds(10, 240, 569, 38);
        painelCadastroJuridico.add(lblTelefone_1);
        lblTelefone_1.setFont(new Font("Segoe UI", Font.BOLD, 28));
        
        telefoneFieldJuridica = new JTextField();
        telefoneFieldJuridica.setBounds(10, 274, 841, 30);
        painelCadastroJuridico.add(telefoneFieldJuridica);
        telefoneFieldJuridica.setColumns(10);
        
        JLabel lblEmail_1 = new JLabel("E-mail");
        lblEmail_1.setBounds(10, 89, 569, 38);
        painelCadastroJuridico.add(lblEmail_1);
        lblEmail_1.setFont(new Font("Segoe UI", Font.BOLD, 28));
        
        emailFieldJuridica = new JTextField();
        emailFieldJuridica.setBounds(10, 125, 841, 30);
        painelCadastroJuridico.add(emailFieldJuridica);
        emailFieldJuridica.setColumns(10);
        
        JLabel lblEndereo_1 = new JLabel("Endereço");
        lblEndereo_1.setBounds(10, 166, 569, 38);
        painelCadastroJuridico.add(lblEndereo_1);
        lblEndereo_1.setFont(new Font("Segoe UI", Font.BOLD, 28));
        
        enderecoFieldJuridica = new JTextField();
        enderecoFieldJuridica.setBounds(10, 204, 841, 30);
        painelCadastroJuridico.add(enderecoFieldJuridica);
        enderecoFieldJuridica.setColumns(10);
        
        JLabel lblRazaoSocialJuridic = new JLabel("Razão Social");
        lblRazaoSocialJuridic.setBounds(10, 465, 569, 38);
        painelCadastroJuridico.add(lblRazaoSocialJuridic);
        lblRazaoSocialJuridic.setFont(new Font("Segoe UI", Font.BOLD, 28));
        
        razaoSocialFieldJuridica = new JTextField();
        razaoSocialFieldJuridica.setBounds(10, 501, 841, 30);
        painelCadastroJuridico.add(razaoSocialFieldJuridica);
        razaoSocialFieldJuridica.setColumns(10);
        
        JButton cadastrarJuridicoButton = new JButton("Cadastrar");
        cadastrarJuridicoButton.setBounds(748, 560, 103, 23);
        painelCadastroJuridico.add(cadastrarJuridicoButton);
        scrollCadastroJuridica.setBounds(50, 101, 910, 537);
        cadastroJuridicaPanel.add(scrollCadastroJuridica);

        contentPanel.add(cadastroFisicaPanel, "CadastroFisica");

        JPanel painelCadastroFisico = new JPanel();
        painelCadastroFisico.setLayout(null);

        // Defina um tamanho maior para o painel dentro do JScrollPane
        painelCadastroFisico.setPreferredSize(new Dimension(910, 650)); // Ajuste o tamanho conforme necessário
        painelCadastroFisico.setBounds(50, 101, 910, 537);

        // Envolver o painel de cadastro físico com JScrollPane (somente vertical)
        JScrollPane scrollCadastroFisica = new JScrollPane(painelCadastroFisico, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        nomeField = new JTextField();
        nomeField.setFont(new Font("Tahoma", Font.PLAIN, 12));
        nomeField.setBounds(10, 48, 841, 30);
        painelCadastroFisico.add(nomeField);
        nomeField.setColumns(10);
        
        emailField = new JTextField();
        emailField.setColumns(10);
        emailField.setBounds(10, 348, 841, 30);
        painelCadastroFisico.add(emailField);
        
        telefoneField = new JTextField();
        telefoneField.setColumns(10);
        telefoneField.setBounds(10, 274, 841, 30);
        painelCadastroFisico.add(telefoneField);
        
        enderecoField = new JTextField();
        enderecoField.setColumns(10);
        enderecoField.setBounds(10, 427, 841, 30);
        painelCadastroFisico.add(enderecoField);
        
        cpfField = new JTextField();
        cpfField.setColumns(10);
        cpfField.setBounds(10, 200, 841, 30);
        painelCadastroFisico.add(cpfField);
        
        profissaoField = new JTextField();
        profissaoField.setColumns(10);
        profissaoField.setBounds(10, 500, 841, 30);
        painelCadastroFisico.add(profissaoField);
        
        JRadioButton solteiroButton = new JRadioButton("Solteiro(a)");
        solteiroButton.setBounds(10, 567, 91, 23);
        painelCadastroFisico.add(solteiroButton);
        
        JRadioButton casadoButton = new JRadioButton("Casado(a)");
        casadoButton.setBounds(99, 567, 91, 23);
        painelCadastroFisico.add(casadoButton);
        
        JRadioButton viuvoButton = new JRadioButton("Viúvo(a)");
        viuvoButton.setBounds(192, 567, 83, 23);
        painelCadastroFisico.add(viuvoButton);
        
        JRadioButton separadoButton = new JRadioButton("Separado(a) Judicialmente");
        separadoButton.setBounds(277, 567, 181, 23);
        painelCadastroFisico.add(separadoButton);
        
        JRadioButton uniaoButton = new JRadioButton("União Estável");
        uniaoButton.setBounds(460, 567, 119, 23);
        painelCadastroFisico.add(uniaoButton);
        
        dataField = new JTextField();
        dataField.setColumns(10);
        dataField.setBounds(10, 125, 841, 30);
        painelCadastroFisico.add(dataField);
        
        JLabel nomeLabel = new JLabel("Nome Completo");
        nomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        nomeLabel.setBounds(10, 11, 221, 38);
        painelCadastroFisico.add(nomeLabel);
        
        JLabel lblDataDeNascimento = new JLabel("Data de Nascimento (dd/MM/YYYY)");
        lblDataDeNascimento.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblDataDeNascimento.setBounds(10, 89, 569, 38);
        painelCadastroFisico.add(lblDataDeNascimento);
        
        JLabel lblCPF = new JLabel("CPF");
        lblCPF.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblCPF.setBounds(10, 166, 569, 38);
        painelCadastroFisico.add(lblCPF);
        scrollCadastroFisica.setBounds(50, 101, 910, 537);
        cadastroFisicaPanel.add(scrollCadastroFisica);

        
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
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(casadoButton);
        buttonGroup.add(separadoButton);
        buttonGroup.add(solteiroButton);
        buttonGroup.add(uniaoButton);
        buttonGroup.add(viuvoButton);
        
        JLabel lblTelefone = new JLabel("Telefone");
        lblTelefone.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTelefone.setBounds(10, 240, 569, 38);
        painelCadastroFisico.add(lblTelefone);
        
        JLabel lblEmail = new JLabel("E-mail");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblEmail.setBounds(10, 315, 569, 38);
        painelCadastroFisico.add(lblEmail);
        
        JLabel lblEndereo = new JLabel("Endereço");
        lblEndereo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblEndereo.setBounds(10, 389, 569, 38);
        painelCadastroFisico.add(lblEndereo);
        
        JRadioButton divorcioButton = new JRadioButton("Divorciado(a)");
        divorcioButton.setBounds(581, 567, 112, 23);
        painelCadastroFisico.add(divorcioButton);
        buttonGroup.add(divorcioButton);
        
        JLabel lblProfisso = new JLabel("Profissão");
        lblProfisso.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblProfisso.setBounds(10, 465, 569, 38);
        painelCadastroFisico.add(lblProfisso);
        
        JLabel lblEstadoCivil = new JLabel("Estado Civil");
        lblEstadoCivil.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblEstadoCivil.setBounds(10, 533, 569, 38);
        painelCadastroFisico.add(lblEstadoCivil);
        
        JButton cadastrarFisicoButton = new JButton("Cadastrar");
        cadastrarFisicoButton.setBounds(748, 601, 103, 23);
        painelCadastroFisico.add(cadastrarFisicoButton);
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
