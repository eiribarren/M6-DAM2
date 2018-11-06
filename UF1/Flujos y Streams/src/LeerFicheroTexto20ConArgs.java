
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
public class LeerFicheroTexto20ConArgs {
	public static void main ( String [] args) throws IOException {
		File fichero = null;
		try {
			fichero = new File (args[0]);  // declaración fichero
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Introduce el nombre del fichero como argumento.");
			System.exit(-1);
		}
		FileReader flu = new FileReader (fichero); // creamos flujo de entrada hacia el fichero
		char[] buffer = new char[20];
		while (flu.read(buffer,0,20)!=-1) {    //Vamos leyendo carácter a carácter
			System.out.println (buffer); //hacemos 
		}
		flu.close();
	}
}