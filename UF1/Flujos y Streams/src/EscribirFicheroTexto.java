import java.io.*;

public class EscribirFicheroTexto {
		public static void main (String [] args) throws IOException {
			File fichero = new File("FicheroTexto.txt");
			FileWriter fic = new FileWriter (fichero);
			String cadena = "Esto es una prueba con FileWriter";
			char [] cad = cadena.toCharArray () ;
			for ( int i=0; i < cad.length ; i++)
			fic.write (cad[i]); // se va escribiendo car�cter a car�cter
			fic.append ('*'); // a�adimos un asterisco al final
			fic.close ();   // cerramos fichero
		}
}