import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class Problema3 {
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String localidad;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://192.168.95.10/ejemplo", "austria", "austria");
			System.out.print("Introduce una localidad: ");
			localidad = br.readLine();
			Statement sentencia = conexion.createStatement();
			String sql = "SELECT dnombre, GROUP_CONCAT(apellido) AS apellidos FROM depart LEFT JOIN emple USING(dept_no) WHERE loc = '" + localidad + "' GROUP BY dept_no;";
			ResultSet result = sentencia.executeQuery(sql);
			while (result.next()) {
				System.out.printf("%s: %s %n", result.getString("dnombre"), result.getString("apellidos"));
			}
			result.close();
			sentencia.close();
			conexion.close();
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
