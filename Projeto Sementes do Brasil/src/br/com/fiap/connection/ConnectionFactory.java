package br.com.fiap.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	
	private static final String url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";

	private static final String user = "tm06";
	private static final String password = "11052007";
	
	
	
	public Connection conectar() {
		try {

			return DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
