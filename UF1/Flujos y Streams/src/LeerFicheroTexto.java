
import java.io.*;
public class LeerFicheroTexto {
	public static void main ( String [] args) throws IOException {
		File fichero = new File ("src/LeerFicheroTexto.java");  // declaración fichero
		FileReader flu = new FileReader (fichero); // creamos flujo de entrada hacia el fichero
		int i;
		while ((i=flu.read())!=-1)    //Vamos leyendo carácter a carácter
			System.out.println ((char) i); //hacemos 
		flu.close();
	}
}