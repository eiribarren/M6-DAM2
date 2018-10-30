import java.io.File;
public class VerDir2{
	public static void main (String[] args) {
		try {
			String dir = args[0];  // directorio actual
			File f = new File(dir);
			String[] archivos = f.list();
			System.out.printf("Ficheros en el directorio actual: %d %n", archivos.length);
			for (int i=0; i<archivos.length; i++){
				File f2 = new File(f, archivos[i]);
				System.out.printf("Nombre: %s, es fichero?: %b, es directorio?:%b %n", archivos[i], f2.isFile(), f2.isDirectory());
			}
		} catch ( IndexOutOfBoundsException e ) {
			System.out.print("Introduce la ruta del directorio como argumento");
		}
	}
}
