import java.io.*;

public class EscribirFicheroBufferedWriter {
	public static void main ( String[] args ) throws IOException {
		File archivo = new File("FicheroTexto.txt");
		BufferedWriter buffw = new BufferedWriter(new FileWriter(archivo));
		
		String[] filas = {"Primera fila", "Segunda fila", "Tercera fila", "Cuarta fila",
				"Quinta fila", "Sexta fila", "Séptima fila", "Octava fila", "Novena fila",
				"Decima fila"};
		
		for ( String fila : filas ) {
			buffw.write(fila);
			buffw.newLine();
		}
		
		buffw.close();
	}
}
