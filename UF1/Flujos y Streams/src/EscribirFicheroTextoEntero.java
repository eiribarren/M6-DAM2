import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EscribirFicheroTextoEntero {
		public static void main (String [] args) throws IOException {
			File fichero = new File("FicheroTexto.txt");
			FileWriter fic = new FileWriter (fichero);
			String cadena = "Esto es una prueba con FileWriter Entero";
			char [] cad = cadena.toCharArray () ;
			fic.write (cad,0,cad.length); // se va escribiendo carácter a carácter
			fic.append ('*'); // añadimos un asterisco al final
			fic.close ();   // cerramos fichero
		}
}