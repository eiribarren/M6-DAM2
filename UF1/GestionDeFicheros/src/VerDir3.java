import java.io.File;
public class VerDir3{
	public static void main (String[] args) {
		try {
			String dir = args[0];  // directorio actual
			File f = new File(dir);
			for( File fs : f.listFiles() ) {
				System.out.printf("Nombre: %s, es fichero?: %b, es directorio?:%b %n", fs.getName(), fs.isFile(), fs.isDirectory());
			}
		} catch ( IndexOutOfBoundsException e ) {
			System.out.print("Introduce la ruta del directorio como argumento");
		}
	}
}
