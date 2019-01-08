import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Problema3 {
	public static void main(String[] args) {
		String emp_no = "", apellido = "", oficio = "", dir = "", salario = "", comision = "", dept_no = "";
		try {
			emp_no = args[0];
			apellido = args[1];
			oficio = args[2];
			dir = args[3];
			salario = args[4];
			comision = args[5];
			dept_no = args[6];
		} catch ( IndexOutOfBoundsException e ) {
			System.out.println("Faltan argumentos, el orden es: emp_no, apellido, oficio, dir, salario, comision, dept_no");
			System.exit(-1);
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://192.168.95.10/ejemplo", "austria", "austria");
			
			Statement statement = conexion.createStatement();
			
			String sql = "SELECT * FROM depart WHERE dept_no = " + dept_no;
			
			ResultSet result = statement.executeQuery(sql);
			
			if ( result.next() == false ) {
				System.out.println("No existe el departamento");
				System.exit(-1);
			}
			
			sql = "SELECT * FROM emple WHERE emp_no = " + emp_no;
			result = statement.executeQuery(sql);
			if ( result.next() != false ) {
				System.out.println("El número del empleado ya se está utilizando");
				System.exit(-1);
			}
			
			if ( 0 > Integer.parseInt(salario) ) {
				System.out.println("El salario debe ser mayor que 0");
				System.exit(-1);
			}
			
			sql = "SELECT * FROM emple WHERE emp_no = " + dir;
			result = statement.executeQuery(sql);
			if  ( result.next() == false ) {
				System.out.println("No existe un empleado con el número indicado para el director");
				System.exit(-1);
			}
			
			if ( apellido.equals("") || apellido == null ) {
				System.out.println("El apellido no puede ser nulo");
				System.exit(-1);
			}
			
			if ( oficio.equals("") || oficio == null ) {
				System.out.println("El oficio no puede ser nulo");
				System.exit(-1);
			}
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			sql = "INSERT INTO emple (emp_no, apellido, oficio, dir, salario, comision, dept_no) "
					+ "VALUES(" + emp_no + ",'" + apellido + "','" + oficio + "'," + dir + "," + salario + "," + comision + "," + dept_no + ")";
			statement.executeUpdate(sql);
			
			
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
