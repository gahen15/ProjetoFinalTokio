package br.com.fiap.interfacegrafica;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Classe que representa a tela de login do sistema. Possui campos para o usuário
 * inserir suas credenciais e um botão para realizar o login.
 */
public class Login {

    private JFrame frame;
    private JTextField textField;
    private JPasswordField passwordField;
    private final Action action = new SwingAction();

    /**
     * Método principal que inicia a aplicação e exibe a tela de login.
     * 
     * @param args Argumentos de linha de comando.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login window = new Login();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Construtor da classe Login, inicializa os componentes da tela.
     */
    public Login() {
        initialize();
    }

    /**
     * Inicializa os componentes da interface gráfica.
     * Cria os elementos da tela, como campos de texto, rótulos e botões.
     */
    private void initialize() {
        // Criação da janela principal
        frame = new JFrame();
        frame.setBounds(470, 190, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Rótulo de "Usuário"
        JLabel lblNewLabel = new JLabel("Usuário");
        lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        lblNewLabel.setBounds(190, 111, 61, 14);
        frame.getContentPane().add(lblNewLabel);

        // Rótulo de "Senha"
        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setFont(new Font("Verdana", Font.BOLD, 12));
        lblSenha.setBounds(195, 163, 46, 14);
        frame.getContentPane().add(lblSenha);

        // Campo de texto para o nome de usuário
        textField = new JTextField();
        textField.setBounds(163, 132, 105, 20);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        // Campo de senha
        passwordField = new JPasswordField();
        passwordField.setBounds(163, 182, 105, 20);
        frame.getContentPane().add(passwordField);

        // Imagem da logo
        JLabel lblNewLabel_1 = new JLabel("New label");
        lblNewLabel_1.setIcon(new ImageIcon(Login.class.getResource("/resources/logo-tokio-marine-seguradora-1024.png")));
        lblNewLabel_1.setBounds(67, -54, 305, 223);
        frame.getContentPane().add(lblNewLabel_1);

        // Botão de login que aciona a ação de autenticação
        JButton btnNewButton = new JButton("Logar");
        btnNewButton.setAction(action);
        btnNewButton.setBounds(178, 213, 76, 23);
        frame.getContentPane().add(btnNewButton);

        // Botão de fechamento da aplicação
        JButton btnNewButton_1 = new JButton("");
        btnNewButton_1.setIcon(new ImageIcon(Login.class.getResource("/resources/shutdown-1.png")));
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();  // Fecha a janela de login
            }
        });
        btnNewButton_1.setBounds(393, 220, 31, 30);
        frame.getContentPane().add(btnNewButton_1);
    }

    /**
     * Classe que define a ação do botão "Logar". Realiza a validação do usuário e
     * senha e redireciona para a tela principal se o login for bem-sucedido.
     */
    private class SwingAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        /**
         * Construtor da ação do botão de login.
         */
        public SwingAction() {
            putValue(NAME, "Logar");
            putValue(SHORT_DESCRIPTION, "Realiza o login no sistema.");
        }

        /**
         * Método que é chamado quando o botão de login é clicado. Ele captura as credenciais
         * inseridas pelo usuário e tenta validá-las. Se o login for bem-sucedido, a tela de 
         * login é fechada e a tela principal é aberta. Caso contrário, uma mensagem de erro é exibida.
         * 
         * @param e O evento de ação disparado pelo clique do botão.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                // Captura o usuário e a senha inseridos
                String usuario = textField.getText();
                String senha = new String(passwordField.getPassword()); // Converte de char[] para String

                // Cria uma instância de 'Usuarios' e valida o login
                Usuarios login = new Usuarios();
                if (login.validarUsuario(usuario, senha)) {
                    // Se o login for válido, fecha a tela de login e abre a tela principal
                    frame.dispose();
                    new Main();  // Chama a tela principal
                } else {
                    // Se o login for inválido, exibe mensagem de erro
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos!");
                    });
                }
            } catch (Exception ex) {
                // Imprime o erro completo no console para diagnóstico
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro inesperado: " + ex.getMessage());
            }
        }
    }
}
