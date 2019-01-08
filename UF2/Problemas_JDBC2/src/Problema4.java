import java.sql.*;

public class Problema4 {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://192.168.95.10/ejemplo", "austria", "austria");
			Statement statement = conexion.createStatement();
			String sql = "SELECT * FROM depart";
			ResultSet result = null;
			result = statement.executeQuery(sql);
			ResultSetMetaData rsmd = result.getMetaData();
			int numeroDeColumnas = rsmd.getColumnCount();
			System.out.println("Numero de columnas en la consulta: " + numeroDeColumnas);
			
			for ( int i = 1 ; i <= numeroDeColumnas; i++) {
				System.out.println("Tipo de la columna: " + rsmd.getColumnTypeName(i));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
