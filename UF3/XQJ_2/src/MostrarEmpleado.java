import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.namespace.QName;
import javax.xml.xquery.*;
import net.xqj.exist.ExistXQDataSource;

public class MostrarEmpleado {
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			XQDataSource server = new ExistXQDataSource();
			server.setProperty("serverName", "192.168.95.102");
			server.setProperty("port", "8080");
			server.setProperty("user", "admin");
			server.setProperty("password", "austria");
			XQConnection conn = server.getConnection();
			XQPreparedExpression consulta;
			XQResultSequence resultado;
			consulta = conn.prepareExpression("declare variable $x as xs:string external;"
											+ "/universidad/departamento[@tipo=$x]/empleado");
			System.out.print("Introduce el tipo de departamento: ");
			String tipoDepartamento = br.readLine();
			consulta.bindString(new QName("x"), tipoDepartamento, null);
			resultado = consulta.executeQuery();
			if (!resultado.next()) {
				System.out.println("No existe ese tipo de departamento");
				System.exit(0);
			} else {
				do {
					System.out.println(resultado.getItemAsString(null));
				} while (resultado.next());
			}
			conn.close();
		} catch (XQException ex) {
			System.out.println("Error al operar" + ex.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

