import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQResultSequence;
import net.xqj.exist.ExistXQDataSource;

public class actualizacionXQ {
	public static void main(String[] args) {
		try {
			// Configuramos conexión como hemos hecho en ocasiones anteriores 
			XQDataSource server = new ExistXQDataSource();
			server.setProperty("serverName", "192.168.95.102");
			server.setProperty("port", "8080");
			server.setProperty("user", "admin");
			server.setProperty("password", "austria");
			XQConnection conn = server.getConnection();
			XQExpression consulta = conn.createExpression();
			// Ejecutamos la expresión XQuery: actualiza el apellido del empleado con EMP_NO=7369 a 1009
			String actual = "update value " +"/EMPLEADOS/EMP_ROW[EMP_NO=7369]/APELLIDO " +"with 'NuevoApellido'";
			consulta.executeCommand(actual);
		} catch(XQException e)
		{
			e.printStackTrace();
		}
	}
}