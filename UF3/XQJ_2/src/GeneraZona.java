import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.namespace.QName;
import javax.xml.xquery.*;
import net.xqj.exist.ExistXQDataSource;

public class GeneraZona {
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("No se introdujo la zona como argumento");
			System.exit(-1);
		}
		int zona = Integer.parseInt(args[0]);
		try {
			XQDataSource server = new ExistXQDataSource();
			server.setProperty("serverName", "192.168.95.102");
			server.setProperty("port", "8080");
			server.setProperty("user", "admin");
			server.setProperty("password", "austria");
			XQConnection conn = server.getConnection();
			XQPreparedExpression consulta;
			XQResultSequence resultado;
			consulta = conn.prepareExpression("declare variable $x as xs:int external; " +
											  "for $prod in doc('nueva/productos.xml')/productos/produc[cod_zona=$x] " + 
											  "let $zona := /zonas/zona[cod_zona=$x] " + 
											  "return " + 
											  "    <produc> " + 
											  "        <cod_prod>{data($prod/cod_prod)}</cod_prod> " + 
											  "        <denominacion>{data($prod/denominacion)}</denominacion> " + 
											  "        <precio>{data($prod/precio)}</precio> " + 
											  "        <nombre_zona>{data($zona/nombre)}</nombre_zona> " + 
											  "        <director>{data($zona/director)}</director> " + 
											  "        <stock>{data($prod/stock_actual)-data($prod/stock_minimo)}</stock> " + 
											  "    </produc>");
			consulta.bindInt(new QName("x"), zona, null);
			resultado = consulta.executeQuery();
			FileWriter fw = new FileWriter(new File("zona" + String.valueOf(args[0]) + ".xml"));
			fw.write("<zona id=\"" + zona + "\">\n");
			while (resultado.next()) {
				fw.write("\t" + resultado.getItemAsString(null) + "\n");
			}
			fw.write("</zona>");
			fw.close();
			conn.close();
		} catch (XQException ex) {
			System.out.println("Error al operar" + ex.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

