import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ContadorDeAlbumsDeAutor extends DefaultHandler {
	int numeroDeDiscos;
	String autor;
	boolean artistaEncontrado;
	public ContadorDeAlbumsDeAutor(String autor){
		super();
		this.numeroDeDiscos = 0;
		this.autor = autor;
		this.artistaEncontrado = false;
	}
	
	public void endDocument() {
		if ( this.numeroDeDiscos > 0 ) {
			System.out.println("Hay " + this.numeroDeDiscos + " discos del artista " + this.autor);
		} else {
			System.out.println("El autor <" + this.autor + "> no aparece en el archivo.");
		}
	}
	
	public void startElement (String uri, String nombre, String nombreC, Attributes atts) {
		if ( nombre.equals("ARTIST")) {
			this.artistaEncontrado = true;
		}
	}
	
	public void characters(char[] ch, int inicio, int longitud) throws SAXException {
		if ( this.artistaEncontrado ) {
			String texto = new String(ch, inicio, longitud);
			if ( texto.equals(this.autor) ) {
				this.numeroDeDiscos++;
			}
			this.artistaEncontrado = false;
		}
	}
}
