import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LecturaAlbumsXml {
	public static void main( String[] args ) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File ("albums.xml"));
			System.out.printf ("Elemento raíz : %s %n", document.getDocumentElement().getNodeName());
			NodeList albums = document.getElementsByTagName("album");
			System.out.printf ("Nodos album a recorrer: %d %n", albums.getLength());
			for (int i = 0; i < albums.getLength(); i++) {
				Node album = albums.item(i);
				if (album.getNodeType() == Node.ELEMENT_NODE){
					Element elemento = (Element) album;
					System.out.printf("Autor = %s %n", elemento.getElementsByTagName("autor").item(0).getTextContent());
					System.out.printf(" * Título = %s %n", 
					elemento.getElementsByTagName("titulo").item(0).getTextContent());
					System.out.printf(" * Formato = %s %n", 
					elemento.getElementsByTagName("formato").item(0).getTextContent());
				}
			}
	    } catch (Exception e) {e.printStackTrace();}
	}
}
