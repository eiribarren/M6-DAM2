import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQResultSequence;
import net.xqj.exist.ExistXQDataSource;

public class CrearDepartamento50 {
	public static void main(String[] args) {
		try {
			// Configuramos conexi�n como hemos hecho en ocasiones anteriores 
			XQDataSource server = new ExistXQDataSource();
			server.setProperty("serverName", "192.168.95.102");
			server.setProperty("port", "8080");
			server.setProperty("user", "admin");
			server.setProperty("password", "austria");
			XQConnection conn = server.getConnection();
			XQExpression consulta = conn.createExpression();
			// Ejecutamos la expresi�n XQuery: actualiza el apellido del empleado con EMP_NO=7369 a 1009
			String actual = "update insert " 
							+ "<DEP_ROW> "
							+ "<DEPT_NO>50</DEPT_NO> "
							+ "<DNOMBRE>INFORM�TICA</DNOMBRE> "
							+ "<LOC>Valencia</LOC> "
							+ "</DEP_ROW>"
							+ "into /departamentos";
			consulta.executeCommand(actual);
		} catch(XQException e)
		{
			e.printStackTrace();
		}
	}
}
