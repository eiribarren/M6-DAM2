
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvToXML {
	
	public static void main(String[] args ) throws IOException {
		File archivos = new File("entrada");
		for ( File archivo : archivos.listFiles() ) {
			escribirXML(archivo);
		}
	}
	
	public static void escribirXML(File entrada) throws IOException {
		System.out.println("Leyendo archivo "+entrada.getName());
		FileReader fr = new FileReader(entrada);
		PrintWriter pw = new PrintWriter(new FileWriter("salida/"+entrada.getName().substring(0, entrada.getName().indexOf('.'))+".xml"));
		List<String> etiquetas = getEtiquetas();
		int data;
		int numetiqueta = 0;
		int numelemento = 1;
		boolean empiezadato = true;
		pw.println("<doc>");
		pw.println("\t<elem nr=\"" + numelemento + "\">");
		
		while ( ( data = fr.read() ) != -1 ) {
			if ( (char)data == ',' ) {
				try {
					pw.println("</"+etiquetas.get(numetiqueta)+">");
				} catch ( IndexOutOfBoundsException e ) {
					pw.println("</altre>");
				}
				numetiqueta++;
				empiezadato = true;
			} else if ( (char)data == '\n' ) {
				try {
					pw.println("</"+etiquetas.get(numetiqueta)+">");
				} catch ( IndexOutOfBoundsException e ) {
					pw.println("</altre>");
				}
				empiezadato = true;
				pw.println("\t</elem>");
				numetiqueta = 0;
				numelemento++;
				pw.println("\t<elem nr=\"" + numelemento + "\">");
			} else if ( (char)data == '\r' ) {
				continue;
			}else {
				if ( empiezadato ) {
					try {
						pw.print("\t\t<"+etiquetas.get(numetiqueta)+">");
					} catch ( IndexOutOfBoundsException e ) {
						pw.print("\t\t<altre>");
					}
					empiezadato = false;
				}
				pw.print((char)data);
			}
		}
		try {
			pw.println("</"+etiquetas.get(numetiqueta)+">");
		} catch ( IndexOutOfBoundsException e ) {
			pw.println("</altre>");
		}
		pw.println("\t</elem>");
		pw.println("</doc>");
		pw.close();
		System.out.println("Archivo xml escrito");
	}
	
	public static ArrayList<String> getEtiquetas() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File("eti.txt")));
		String etiqueta;
		ArrayList<String> etiquetas = new ArrayList<String>();
		while ( ( etiqueta = br.readLine() ) != null ) {
			etiquetas.add(etiqueta);
		}
		return etiquetas;
	}
	
}
