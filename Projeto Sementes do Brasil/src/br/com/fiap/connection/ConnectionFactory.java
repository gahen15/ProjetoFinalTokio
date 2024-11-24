package br.com.fiap.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável pela criação de uma conexão com o banco de dados Oracle.
 * Esta classe utiliza o DriverManager para obter a conexão com o banco utilizando as credenciais fornecidas.
 */
public class ConnectionFactory {

    /**
     * URL de conexão com o banco de dados Oracle. 
     * Contém informações como o endereço do servidor, a porta e o nome do banco.
     */
    private static final String url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";

    /**
     * Nome de usuário para autenticação no banco de dados Oracle.
     */
    private static final String user = "tm06";
    
    /**
     * Senha para autenticação no banco de dados Oracle.
     */
    private static final String password = "11052007";

    /**
     * Estabelece uma conexão com o banco de dados Oracle usando as credenciais fornecidas.
     * 
     * @return Uma instância de {@link Connection} se a conexão for bem-sucedida, ou {@code null} se a conexão falhar.
     */
    public Connection conectar() {
        try {
            // Tenta estabelecer a conexão com o banco de dados
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            // Se ocorrer um erro ao tentar conectar, a exceção será capturada e o stack trace será impresso
            e.printStackTrace();
        }
        // Se a conexão falhar, retorna null
        return null;
    }
}

