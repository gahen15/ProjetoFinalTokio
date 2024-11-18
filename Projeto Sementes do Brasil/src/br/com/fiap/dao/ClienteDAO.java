package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.models.Cliente;
import br.com.fiap.models.EstadoCivil;
import br.com.fiap.models.submodel.Empresa;
import br.com.fiap.models.submodel.PessoaFisica;

public class ClienteDAO {

	private Connection connection;

	public ClienteDAO(Connection connection) {
		this.connection = connection;
	}

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
						String pfSql = "INSERT INTO T_PessoaFisica (idCliente, cpf, estadoCivil, profissao) VALUES (?, ?, ?, ?)";
						try (PreparedStatement pfStmt = connection.prepareStatement(pfSql)) {
							pfStmt.setLong(1, idCliente);
							pfStmt.setString(2, pf.getCpf());
							pfStmt.setString(3, pf.getEstadoCivil().name());
							pfStmt.setString(4, pf.getProfissao());
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

	public void deletarCliente(long id) {
		String deletePessoaFisicaSql = "DELETE FROM T_PessoaFisica WHERE idCliente = ?";
		String deleteEmpresaSql = "DELETE FROM T_Empresa WHERE idCliente = ?";
		String deleteClienteSql = "DELETE FROM T_Cliente WHERE idCliente = ?";

		try {
			// Excluir Pessoa Física, se existir
			try (PreparedStatement pfStmt = connection.prepareStatement(deletePessoaFisicaSql)) {
				pfStmt.setLong(1, id);
				pfStmt.executeUpdate();
			}

			// Excluir Empresa, se existir
			try (PreparedStatement empStmt = connection.prepareStatement(deleteEmpresaSql)) {
				empStmt.setLong(1, id);
				empStmt.executeUpdate();
			}

			// Excluir o Cliente
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

	public List<Cliente> listarClientes() {
		String sql = "SELECT c.idCliente, c.nome, c.email, c.telefone, c.endereco, c.tipoCliente, c.dataCadastro, "
				+ "pf.cpf, pf.estadoCivil, pf.profissao, emp.cnpj, emp.nomeFantasia, emp.razaoSocial "
				+ "FROM T_Cliente c " + "LEFT JOIN T_PessoaFisica pf ON c.idCliente = pf.idCliente "
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

	public Cliente buscarClientePorId(long id) {
	    String sql = "SELECT c.idCliente, c.nome, c.email, c.telefone, c.endereco, c.tipoCliente, c.dataCadastro, " +
	                 "pf.cpf, pf.estadoCivil, pf.profissao, emp.cnpj, emp.nomeFantasia, emp.razaoSocial " +
	                 "FROM T_Cliente c " +
	                 "LEFT JOIN T_PessoaFisica pf ON c.idCliente = pf.idCliente " +
	                 "LEFT JOIN T_Empresa emp ON c.idCliente = emp.idCliente " +
	                 "WHERE c.idCliente = ?";

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

	public void atualizarCliente(Cliente cliente) {
	    String updateClienteSql = "UPDATE T_Cliente SET nome = ?, email = ?, telefone = ?, endereco = ?, dataCadastro = ? " +
	                              "WHERE idCliente = ?";

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
	            String updatePfSql = "UPDATE T_PessoaFisica SET cpf = ?, estadoCivil = ?, profissao = ? " +
	                                 "WHERE idCliente = ?";
	            try (PreparedStatement pfStmt = connection.prepareStatement(updatePfSql)) {
	                pfStmt.setString(1, pf.getCpf());
	                pfStmt.setString(2, pf.getEstadoCivil().name());
	                pfStmt.setString(3, pf.getProfissao());
	                pfStmt.setLong(4, pf.getIdCliente());
	                pfStmt.executeUpdate();
	            }
	        }

	        // Atualiza dados específicos de Empresa
	        else if (cliente instanceof Empresa) {
	            Empresa emp = (Empresa) cliente;
	            String updateEmpSql = "UPDATE T_Empresa SET cnpj = ?, nomeFantasia = ?, razaoSocial = ? " +
	                                  "WHERE idCliente = ?";
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
