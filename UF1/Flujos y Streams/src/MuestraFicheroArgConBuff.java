import java.io.*;

public class MuestraFicheroArgConBuff {
	public static void main(String[] args) throws IOException {
		File archivo = null;
		BufferedReader buff = null;
		
		try {
			archivo = new File(args[0]);
		} catch ( IndexOutOfBoundsException e ) {
			System.out.println("Introduce el fichero como argumento.");
			System.exit(-1);
		}
		
		try {
			buff = new BufferedReader(new FileReader(archivo));
		} catch ( FileNotFoundException e ) {
			System.out.println("No se encontró el fichero.");
			System.exit(-1);
		}
		
		String linea;
		while ( ( linea = buff.readLine() ) != null ) {
			System.out.println(linea);
		}
		
	}
}
