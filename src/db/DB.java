package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	
	private static Connection conn = null; // instancia uma vari�vel do tipo Connection que vai ser respons�vel pela conex�o com o banco

	
	//m�todo para conectar com o banco (deve retornar um objeto do tipo connection)
	public static Connection getConnection() {
		if (conn == null) {
			try {
				Properties props = LoadProperties(); // carrega as credenciais para entrar no banco dentro de uma vari�vel do tipo propertis
				String url = props.getProperty("dburl"); // cria uma variavel para guardar a url que t� dentro do arquivo das credenciais atrav�s do metodo getProperty
				conn = DriverManager.getConnection(url, props); // atribui a variavel conn o driverManager.getConnection passando como parametro a url e as credenciais para acessar o banco
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conn;
	}

	private static Properties LoadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) { //leitura do arquivo db.properties
			Properties props = new Properties(); // instancia um objeto do tipo Properties
			props.load(fs); // pega as informa��es dentro do arquivo
			return props;
		} catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}

	public static void CloseConnection() {
		if (conn != null) { // se existir conex�o, ela � fechada.
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeStatement(Statement st) {
		if(st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}
