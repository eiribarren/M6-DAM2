import java.io.File;
import javax.xml.parsers.*;
import org.w3c.dom.*;
public class LecturaEmpleadoXml {
	public static void main (String[] args) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File ("Empleados.xml"));
			System.out.printf ("Elemento raíz : %s %n", document.getDocumentElement().getNodeName());
			NodeList empleados = document.getElementsByTagName("empleado");
			System.out.printf ("Nodos empleado a recorrer: %d %n", empleados.getLength());
			for (int i = 0; i < empleados.getLength(); i++) {
				Node emple = empleados.item(i);
				if (emple.getNodeType() == Node.ELEMENT_NODE){
					Element elemento = (Element) emple;
					System.out.printf("ID = %s %n", elemento.getElementsByTagName("id").item(0).getTextContent());
					System.out.printf(" * Apellido = %s %n", 
					elemento.getElementsByTagName("apellido").item(0).getTextContent());
					System.out.printf(" * Departamento = %s %n", 
					elemento.getElementsByTagName("dep").item(0).getTextContent());
					System.out.printf(" * Salario = %s %n",
					elemento.getElementsByTagName("salario").item(0).getTextContent());
				}
			}
	    } catch (Exception e) {e.printStackTrace();}
	}
}
