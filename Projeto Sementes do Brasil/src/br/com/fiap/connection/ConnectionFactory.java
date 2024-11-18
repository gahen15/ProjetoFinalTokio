package br.com.fiap.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	
	private static final String url = "jdbc:oracle:thin:@localhost:1521/XEPDB1";

	private static final String user = "GAHEN";
	private static final String password = "bielgamers12";
	
	
	
	public Connection conectar() {
		try {

			return DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
