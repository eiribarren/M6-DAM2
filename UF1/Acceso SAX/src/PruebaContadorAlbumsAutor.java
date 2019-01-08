import java.io.*;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
public class PruebaContadorAlbumsAutor {
	public static void main (String [] args)
	throws FileNotFoundException, IOException, SAXException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		/* A continuaci�n se crea objeto procesador XML - 
		XMLReader
		 -. Durante la creaci�n de este objeto se puede producir una 
		excepci�n SAXException. */
		XMLReader procesadorXML = XMLReaderFactory.createXMLReader();
		System.out.print("Introduce el nombre del autor: ");
		String autor = br.readLine();
		/* A continuaci�n, mediante setContentHandler
		 establecemos que la clase que gestiona los eventos provocados por la 
		lectura del XML ser� GestionContenido */
		ContadorDeAlbumsDeAutor contador = new ContadorDeAlbumsDeAutor(autor);
		procesadorXML.setContentHandler(contador);
		/* Por �ltimo, se define el fichero que se va leer mediante 
		InputSource y se procesa el documento XML mediante el m�todo parse() de 
		XMLReader */
		InputSource fileXML = new InputSource ("discoteca.xml");
		procesadorXML.parse(fileXML);
	}
}
		