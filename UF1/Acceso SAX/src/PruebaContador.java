import java.io.*;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
public class PruebaContador {
	public static void main (String [] args)
	throws FileNotFoundException, IOException, SAXException {
		/* A continuación se crea objeto procesador XML - 
		XMLReader
		 -. Durante la creación de este objeto se puede producir una 
		excepción SAXException. */
		XMLReader procesadorXML = XMLReaderFactory.createXMLReader();
		/* A continuación, mediante setContentHandler
		 establecemos que la clase que gestiona los eventos provocados por la 
		lectura del XML será GestionContenido */
		ContadorDeAlbums contador = new ContadorDeAlbums();
		procesadorXML.setContentHandler(contador);
		/* Por último, se define el fichero que se va leer mediante 
		InputSource y se procesa el documento XML mediante el método parse() de 
		XMLReader */
		InputSource fileXML = new InputSource ("discoteca.xml");
		procesadorXML.parse(fileXML);
	}
}
		