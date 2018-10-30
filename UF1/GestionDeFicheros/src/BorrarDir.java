import java.io.File;
public class BorrarDir{
	public static void main (String[] args) {
		try {
			String dir = "NuevoDir";  // directorio actual
			File f = new File(dir);
			for( File fs : f.listFiles() ) {
				fs.delete();
			}
			f.delete();
		} catch ( IndexOutOfBoundsException e ) {
			System.out.println("Introduce la ruta del directorio como argumento");
		} catch ( NullPointerException e ) {
			System.out.println("Error, el directorio no existe. Introduce un directorio válido.");
		}
	}
}