import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Problema2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://192.168.95.10/ejemplo", "austria", "austria");
			
			Statement statement = conexion.createStatement();
			String sql = "CREATE VIEW nombresTotales "
					+ "	  AS SELECT dept_no, "
					+ "	  dnombre, "
					+ "	  COUNT(emp_no), "
					+ "	  AVG(salario) "
					+ "	  FROM depart JOIN emple USING(dept_no)"
					+ "	  GROUP BY dept_no";
			statement.executeUpdate(sql);
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
