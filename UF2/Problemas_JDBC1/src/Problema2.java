import java.sql.*;

public class Problema2 {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://192.168.95.10/ejemplo", "austria", "austria");
			Statement sentencia = conexion.createStatement();
			String sql = "SELECT * FROM emple JOIN depart USING(dept_no) WHERE salario = (SELECT MAX(salario) FROM emple);";
			ResultSet result = sentencia.executeQuery(sql);
			while (result.next()) {
				System.out.printf("%s, %d, %s, %n", result.getString("apellido"), result.getInt("salario"), result.getString("dnombre"));
			}
			result.close();
			sentencia.close();
			conexion.close();
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
