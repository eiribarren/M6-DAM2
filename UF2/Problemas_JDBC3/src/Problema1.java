import java.sql.*;
import java.io.*;

public class Problema1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://192.168.95.10/ejemplo", "austria", "austria");
			
			System.out.print("Introduce el número del departamento: ");
			String dept_no = br.readLine();
			
			System.out.print("Introduce el aumento de salario: ");
			String aumento = br.readLine();
			
			Statement statement = conexion.createStatement();
			String sql = "UPDATE emple SET salario = salario + " + aumento + " WHERE dept_no = " + dept_no;
			int filasAfectadas = statement.executeUpdate(sql);
			
			System.out.println( filasAfectadas + " filas afectadas.");
			
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
