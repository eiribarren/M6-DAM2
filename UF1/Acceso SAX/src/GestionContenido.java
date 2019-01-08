import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* GestionContenido es la clase que implementa los métodos necesarios para crear nuestro parser de XML. Es decir, 
definimos los métodos que serán llamados al provocarse los eventos comentados anteriormente: startDocument, 
startElement, characters, etc. Si quisieramos tratar más eventos definiríamos el método asociado en esta clase. */
class GestionContenido extends DefaultHandler {
	public GestionContenido(){
		super();
	}
	public void startDocument(){
		System.out.println("Comienzo del documento XML");
	}
	public void endDocument(){
		System.out.println("Final del documento XML");
	}
	public void startElement (String uri, String nombre, String nombreC, Attributes atts) {
		System.out.printf("\tPrincipio Elemento: %s %n", nombre);
	}
	public void endElement (String uri, String nombre, String nombreC){
		System.out.printf("\tFin Elemento: %s %n",nombre);
	}
	public void characters(char[] ch, int inicio, int longitud) throws SAXException {
		String car = new String (ch, inicio, longitud);
		car = car.replaceAll("[\t\n]","");
		System.out.printf("\tCaracteres: %s %n", car);
	}
}
