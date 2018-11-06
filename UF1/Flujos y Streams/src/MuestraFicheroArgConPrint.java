import java.io.*;

public class MuestraFicheroArgConPrint {
	public static void main ( String[] args ) throws IOException {
		File archivo = new File("FicheroTexto.txt");
		PrintWriter printw = new PrintWriter(new FileWriter(archivo));
		
		String[] filas = {"Primera fila", "Segunda fila", "Tercera fila", "Cuarta fila",
				"Quinta fila", "Sexta fila", "Séptima fila", "Octava fila", "Novena fila",
				"Decima fila"};
		
		for ( String fila : filas ) {
			printw.println(fila);
		}
		
		printw.close();
	}
}
