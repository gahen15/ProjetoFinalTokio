package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.models.TipoSeguro;


public class SeguroDAO {

    private Connection connection;

    public SeguroDAO(Connection connection) {
        this.connection = connection;
    }
 

    public List<TipoSeguro> listarTodosSeguros() {
        String sql = "SELECT idTipoSeguro, descricao, categoria FROM TipoSeguro";  // Consulta para todos os seguros

        List<TipoSeguro> listaSeguros = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                int idTipoSeguro = rs.getInt("idTipoSeguro");
                String descricao = rs.getString("descricao");
                String categoria = rs.getString("categoria");

                TipoSeguro tipoSeguro = new TipoSeguro(idTipoSeguro, descricao, categoria);
                listaSeguros.add(tipoSeguro);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os seguros", e);
        }

        return listaSeguros;
    }

    

    public void associarSeguroAoCliente(long idCliente, TipoSeguro seguro) {
        String sql = "INSERT INTO ClienteSeguro (idCliente, idTipoSeguro) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idCliente);
            stmt.setLong(2, seguro.getIdTipoSeguro());  // Aqui você usa o método do TipoSeguro, não do Seguro

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao associar seguro ao cliente", e);
        }
    }


    // Método para buscar um tipo de seguro pelo ID
    public TipoSeguro buscarTipoSeguroPorId(Long idTipoSeguro) throws SQLException {
        TipoSeguro tipoSeguro = null;
        String query = "SELECT idTipoSeguro, descricao, categoria FROM TipoSeguro WHERE idTipoSeguro = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, idTipoSeguro);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String descricao = rs.getString("descricao");
                    String categoria = rs.getString("categoria");
                    tipoSeguro = new TipoSeguro(idTipoSeguro, descricao, categoria);
                }
            }
        }
        
        return tipoSeguro;  // Retorna o tipo de seguro ou null se não encontrado
    }

    public void listarSegurosPorCliente(long idCliente) {
        String sql = "SELECT ts.idTipoSeguro, ts.descricao, ts.categoria " +
                     "FROM TipoSeguro ts " +
                     "INNER JOIN ClienteSeguro cs ON ts.idTipoSeguro = cs.idTipoSeguro " +
                     "WHERE cs.idCliente = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idTipoSeguro = rs.getInt("idTipoSeguro");
                    String descricao = rs.getString("descricao");
                    String categoria = rs.getString("categoria");

                  
                    System.out.println("ID Tipo Seguro: " + idTipoSeguro);
                    System.out.println("Descrição: " + descricao);
                    System.out.println("Categoria: " + categoria);
                    System.out.println("----------------------------");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar seguros por cliente", e);
        }
    }

   
    public void listarClientesPorTipoSeguro(long idTipoSeguro) {
        String sql = "SELECT c.idCliente, c.nome, c.email " +
                     "FROM T_Cliente c " +
                     "INNER JOIN ClienteSeguro cs ON c.idCliente = cs.idCliente " +
                     "WHERE cs.idTipoSeguro = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idTipoSeguro);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    long idCliente = rs.getLong("idCliente");
                    String nome = rs.getString("nome");
                    String email = rs.getString("email");

                    System.out.println("ID Cliente: " + idCliente);
                    System.out.println("Nome: " + nome);
                    System.out.println("Email: " + email);
                    System.out.println("----------------------------");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar clientes por tipo de seguro", e);
        }
    }}

  
   