import javax.xml.xquery.*;
import net.xqj.exist.ExistXQDataSource;

public class EmpleadosDelDepartamento10 {
	public static void main(String[] args) {
		try {
			XQDataSource server = new ExistXQDataSource();
			server.setProperty("serverName", "192.168.95.102");
			server.setProperty("port", "8080");
			server.setProperty("user", "admin");
			server.setProperty("password", "austria");
			XQConnection conn = server.getConnection();
			XQPreparedExpression consulta;
			XQResultSequence resultado;
			consulta = conn.prepareExpression("for $emp in doc('nueva/empleados.xml')/EMPLEADOS/EMP_ROW[DEPT_NO=10] return $pr");
			resultado = consulta.executeQuery();
			while (resultado.next()) {
				System.out.println(resultado.getItemAsString(null));
			}
			conn.close();
		} catch (XQException ex) {
			System.out.println("Error al operar" + ex.getMessage());
		}
	}
}

