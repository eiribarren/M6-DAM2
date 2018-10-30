import java.io.File;
public class VerDir5{
	public static void main (String[] args) {
		try {
			String dir = args[0];  // directorio actual
			File f = new File(dir);
			System.out.println("Nombre: "+f.getName());
			System.out.println("Ruta relativa: " + f.getPath());
			System.out.println("Ruta absoluta: " + f.getAbsolutePath());
			System.out.println("Permisos: Lectura -> " + f.canRead() + " Escritura -> " + f.canWrite() + " Ejecución -> " + f.canExecute());
			System.out.println("Tamaño: " + f.length());
		} catch ( IndexOutOfBoundsException e ) {
			System.out.println("Introduce la ruta del directorio como argumento");
		} catch ( NullPointerException e ) {
			System.out.println("Error, el directorio no existe. Introduce un directorio válido.");
		}
	}
}