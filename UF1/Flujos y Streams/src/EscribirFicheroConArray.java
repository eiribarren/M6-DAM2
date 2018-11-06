import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EscribirFicheroConArray {
		public static void main (String [] args) throws IOException {
			File fichero = new File("FicheroTexto.txt");
			FileWriter fic = new FileWriter (fichero);
			String provincias[] = {"Albacete", "Avila", "Badajoz", "Caceres", "Huelva", "Jaen", 
					"Madrid", "Segovia", "Soria", "Toledo", "Valladolid", "Zamora"};
			for( String provincia : provincias ) {
				fic.write(provincia+'\r'+'\n');
			}
			fic.close ();   // cerramos fichero
		}
}