import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
public class ContadorDeAlbums extends DefaultHandler{
	int numeroDeDiscos;
	public ContadorDeAlbums(){
		super();
		numeroDeDiscos = 0;
	}
	
	public void endDocument() {
		System.out.println("Hay " + this.numeroDeDiscos + " discos");
	}
	
	public void startElement (String uri, String nombre, String nombreC, Attributes atts) {
		if ( nombre.equals("TITLE")) {
			this.numeroDeDiscos++;
		}
	}
}
		