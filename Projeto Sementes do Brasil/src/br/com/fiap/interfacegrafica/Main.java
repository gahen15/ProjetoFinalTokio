package br.com.fiap.interfacegrafica;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import br.com.fiap.controller.AppController;
import br.com.fiap.models.Apolice;
import br.com.fiap.models.Cliente;
import br.com.fiap.models.EstadoCivil;
import br.com.fiap.models.TipoSeguro;
import br.com.fiap.models.submodel.Empresa;
import br.com.fiap.models.submodel.PessoaFisica;


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
	 * Launch the application.*/

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

    public Main() throws SQLException {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() throws SQLException {
    	 AppController app = AppController.getInstance();
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
     // Criando o painel para as apólices
        JPanel apolicesPanel = new JPanel();
        apolicesPanel.setOpaque(false);
        apolicesPanel.setLayout(null);

        // Título da página de apólices
        JLabel lblApolices = new JLabel("APÓLICES");
        lblApolices.setForeground(new Color(0, 0, 0));
        lblApolices.setFont(new Font("Segoe UI", Font.BOLD, 50));
        lblApolices.setBounds(434, 58, 232, 67);
        apolicesPanel.add(lblApolices);

        // Adicionando ao conteúdo
        contentPanel.add(apolicesPanel, "Apolices");

        // Criando o painel de lista de apólices
        JPanel listaApolices = new JPanel();
        listaApolices.setBounds(77, 165, 908, 594);
        listaApolices.setLayout(null);
        apolicesPanel.add(listaApolices);

        // Criando o modelo para a JList
        DefaultListModel<String> modelApolicesTable = new DefaultListModel<>();
        JList<String> listApolices = new JList<>(modelApolicesTable);  // Passando o modelo para a JList
        listApolices.setLayoutOrientation(JList.VERTICAL);  
        listApolices.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Adicionando a JList dentro de um JScrollPane para rolagem
        JScrollPane scrollPaneApolices = new JScrollPane(listApolices);
        scrollPaneApolices.setBounds(10, 144, 595, 243);
        scrollPaneApolices.setOpaque(false);
        listaApolices.add(scrollPaneApolices);

        // Campo de texto para ID do cliente
        JTextField idClienteFieldApolice = new JTextField();
        idClienteFieldApolice.setBounds(648, 197, 250, 40);
        listaApolices.add(idClienteFieldApolice);

        // Campo de texto para ID da apólice (nova funcionalidade)
        JTextField idApoliceField = new JTextField();
        idApoliceField.setBounds(648, 297, 250, 40); // Coloque em uma posição adequada
        listaApolices.add(idApoliceField);

        // Botão para pesquisar cliente
        JButton btnPesquisarApolice = new JButton("Pesquisar Cliente");
        btnPesquisarApolice.setBounds(648, 247, 250, 40);
        listaApolices.add(btnPesquisarApolice);

        // Botões para associar e remover apólice
        JButton btnAssociarApolice = new JButton("Associar Apólice");
        btnAssociarApolice.setBounds(648, 347, 250, 40);
        btnAssociarApolice.setEnabled(false);  // Inicialmente desabilitado
        listaApolices.add(btnAssociarApolice);

        JButton btnRemoverApolice = new JButton("Remover Apólice");
        btnRemoverApolice.setBounds(648, 397, 250, 40);
        btnRemoverApolice.setEnabled(false);  // Inicialmente desabilitado
        listaApolices.add(btnRemoverApolice);

        // Lógica para pesquisar cliente e apólices
     /*   btnPesquisarApolice.addActionListener(e -> {
            String idClienteText = idClienteFieldApolice.getText();
            String idApoliceText = idApoliceField.getText();

            if (!idClienteText.isEmpty()) {
                try {
                    long idCliente = Long.parseLong(idClienteText);
                    Cliente cliente = app.buscarClientePorId(idCliente);  // Método para buscar cliente

                    if (cliente != null) {
                        // Se o ID da apólice for fornecido, buscar por ID da apólice
                        if (!idApoliceText.isEmpty()) {
                            long idApolice = Long.parseLong(idApoliceText);
                            Apolice apolice = app.buscarApolicePorId(idApolice);

                            if (apolice != null && apolice.getCliente().getIdCliente() == idCliente) {
                                modelApolicesTable.clear();  // Limpa a lista antes de adicionar
                                modelApolicesTable.addElement(apolice.toString());  // Exibe a apólice

                                btnAssociarApolice.setEnabled(true);
                                btnRemoverApolice.setEnabled(true);
                            } else {
                                JOptionPane.showMessageDialog(frame, "Apólice não encontrada ou não pertence a este cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
                                btnAssociarApolice.setEnabled(false);
                                btnRemoverApolice.setEnabled(false);
                            }
                        } else {
                            // Se o ID da apólice não for fornecido, listar todas as apólices do cliente
                            List<Apolice> apolicesAssociadas = app.listarApolicesDoCliente(idCliente);
                            modelApolicesTable.clear();  // Limpa a lista antes de adicionar

                            for (Apolice apolice : apolicesAssociadas) {
                                modelApolicesTable.addElement(apolice.toString());  // Exibe as apólices
                            }

                            btnAssociarApolice.setEnabled(true);
                            btnRemoverApolice.setEnabled(true);
                        }
                        
                        // Atualizar a interface
                        listApolices.revalidate();  // Forçar a revalidação da JList
                        listApolices.repaint();     // Forçar o repintar da JList
                    } else {
                        JOptionPane.showMessageDialog(frame, "Cliente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                        btnAssociarApolice.setEnabled(false);
                        btnRemoverApolice.setEnabled(false);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor, insira um ID válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Por favor, insira o ID do cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
*/

        JPanel cadastroPanel = new JPanel();
        cadastroPanel.setOpaque(false); // Garantir que o painel do conteúdo também seja transparente
        cadastroPanel.setLayout(null);
        JLabel lblCadastroDeClientes = new JLabel("CADASTRO DE CLIENTES");
        lblCadastroDeClientes.setForeground(new Color(0, 0, 0));
        lblCadastroDeClientes.setFont(new Font("Segoe UI", Font.BOLD, 50));
        lblCadastroDeClientes.setBounds(235, 62, 610, 47);
        cadastroPanel.add(lblCadastroDeClientes);

        JPanel segurosPanel = new JPanel();
        segurosPanel.setOpaque(false); // Garantir que o painel do conteúdo também seja transparente
        segurosPanel.setLayout(null);
        JLabel lblSeguros = new JLabel("SEGUROS");
        lblSeguros.setForeground(new Color(0, 0, 0));
        lblSeguros.setFont(new Font("Segoe UI", Font.BOLD, 50));
        lblSeguros.setBounds(434, 58, 232, 67);
        segurosPanel.add(lblSeguros);

        JPanel relatoriosPanel = new JPanel();
        relatoriosPanel.setOpaque(false); // Garantir que o painel do conteúdo também seja transparente
        relatoriosPanel.setLayout(null);
        JLabel lblDados = new JLabel("DADOS");
        lblDados.setBounds(440, 0, 175, 67);
        lblDados.setForeground(new Color(255, 255, 255));
        lblDados.setFont(new Font("Segoe UI", Font.BOLD, 50));
        relatoriosPanel.add(lblDados);
     // Criando o painel de lista de clientes
		JPanel listaClientes = new JPanel();
		listaClientes.setBounds(35, 81, 908, 594);
		listaClientes.setLayout(null);
		listaClientes.setOpaque(false);
		relatoriosPanel.add(listaClientes);

		// Criando o modelo da tabela (nome das colunas e dados)
		String[] colunasClientes = { "ID", "Nome", "Endereço", "Telefone", "Documento" }; // Adicionando "Documento" como coluna
		List<Cliente> listaClientes1 = app.listarClientes(); // Obtém todos os clientes

		// Criando uma lista de dados para a tabela
		Object[][] dados = new Object[listaClientes1.size()][colunasClientes.length];

		for (int i = 0; i < listaClientes1.size(); i++) {
		    Cliente cliente = listaClientes1.get(i);
		    dados[i][0] = cliente.getIdCliente();  // ID do cliente
		    dados[i][1] = cliente.getNome();       // Nome do cliente
		    dados[i][2] = cliente.getEndereco();   // Endereço do cliente
		    dados[i][3] = cliente.getTelefone();   // Telefone do cliente

		    // Condicional para verificar o tipo de cliente e obter o documento correto
		    if (cliente instanceof PessoaFisica) {
		        PessoaFisica pf = (PessoaFisica) cliente;
		        dados[i][4] = pf.getCpf(); // CPF da pessoa física
		    } else if (cliente instanceof Empresa) {
		        Empresa empresa = (Empresa) cliente;
		        dados[i][4] = empresa.getCnpj(); // CNPJ da empresa
		    } else {
		        dados[i][4] = "Documento desconhecido"; // Caso não seja nem PF nem Empresa
		    }
		}

		// Criando o modelo da tabela com os dados e as colunas
		DefaultTableModel modelClientes = new DefaultTableModel(dados, colunasClientes);

		// Criando a JTable com o modelo de dados
		JTable tableClientes = new JTable(modelClientes);

		// Criando o TableRowSorter
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelClientes);
		tableClientes.setRowSorter(sorter);

		// Configurando a JTable
		tableClientes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableClientes.setFont(new Font("Arial", Font.PLAIN, 14));
		tableClientes.setRowHeight(25);
		tableClientes.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		tableClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableClientes.setShowGrid(true);
		tableClientes.setGridColor(Color.BLACK);
		tableClientes.setOpaque(false);

		// Ajustando o tamanho das colunas
		for (int i = 0; i < tableClientes.getColumnCount(); i++) {
		    int maxWidth = 0;
		    for (int j = 0; j < tableClientes.getRowCount(); j++) {
		        TableCellRenderer renderer = tableClientes.getCellRenderer(j, i);
		        Component comp = tableClientes.prepareRenderer(renderer, j, i);
		        maxWidth = Math.max(comp.getPreferredSize().width, maxWidth);
		    }
		    tableClientes.getColumnModel().getColumn(i).setPreferredWidth(maxWidth + 10);
		}

		// Adicionando a tabela dentro de um JScrollPane
		JScrollPane scrollPaneClientes = new JScrollPane(tableClientes);
		scrollPaneClientes.setBounds(10, 49, 526, 521);
		listaClientes.add(scrollPaneClientes);

		// Criando os componentes de pesquisa
		JTextField txtPesquisar = new JTextField();
		txtPesquisar.setBounds(10, 10, 200, 30);
		listaClientes.add(txtPesquisar);

		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setBounds(220, 10, 120, 30);
		listaClientes.add(btnPesquisar);

		JButton btnLimparBusca = new JButton("Limpar Busca");
		btnLimparBusca.setBounds(350, 10, 120, 30);
		listaClientes.add(btnLimparBusca);

		// Ação do botão Pesquisar
		btnPesquisar.addActionListener(e2 -> {
		    String termoPesquisa = txtPesquisar.getText().trim();
		    if (!termoPesquisa.isEmpty()) {
		        try {
		            // Tentando filtrar por ID (primeira coluna)
		            int id = Integer.parseInt(termoPesquisa);
		            sorter.setRowFilter(RowFilter.numberFilter(RowFilter.ComparisonType.EQUAL, id, 0));
		        } catch (NumberFormatException ex) {
		            // Caso não seja um número, filtra por Documento (quarta coluna)
		            sorter.setRowFilter(RowFilter.regexFilter(termoPesquisa, 4));
		        }
		    }
		});

		// Ação do botão Limpar Busca
		btnLimparBusca.addActionListener(e2 -> {
		    sorter.setRowFilter(null); // Remove o filtro e exibe todos os dados
		    txtPesquisar.setText(""); // Limpa o campo de texto
		});

		// Criando o botão Refresh
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setBounds(480, 10, 120, 30);
		listaClientes.add(btnRefresh);

		// Ação do botão Refresh
		// Ação do botão Refresh
		btnRefresh.addActionListener(e -> {
		    // Remover a tabela antiga, se houver
		    Component[] components = listaClientes.getComponents();
		    for (Component component : components) {
		        if (component instanceof JScrollPane) {
		            listaClientes.remove(component);  // Remove o JScrollPane com a tabela antiga
		        }
		    }

		    // Atualizando os dados da tabela
		    List<Cliente> listaClientes2 = app.listarClientes();  // Obtém a lista atualizada
		    Object[][] dados2 = new Object[listaClientes2.size()][colunasClientes.length];

		    for (int i = 0; i < listaClientes2.size(); i++) {
		        Cliente cliente = listaClientes2.get(i);
		        dados2[i][0] = cliente.getIdCliente();  // ID do cliente
		        dados2[i][1] = cliente.getNome();       // Nome do cliente
		        dados2[i][2] = cliente.getEndereco();   // Endereço do cliente
		        dados2[i][3] = cliente.getTelefone();   // Telefone do cliente

		        // Condicional para verificar o tipo de cliente e obter o documento correto
		        if (cliente instanceof PessoaFisica) {
		            PessoaFisica pf = (PessoaFisica) cliente;
		            dados2[i][4] = pf.getCpf(); // CPF da pessoa física
		        } else if (cliente instanceof Empresa) {
		            Empresa empresa = (Empresa) cliente;
		            dados2[i][4] = empresa.getCnpj(); // CNPJ da empresa
		        } else {
		            dados2[i][4] = "Documento desconhecido"; // Caso não seja nem PF nem Empresa
		        }
		    }

		    // Criando o modelo da tabela com os dados atualizados
		    DefaultTableModel modelClientes2 = new DefaultTableModel(dados2, colunasClientes);
		    JTable tableClientes2 = new JTable(modelClientes2);

		    // Criando o TableRowSorter
		    TableRowSorter<DefaultTableModel> sorter2 = new TableRowSorter<>(modelClientes2);
		    tableClientes2.setRowSorter(sorter2);

		    // Configurando a JTable
		    tableClientes2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		    tableClientes2.setFont(new Font("Arial", Font.PLAIN, 14));
		    tableClientes2.setRowHeight(25);
		    tableClientes2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		    tableClientes2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		    tableClientes2.setShowGrid(true);
		    tableClientes2.setGridColor(Color.BLACK);

		    // Ajustando o tamanho das colunas
		    for (int i = 0; i < tableClientes2.getColumnCount(); i++) {
		        int maxWidth = 0;
		        for (int j = 0; j < tableClientes2.getRowCount(); j++) {
		            TableCellRenderer renderer = tableClientes2.getCellRenderer(j, i);
		            Component comp = tableClientes2.prepareRenderer(renderer, j, i);
		            maxWidth = Math.max(comp.getPreferredSize().width, maxWidth);
		        }
		        tableClientes2.getColumnModel().getColumn(i).setPreferredWidth(maxWidth + 10);
		    }

		    // Adicionando a nova tabela dentro de um JScrollPane
		    JScrollPane scrollPaneClientes2 = new JScrollPane(tableClientes2);
		    scrollPaneClientes2.setBounds(10, 49, 526, 521);
		    listaClientes.add(scrollPaneClientes2);

		    // Atualizando a interface
		    listaClientes.revalidate();
		    listaClientes.repaint();
		});


        // Adicionar os painéis de conteúdo ao painel principal

        contentPanel.add(cadastroPanel, "Cadastro");
        contentPanel.add(segurosPanel, "Seguros");

     // Criando o painel que vai conter a lista de seguros
        JPanel listaSeguros = new JPanel();
        listaSeguros.setBounds(77, 165, 908, 594);
        listaSeguros.setLayout(null); // Usando o layout absoluto para posicionamento
        segurosPanel.add(listaSeguros);

        // Criando a JList para exibir os seguros
        JList<String> list = new JList<>();
        list.setLayoutOrientation(JList.VERTICAL);  // Usando uma disposição vertical para os itens
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Permitir apenas a seleção de um item por vez

        // Adicionando a JList dentro de um JScrollPane para permitir rolagem
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(10, 144, 595, 243);
        scrollPane.setOpaque(false);  // Ajustando a posição e o tamanho
        listaSeguros.add(scrollPane);
        listaSeguros.setOpaque(false);

        // Obtendo a lista de seguros
       
        List<TipoSeguro> listaSeguros1 = app.listarSeguros(); // Obtém todos os seguros

        // Criando o modelo da lista de seguros
        DefaultListModel<String> model = new DefaultListModel<>();
        for (TipoSeguro tipoSeguro : listaSeguros1) {
            model.addElement(tipoSeguro.toString());  // Exibe a descrição ou outros detalhes do seguro
        }
        list.setModel(model);

        // Personalizando a aparência da JList
        list.setOpaque(false); // Fundo mais suave
        list.setFont(new Font("Arial", Font.PLAIN, 20));  // Definindo uma fonte mais legível
        list.setBorder(BorderFactory.createLineBorder(Color.BLACK));  // Adicionando uma borda fina

        // Adicionando título
        JLabel lblNewLabel = new JLabel("Associar Seguro");
        lblNewLabel.setForeground(new Color(0, 0, 0));
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 24));
        lblNewLabel.setBounds(674, 154, 203, 40);
        listaSeguros.add(lblNewLabel);

        // Criando campo de texto para inserir ID do Cliente
        JTextField idClienteField = new JTextField();
        idClienteField.setBounds(648, 197, 250, 40);
        listaSeguros.add(idClienteField);

        // Criando botão de pesquisa para buscar o cliente
        JButton btnPesquisar2 = new JButton("Pesquisar Cliente");
        btnPesquisar2.setBounds(648, 247, 250, 40);
        listaSeguros.add(btnPesquisar2);

        // Criando botão para associar o seguro ao cliente
        JButton btnAssociar = new JButton("Associar Seguro");
        btnAssociar.setBounds(648, 297, 250, 40);
        btnAssociar.setEnabled(false); // Inicialmente, desabilitado
        listaSeguros.add(btnAssociar);

        // Criando botão para remover o seguro
        JButton btnRemover = new JButton("Remover Seguro");
        btnRemover.setBounds(648, 347, 250, 40);
        btnRemover.setEnabled(false); // Inicialmente, desabilitado
        listaSeguros.add(btnRemover);

        // Adicionando a tabela para mostrar as informações do cliente e os seguros associados
        String[] colunas = { "ID Cliente", "Nome", "Email", "Tipo Cliente", "Seguros Associados" };
        DefaultTableModel tableModel = new DefaultTableModel(colunas, 0);
        JTable tableCliente = new JTable(tableModel);
        tableCliente.setBounds(557, 310, 400, 200);
        JScrollPane scrollPane_1 = new JScrollPane(tableCliente);
        scrollPane_1.setSize(888, 70);
        scrollPane_1.setLocation(10, 23);
        listaSeguros.add(scrollPane_1);  // Adicionando a tabela ao painel
     // Ajustando a largura das colunas
        tableCliente.getColumnModel().getColumn(0).setPreferredWidth(80); // ID Cliente
        tableCliente.getColumnModel().getColumn(1).setPreferredWidth(150); // Nome
        tableCliente.getColumnModel().getColumn(2).setPreferredWidth(200); // Email
        tableCliente.getColumnModel().getColumn(3).setPreferredWidth(120); // Tipo Cliente
        tableCliente.getColumnModel().getColumn(4).setPreferredWidth(600); // Seguros Associados (aumentada)
        // Ouvinte para o botão de pesquisa para buscar o cliente e exibir seus dados
        btnPesquisar2.addActionListener(e -> {
            String idClienteText = idClienteField.getText();
            if (!idClienteText.isEmpty()) {
                try {
                    long idCliente = Integer.parseInt(idClienteText);
                    Cliente cliente = app.buscarClientePorId(idCliente);  // Chama o método do controlador para buscar o cliente

                    if (cliente != null) {
                        // Recuperando os seguros associados ao cliente
                        List<TipoSeguro> segurosAssociados = app.listarSegurosDoCliente(idCliente);

                        // Atualiza a tabela com os dados do cliente e os seguros
                        DefaultTableModel modelTable = (DefaultTableModel) tableCliente.getModel();
                        modelTable.setRowCount(0); // Limpa a tabela antes de adicionar o novo cliente
                        modelTable.addRow(new Object[]{
                                cliente.getIdCliente(), 
                                cliente.getNome(), 
                                cliente.getEmail(), 
                                cliente.getTipoCliente(), 
                                segurosAssociados.isEmpty() ? "Nenhum seguro associado" : segurosAssociados.stream()
                                	    .map(seguro -> String.valueOf(seguro.getIdTipoSeguro()))  // Usa o ID do seguro
                                	    .collect(Collectors.joining(", "))
                        });

                        // Habilita os botões de associar e remover
                        btnAssociar.setEnabled(true);
                        btnRemover.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Cliente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                        btnAssociar.setEnabled(false);
                        btnRemover.setEnabled(false);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor, insira um ID válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            } else {
                JOptionPane.showMessageDialog(frame, "Por favor, insira o ID do cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

     // Ouvinte para o botão de associação de seguro
        btnAssociar.addActionListener(e -> {
            String seguroSelecionado = list.getSelectedValue();
            String idClienteText = idClienteField.getText();

            // Lógica para validar e associar o seguro com base no tipo de cliente
            if (seguroSelecionado != null && !idClienteText.isEmpty()) {
                try {
                    int idCliente = Integer.parseInt(idClienteText);  // Converte o texto do ID do cliente para um número
                    Cliente cliente = app.buscarClientePorId(idCliente);  // Chama o método do controlador para buscar o cliente

                    if (cliente != null) {
                        TipoSeguro tipoSeguroSelecionado = listaSeguros1.stream()
                                .filter(tipo -> tipo.toString().equals(seguroSelecionado))
                                .findFirst()
                                .orElse(null);

                        if (tipoSeguroSelecionado != null) {
                            // Verificar se o tipo de seguro é compatível com o tipo de cliente
                            if (tipoSeguroSelecionado.getCategoria().equals(cliente.getTipoCliente())) {
                                // Chama o controlador para associar o seguro
                                app.associarSeguroAoCliente(idCliente, tipoSeguroSelecionado);
                                Apolice novaApolice = new Apolice(cliente, tipoSeguroSelecionado, tipoSeguroSelecionado.getValor());
                                novaApolice.setStatus("Ativa");
                                app.inserirApolice(novaApolice);
                                // Corrigindo chamada do método criarApolice
                               
                                JOptionPane.showMessageDialog(frame, "Seguro associado com sucesso.");
                            } else {
                                JOptionPane.showMessageDialog(frame, "O tipo de seguro não corresponde ao tipo de cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "Tipo de seguro não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Cliente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor, insira um ID válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao buscar cliente no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Por favor, selecione um seguro e insira o ID do cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

     // Ouvinte para o botão de remoção de seguro
        btnRemover.addActionListener(e -> {
            String seguroSelecionado = list.getSelectedValue();  // Obtém o seguro selecionado na lista
            String idClienteText = idClienteField.getText();  // Obtém o ID do cliente a partir do campo de texto

            // Lógica para validar e remover o seguro do cliente
            if (seguroSelecionado != null && !idClienteText.isEmpty()) {
                try {
                    long idCliente = Integer.parseInt(idClienteText);  // Converte o texto do ID do cliente para um número
                    Cliente cliente = app.buscarClientePorId(idCliente);  // Chama o método do controlador para buscar o cliente

                    if (cliente != null) {
                        TipoSeguro tipoSeguroSelecionado = listaSeguros1.stream()
                                .filter(tipo -> tipo.toString().equals(seguroSelecionado))
                                .findFirst()
                                .orElse(null);

                        if (tipoSeguroSelecionado != null) {
                            // Verificar se o cliente possui esse seguro associado
                            if (app.verificarSeguroAssociado(idCliente, tipoSeguroSelecionado)) {
                                // Chama o controlador para remover o seguro associado
                            
                            	app.alterarStatusApolice(idCliente,tipoSeguroSelecionado.getIdTipoSeguro(), "Inativo");
                                app.removerSeguroDoCliente(idCliente, tipoSeguroSelecionado);
                                JOptionPane.showMessageDialog(frame, "Seguro removido com sucesso.");
                            } else {
                                JOptionPane.showMessageDialog(frame, "Este seguro não está associado a este cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "Tipo de seguro não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Cliente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor, insira um ID válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao buscar cliente no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Por favor, selecione um seguro e insira o ID do cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });






        contentPanel.add(relatoriosPanel, "Relatórios");
    


		// Criar os painéis "CadastroJuridica" e "CadastroFisica"
		JPanel cadastroJuridicaPanel = new JPanel();
		cadastroJuridicaPanel.setOpaque(false);
		cadastroJuridicaPanel.setLayout(null);
		JLabel lblCadastroJuridica = new JLabel("CADASTRO JURÍDICO");
		lblCadastroJuridica.setBackground(new Color(0, 0, 0));
		lblCadastroJuridica.setForeground(new Color(0, 0, 0));
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
		JScrollPane scrollCadastroJuridica = new JScrollPane(painelCadastroJuridico,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

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
		JScrollPane scrollCadastroFisica = new JScrollPane(painelCadastroFisico, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

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

		JButton btnApolices = new JButton("");
		btnApolices.setToolTipText("Apolices");
		btnApolices.setIcon(new ImageIcon(Main.class.getResource("/resources/images/Homebotão.png")));
		btnApolices.setBounds(37, 164, 105, 100);
		frame.getContentPane().add(btnApolices);

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

		btnApolices.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "Apolices");
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

		cadastrarFisicoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String nome = nomeField.getText();
					String email = emailField.getText();
					String telefone = telefoneField.getText();
					String endereco = enderecoField.getText();
					String cpf = cpfField.getText();
					String profissao = profissaoField.getText();

					// Formatando a data
					String dataTexto = dataField.getText();
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					java.util.Date utilDate = dateFormat.parse(dataTexto); // Converte para java.util.Date
					java.sql.Date dataNascimento = new java.sql.Date(utilDate.getTime()); // Converte para java.sql.Date

					// Obtendo o estado civil a partir dos botões
					EstadoCivil estadoCivil = null;
					if (solteiroButton.isSelected()) {
						estadoCivil = EstadoCivil.SOLTEIRO;
					} else if (casadoButton.isSelected()) {
						estadoCivil = EstadoCivil.CASADO;
					} else if (divorcioButton.isSelected()) {
						estadoCivil = EstadoCivil.DIVORCIADO;
					} else if (viuvoButton.isSelected()) {
						estadoCivil = EstadoCivil.VIUVO;
					} else if (separadoButton.isSelected()) {
						estadoCivil = EstadoCivil.SEPARADO_JUDICIALMENTE;
					} else if (uniaoButton.isSelected()) {
						estadoCivil = EstadoCivil.UNIAO_ESTAVEL;
					}

					if (estadoCivil == null) {
						throw new IllegalArgumentException("Por favor, selecione um estado civil.");
					}

					// Chamando o método do controlador
					AppController app = AppController.getInstance();
					app.cadastrarClienteFisico(nome, email, telefone, endereco, cpf, estadoCivil, profissao,
							dataNascimento);
					nomeField.setText("");
					emailField.setText("");
					telefoneField.setText("");
					enderecoField.setText("");
					cpfField.setText("");
					profissaoField.setText("");
					dataField.setText("");
					buttonGroup.clearSelection();
					cardLayout.show(contentPanel, "Cadastro");
					JOptionPane.showMessageDialog(null, "Cadastro Realizado com Sucesso!");
					
					
				} catch (ParseException ex) {
					JOptionPane.showMessageDialog(null, "A data de nascimento deve estar no formato dd/MM/yyyy.",
							"Erro", JOptionPane.ERROR_MESSAGE);
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Erro ao salvar os dados no banco.", "Erro",
							JOptionPane.ERROR_MESSAGE);
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		cadastrarJuridicoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String nome = nomeFieldJuridica.getText();
					String email = emailFieldJuridica.getText();
					String telefone = telefoneFieldJuridica.getText();
					String endereco = enderecoFieldJuridica.getText();
					String cnpj = cnpjFieldJuridica.getText();
					String nomeFantasia = nomeFantasiaFieldJuridica.getText();
					String razaoSocial = razaoSocialFieldJuridica.getText();
					
					
					
					// Chamando o método do controlador
					AppController app = AppController.getInstance();
					app.cadastrarClienteEmpresa(nome, email, telefone, endereco, cnpj, nomeFantasia,razaoSocial);
					nomeFieldJuridica.setText("");
					emailFieldJuridica.setText("");
					telefoneFieldJuridica.setText("");
					enderecoFieldJuridica.setText("");
					cnpjFieldJuridica.setText("");
					nomeFantasiaFieldJuridica.setText("");
					razaoSocialFieldJuridica.setText("");
					cardLayout.show(contentPanel, "Cadastro");
					JOptionPane.showMessageDialog(null, "Cadastro Realizado com Sucesso!");
				}  catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Erro ao salvar os dados no banco.", "Erro",
							JOptionPane.ERROR_MESSAGE);
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

	}

	// Classe do painel de fundo
	class BackgroundPanel extends JPanel {
		private BufferedImage backgroundImage;

		public BackgroundPanel() {
			try {
			
				backgroundImage = ImageIO.read(getClass().getResource("/resources/images/backgroundHOME.jpg"));
			
				
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
