import java.sql.*;

public class Problema2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://192.168.95.10/ejemplo", "austria", "austria");
			DatabaseMetaData dbmd = conexion.getMetaData();
			ResultSet result = null;
			result = dbmd.getColumns(null, null, "depart", null);
			while ( result.next() ) {
				// Muestra el nombre, tipo, tamaño y si puede ser null de cada columna
				System.out.println("Nombre: " + result.getString(4) + "\t" +
								  " Tipo: " + result.getString(6) + "\t" +
								  " Tamaño: " + result.getInt(7) + "\t" +
								  " Puede ser null? " + result.getString(18));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
