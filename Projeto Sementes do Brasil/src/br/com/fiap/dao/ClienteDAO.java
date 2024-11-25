package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.models.Apolice;
import br.com.fiap.models.Cliente;
import br.com.fiap.models.EstadoCivil;
import br.com.fiap.models.TipoSeguro;
import br.com.fiap.models.submodel.Empresa;
import br.com.fiap.models.submodel.PessoaFisica;

/**
 * Classe responsável por realizar operações no banco de dados para a entidade
 * Cliente. Inclui métodos para inserir, buscar, listar, e deletar clientes, bem
 * como gerenciar apólices.
 */
public class ClienteDAO {

	private Connection connection;

	/**
	 * Construtor da classe ClienteDAO.
	 *
	 * @param connection Conexão com o banco de dados.
	 */
	public ClienteDAO(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Busca o tipo de seguro no banco de dados pelo ID.
	 *
	 * @param idTipoSeguro ID do tipo de seguro a ser buscado.
	 * @return O objeto TipoSeguro correspondente ao ID fornecido.
	 */

	public TipoSeguro buscarTipoSeguroPorId(long idTipoSeguro) {
		TipoSeguro tipoSeguro = null;
		String sql = "SELECT * FROM TipoSeguro WHERE idTipoSeguro = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setLong(1, idTipoSeguro);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					tipoSeguro = new TipoSeguro(rs.getLong("idTipoSeguro"), rs.getString("descricao"),
							rs.getString("categoria"), rs.getDouble("valor") // Recuperando o valor
					);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao buscar TipoSeguro", e);
		}

		return tipoSeguro;
	}
	  /**
     * Insere uma nova apólice de seguro para um cliente no banco de dados.
     * 
     * @param apolice Objeto contendo os dados da apólice.
     * @param cliente Objeto cliente associado à apólice.
     */
	public void InserirApolice(Apolice apolice, Cliente cliente) {

	}
	  /**
     * Insere um novo cliente no banco de dados.
     * Dependendo do tipo de cliente (Pessoa Física ou Jurídica), os dados específicos serão inseridos.
     * 
     * @param cliente Objeto cliente a ser inserido.
     */
	public void InserirCliente(Cliente cliente) {
		String sql = "INSERT INTO T_Cliente (idCliente, nome, email, telefone, endereco, tipoCliente, dataCadastro) "
				+ "VALUES (seq_cliente.NEXTVAL, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getEmail());
			stmt.setString(3, cliente.getTelefone());
			stmt.setString(4, cliente.getEndereco());
			stmt.setString(5, cliente instanceof PessoaFisica ? "FISICA" : "JURIDICA");
			stmt.setDate(6, cliente.getDataCadastro());

			int affectedRows = stmt.executeUpdate();

			if (affectedRows > 0) {
				long idCliente;

				// Recuperar o ID gerado para o cliente
				try (Statement idStmt = connection.createStatement();
						ResultSet rs = idStmt.executeQuery("SELECT seq_cliente.CURRVAL FROM DUAL")) {
					if (rs.next()) {
						idCliente = rs.getLong(1);
					} else {
						throw new SQLException("Erro ao obter o ID do cliente inserido.");
					}
				}

				try {
					// Inserir dados específicos para Pessoa Física
					if (cliente instanceof PessoaFisica) {
						PessoaFisica pf = (PessoaFisica) cliente;
						String pfSql = "INSERT INTO T_PessoaFisica (idCliente, cpf, estadoCivil, profissao, dataNascimento) VALUES (?, ?, ?, ?, ?)";
						try (PreparedStatement pfStmt = connection.prepareStatement(pfSql)) {
							pfStmt.setLong(1, idCliente);
							pfStmt.setString(2, pf.getCpf());
							pfStmt.setString(3, pf.getEstadoCivil().name());
							pfStmt.setString(4, pf.getProfissao());
							pfStmt.setDate(5, pf.getDataNascimento()); // Adicionando a data de nascimento
							pfStmt.executeUpdate();
						}
					}
					// Inserir dados específicos para Empresa
					else if (cliente instanceof Empresa) {
						Empresa emp = (Empresa) cliente;
						String empSql = "INSERT INTO T_Empresa (idCliente, cnpj, nomeFantasia, razaoSocial) VALUES (?, ?, ?, ?)";
						try (PreparedStatement empStmt = connection.prepareStatement(empSql)) {
							empStmt.setLong(1, idCliente);
							empStmt.setString(2, emp.getCnpj());
							empStmt.setString(3, emp.getNomeFantasia());
							empStmt.setString(4, emp.getRazaoSocial());
							empStmt.executeUpdate();
						}
					}
				} catch (SQLException e) {
					// Em caso de erro, remover o cliente inserido
					String deleteSql = "DELETE FROM T_Cliente WHERE idCliente = ?";
					try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSql)) {
						deleteStmt.setLong(1, idCliente);
						deleteStmt.executeUpdate();

						// Ajusta a sequência, se necessário (exemplo Oracle)
						String adjustSeqSql = "ALTER SEQUENCE seq_cliente INCREMENT BY -1";
						try (PreparedStatement adjustSeqStmt = connection.prepareStatement(adjustSeqSql)) {
							adjustSeqStmt.executeUpdate();
						}

						// Confirma que a sequência foi ajustada
						String testSeqSql = "SELECT seq_cliente.nextval FROM dual";
						try (PreparedStatement testSeqStmt = connection.prepareStatement(testSeqSql)) {
							testSeqStmt.executeQuery(); // Pode adicionar o resultado, se necessário.
						}

						// Restaura a sequência para o incremento normal
						String restoreSeqSql = "ALTER SEQUENCE seq_cliente INCREMENT BY 1";
						try (PreparedStatement restoreSeqStmt = connection.prepareStatement(restoreSeqSql)) {
							restoreSeqStmt.executeUpdate();
						}

					} catch (SQLException deleteException) {
						throw new RuntimeException("Erro ao remover cliente e ajustar sequência.", deleteException);
					}

					throw new RuntimeException("Erro ao criar registro específico e cliente removido.", e);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao criar cliente", e);
		}
	}
	/**
     * Adiciona uma apólice de seguro a um cliente existente.
     *
     * @param idCliente ID do cliente que receberá a apólice.
     * @param apolice Objeto da apólice a ser adicionada.
     */
	public void adicionarApoliceAoCliente(long idCliente, Apolice apolice) {
		String sql = "INSERT INTO T_Apolice (idCliente, idTipoSeguro, dataEmissao, valor, status) "
				+ "VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setLong(1, idCliente); // Definir idCliente como chave estrangeira
			stmt.setLong(2, apolice.getTipoSeguro().getIdTipoSeguro()); // Definir idTipoSeguro para o tipo de seguro
			stmt.setDate(3, new java.sql.Date(apolice.getDataEmissao().getTime())); // Data de emissão
			stmt.setDouble(4, apolice.getValor()); // Valor da apólice
			stmt.setString(5, apolice.getStatus()); // Status da apólice
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao adicionar apólice ao cliente", e);
		}
	}

	/**
     * Deleta um cliente do banco de dados, removendo também as apólices associadas.
     * 
     * @param id ID do cliente a ser deletado.
     */
	public void deletarCliente(long id) {
	    String deleteApolicesSql = "DELETE FROM ClienteApolice WHERE idCliente = ?";
	    String deleteApoliceSql = "DELETE FROM T_Apolice WHERE idCliente = ?";
	    String deletePessoaFisicaSql = "DELETE FROM T_PessoaFisica WHERE idCliente = ?";
	    String deleteEmpresaSql = "DELETE FROM T_Empresa WHERE idCliente = ?";
	    String deleteClienteSeguroSql = "DELETE FROM ClienteSeguro WHERE idCliente = ?";  // Novo DELETE
	    String deleteClienteSql = "DELETE FROM T_Cliente WHERE idCliente = ?";

	    try {
	        // Excluir apólices associadas ao cliente na tabela ClienteApolice
	        try (PreparedStatement apolicesStmt = connection.prepareStatement(deleteApolicesSql)) {
	            apolicesStmt.setLong(1, id);
	            apolicesStmt.executeUpdate();
	        }

	        // Excluir apólices na tabela T_Apolice, se existirem
	        try (PreparedStatement apoliceStmt = connection.prepareStatement(deleteApoliceSql)) {
	            apoliceStmt.setLong(1, id);
	            apoliceStmt.executeUpdate();
	        }

	        // Excluir registros de Pessoa Física, se existir
	        try (PreparedStatement pfStmt = connection.prepareStatement(deletePessoaFisicaSql)) {
	            pfStmt.setLong(1, id);
	            pfStmt.executeUpdate();
	        }

	        // Excluir registros de Empresa, se existir
	        try (PreparedStatement empStmt = connection.prepareStatement(deleteEmpresaSql)) {
	            empStmt.setLong(1, id);
	            empStmt.executeUpdate();
	        }

	        // Excluir registros do Cliente na tabela ClienteSeguro
	        try (PreparedStatement clienteSeguroStmt = connection.prepareStatement(deleteClienteSeguroSql)) {
	            clienteSeguroStmt.setLong(1, id);
	            clienteSeguroStmt.executeUpdate();
	        }

	        // Excluir o Cliente de T_Cliente
	        try (PreparedStatement clienteStmt = connection.prepareStatement(deleteClienteSql)) {
	            clienteStmt.setLong(1, id);
	            int affectedRows = clienteStmt.executeUpdate();

	            if (affectedRows == 0) {
	                throw new RuntimeException("Nenhum cliente encontrado com o ID: " + id);
	            }
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao deletar cliente", e);
	    }
	}

	/**
     * Lista todos os clientes cadastrados no banco de dados.
     * Inclui clientes do tipo Pessoa Física e Jurídica.
     * 
     * @return Lista de objetos Cliente, que podem ser Pessoa Física ou Jurídica.
     */
	public List<Cliente> listarClientes() {
	    String sql = "SELECT c.idCliente, c.nome, c.email, c.telefone, c.endereco, c.tipoCliente, c.dataCadastro, "
	            + "pf.cpf, pf.estadoCivil, pf.profissao, pf.dataNascimento, emp.cnpj, emp.nomeFantasia, emp.razaoSocial "
	            + "FROM T_Cliente c "
	            + "LEFT JOIN T_PessoaFisica pf ON c.idCliente = pf.idCliente "
	            + "LEFT JOIN T_Empresa emp ON c.idCliente = emp.idCliente";

	    List<Cliente> clientes = new ArrayList<>();

	    try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
	        while (rs.next()) {
	            long idCliente = rs.getLong("idCliente");
	            String nome = rs.getString("nome");
	            String email = rs.getString("email");
	            String telefone = rs.getString("telefone");
	            String endereco = rs.getString("endereco");
	            String tipoCliente = rs.getString("tipoCliente");
	            Date dataCadastro = rs.getDate("dataCadastro");

	            // Verifica se é Pessoa Física ou Empresa
	            if ("FISICA".equals(tipoCliente)) {
	                String cpf = rs.getString("cpf");
	                String estadoCivil = rs.getString("estadoCivil");
	                String profissao = rs.getString("profissao");
	                Date dataNascimento = rs.getDate("dataNascimento");

	                PessoaFisica pessoaFisica = new PessoaFisica();
	                pessoaFisica.setIdCliente(idCliente);
	                pessoaFisica.setNome(nome);
	                pessoaFisica.setEmail(email);
	                pessoaFisica.setTelefone(telefone);
	                pessoaFisica.setEndereco(endereco);
	                pessoaFisica.setDataCadastro(dataCadastro);
	                pessoaFisica.setCpf(cpf);
	                if (estadoCivil != null && !estadoCivil.isEmpty()) {
	                    pessoaFisica.setEstadoCivil(EstadoCivil.valueOf(estadoCivil)); // Atribui estado civil somente se não for nulo
	                }
	                pessoaFisica.setProfissao(profissao);
	                pessoaFisica.setDataNascimento(dataNascimento);

	                clientes.add(pessoaFisica);
	            } else if ("JURIDICA".equals(tipoCliente)) {
	                String cnpj = rs.getString("cnpj");
	                String nomeFantasia = rs.getString("nomeFantasia");
	                String razaoSocial = rs.getString("razaoSocial");

	                Empresa empresa = new Empresa();
	                empresa.setIdCliente(idCliente);
	                empresa.setNome(nome);
	                empresa.setEmail(email);
	                empresa.setTelefone(telefone);
	                empresa.setEndereco(endereco);
	                empresa.setDataCadastro(dataCadastro);
	                empresa.setCnpj(cnpj);
	                empresa.setNomeFantasia(nomeFantasia);
	                empresa.setRazaoSocial(razaoSocial);

	                clientes.add(empresa);
	            }
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao listar clientes", e);
	    }

	    return clientes;
	}
	
	 /**
     * Busca um cliente pelo seu ID no banco de dados.
     * 
     * @param id ID do cliente a ser buscado.
     * @return O cliente correspondente ao ID fornecido.
     */
	public Cliente buscarClientePorId(long id) {
		String sql = "SELECT c.idCliente, c.nome, c.email, c.telefone, c.endereco, c.tipoCliente, c.dataCadastro, "
				+ "pf.cpf, pf.estadoCivil, pf.profissao, pf.dataNascimento, " + // Incluindo dataNascimento
				"emp.cnpj, emp.nomeFantasia, emp.razaoSocial " + "FROM T_Cliente c "
				+ "LEFT JOIN T_PessoaFisica pf ON c.idCliente = pf.idCliente "
				+ "LEFT JOIN T_Empresa emp ON c.idCliente = emp.idCliente " + "WHERE c.idCliente = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setLong(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					long idCliente = rs.getLong("idCliente");
					String nome = rs.getString("nome");
					String email = rs.getString("email");
					String telefone = rs.getString("telefone");
					String endereco = rs.getString("endereco");
					String tipoCliente = rs.getString("tipoCliente");
					Date dataCadastro = rs.getDate("dataCadastro");

					// Verifica se é Pessoa Física ou Empresa
					if ("FISICA".equals(tipoCliente)) {
						String cpf = rs.getString("cpf");
						String estadoCivil = rs.getString("estadoCivil");
						String profissao = rs.getString("profissao");
						Date dataNascimento = rs.getDate("dataNascimento"); // Obtendo data de nascimento

						PessoaFisica pessoaFisica = new PessoaFisica();
						pessoaFisica.setIdCliente(idCliente);
						pessoaFisica.setNome(nome);
						pessoaFisica.setEmail(email);
						pessoaFisica.setTelefone(telefone);
						pessoaFisica.setEndereco(endereco);
						pessoaFisica.setDataCadastro(dataCadastro);
						pessoaFisica.setCpf(cpf);
						pessoaFisica.setEstadoCivil(EstadoCivil.valueOf(estadoCivil));
						pessoaFisica.setProfissao(profissao);
						pessoaFisica.setDataNascimento(dataNascimento); // Definindo a data de nascimento

						return pessoaFisica;

					} else if ("JURIDICA".equals(tipoCliente)) {
						String cnpj = rs.getString("cnpj");
						String nomeFantasia = rs.getString("nomeFantasia");
						String razaoSocial = rs.getString("razaoSocial");

						Empresa empresa = new Empresa();
						empresa.setIdCliente(idCliente);
						empresa.setNome(nome);
						empresa.setEmail(email);
						empresa.setTelefone(telefone);
						empresa.setEndereco(endereco);
						empresa.setDataCadastro(dataCadastro);
						empresa.setCnpj(cnpj);
						empresa.setNomeFantasia(nomeFantasia);
						empresa.setRazaoSocial(razaoSocial);

						return empresa;
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao buscar cliente por ID", e);
		}

		return null; // Caso nenhum cliente seja encontrado
	}
	/**
	 * Atualiza os dados de um cliente no banco de dados. Dependendo do tipo do cliente,
	 * os dados específicos são atualizados nas tabelas apropriadas.
	 * 
	 * @param cliente Objeto cliente com os dados atualizados.
	 * @throws RuntimeException Caso ocorra um erro ao atualizar o cliente no banco de dados.
	 * 
	 * Este método realiza a atualização dos dados do cliente na tabela T_Cliente, e 
	 * se o cliente for do tipo Pessoa Física ou Empresa, ele também atualiza os dados 
	 * específicos na tabela correspondente (T_PessoaFisica ou T_Empresa).
	 */
	public void atualizarCliente(Cliente cliente) {
		String updateClienteSql = "UPDATE T_Cliente SET nome = ?, email = ?, telefone = ?, endereco = ?, dataCadastro = ? "
				+ "WHERE idCliente = ?";

		try (PreparedStatement stmt = connection.prepareStatement(updateClienteSql)) {
			// Atualiza os dados comuns a todos os clientes
			stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getEmail());
			stmt.setString(3, cliente.getTelefone());
			stmt.setString(4, cliente.getEndereco());
			stmt.setDate(5, cliente.getDataCadastro());
			stmt.setLong(6, cliente.getIdCliente());
			stmt.executeUpdate();

			// Atualiza dados específicos de Pessoa Física
			if (cliente instanceof PessoaFisica) {
				PessoaFisica pf = (PessoaFisica) cliente;
				String updatePfSql = "UPDATE T_PessoaFisica SET cpf = ?, estadoCivil = ?, profissao = ?, dataNascimento = ? "
						+ // Incluindo dataNascimento
						"WHERE idCliente = ?";
				try (PreparedStatement pfStmt = connection.prepareStatement(updatePfSql)) {
					pfStmt.setString(1, pf.getCpf());
					pfStmt.setString(2, pf.getEstadoCivil().name());
					pfStmt.setString(3, pf.getProfissao());
					pfStmt.setDate(4, pf.getDataNascimento()); // Atualizando a data de nascimento
					pfStmt.setLong(5, pf.getIdCliente());
					pfStmt.executeUpdate();
				}
			}

			// Atualiza dados específicos de Empresa
			else if (cliente instanceof Empresa) {
				Empresa emp = (Empresa) cliente;
				String updateEmpSql = "UPDATE T_Empresa SET cnpj = ?, nomeFantasia = ?, razaoSocial = ? "
						+ "WHERE idCliente = ?";
				try (PreparedStatement empStmt = connection.prepareStatement(updateEmpSql)) {
					empStmt.setString(1, emp.getCnpj());
					empStmt.setString(2, emp.getNomeFantasia());
					empStmt.setString(3, emp.getRazaoSocial());
					empStmt.setLong(4, emp.getIdCliente());
					empStmt.executeUpdate();
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao atualizar cliente", e);
		}
	}

}
