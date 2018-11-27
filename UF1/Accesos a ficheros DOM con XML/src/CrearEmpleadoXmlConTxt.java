import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;  
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class CrearEmpleadoXmlConTxt {
	public static void main (String args[]) throws IOException {
		File fichero = new File ("empleados.txt");
		BufferedReader file = new BufferedReader(new FileReader(fichero));
		int posicion=0;
		String linea;
		String[] datos;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document document = implementation.createDocument (null,"Empleados", null);
			document.setXmlVersion("1.0");
			while ( ( linea = file.readLine() ) != null ){
				datos = linea.split(":");
				if (Integer.parseInt(datos[0])>0) {
					Element raiz = document.createElement ("empleado");
					document.getDocumentElement().appendChild(raiz);
					CrearElemento ("id", datos[0], raiz, document);
					CrearElemento ("apellido",datos[1], raiz, document);
					CrearElemento ("dep", datos[2], raiz, document);
					CrearElemento ("salario", datos[3],raiz, document);
				}
				
			}
			Source source = new DOMSource (document);
			Result result = new StreamResult (new java.io.File ("Empleados.xml"));
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform (source, result);
		} catch (Exception e ) { System.err.println ("Error: " + e);}
		file.close();
	}
	
	static void CrearElemento (String datoEmpleado, String valor, Element raiz, Document document) {
		Element elem = document.createElement (datoEmpleado);
		Text text = document.createTextNode(valor);
		raiz.appendChild (elem);
		elem.appendChild (text);
	}
}

