
import java.io.*;
public class LeerFicheroTextoDe20En20 {
	public static void main ( String [] args) throws IOException {
		File fichero = new File ("src/LeerFicheroTexto.java");  // declaraci�n fichero
		FileReader flu = new FileReader (fichero); // creamos flujo de entrada hacia el fichero
		char[] buffer = new char[20];
		while (flu.read(buffer,0,20)!=-1) {    //Vamos leyendo car�cter a car�cter
			System.out.println (buffer); //hacemos 
		}
		flu.close();
	}
}