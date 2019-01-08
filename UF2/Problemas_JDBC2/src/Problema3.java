import java.sql.*;

public class Problema3 {

	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://192.168.95.10/ejemplo", "austria", "austria");
			DatabaseMetaData dbmd = conexion.getMetaData();
			ResultSet result = null;
			result = dbmd.getPrimaryKeys("ejemplo", null, "depart");
			System.out.println("Claves primarias: ");
			while ( result.next() ) {
				System.out.println("\t" + result.getString("COLUMN_NAME"));
			}
			result = dbmd.getExportedKeys("ejemplo", null, "depart");
			System.out.println("Claves foráneas que referencian a la tabla: ");
			while ( result.next() ) {
				System.out.println("\t" + result.getString("FKCOLUMN_NAME"));
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
