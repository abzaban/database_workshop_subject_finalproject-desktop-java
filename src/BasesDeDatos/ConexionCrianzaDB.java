package BasesDeDatos;
import java.sql.*;

public class ConexionCrianzaDB {
	
	static private String url = null;
	static private Statement statement = null;
	static private Connection conn;
	
	private ConexionCrianzaDB() {}
	
	static synchronized public Statement getConexion() {
		return getConexion("localhost", "CRIANZA", "1433");
	}
	
	static synchronized public Statement getConexion(String hostname, String database, String puerto) {
		return getConexion(hostname, database, puerto, "sa", "06OctU99");
	}
	
	static synchronized public Statement getConexion(String hostname, String database, String puerto, String user, String password) {
		if(statement == null) {
			url = "jdbc:sqlserver://" + hostname + "\\SQLEXPRESS:" + puerto + ";databaseName=" + database + ";user=" + user + ";password="
				  + password + ";";
			try {
				conn = DriverManager.getConnection(url);
				statement = conn.createStatement();
			} catch(SQLException e) {
				return null;
			}
		}
		return statement;
	}
	
	static synchronized public void cierraConexion() {
		try {
			conn.close();
			statement.close();
		} catch(SQLException E) {}
	}
}
