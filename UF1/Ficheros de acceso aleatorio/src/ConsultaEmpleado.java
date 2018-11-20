import java.io.*;

public class ConsultaEmpleado {
	public static void main (String[] args) throws IOException {
		int id, id_busqueda, dep ,posicion;
		Double salario;
		char apellido[]= new char[10], aux;
		RandomAccessFile f;
		id_busqueda = 0;
		try {
			id_busqueda = Integer.parseInt(args[0]);
		} catch ( IndexOutOfBoundsException e ) {
			System.out.println("Introduce el id como argumento");
			System.exit(-1);
		} catch ( NumberFormatException e ) {
			System.out.println("Argumentos no válidos");
			System.out.println("java ConsultaEmpleado id");
			System.exit(-1);
		}
		
		f = new RandomAccessFile(new File("AleatorioEmpleado.dat"),"r");
		
		posicion = 0;
		while ( true ) {
			try {
				f.seek(posicion);
				id = f.readInt();
				if ( id == id_busqueda ) {
					for ( int i = 0 ; i < apellido.length ; i++ ) {
						apellido[i] = f.readChar();
					}
					String apellidos = new String (apellido);
					dep = f.readInt();              
					salario = f.readDouble();
					System.out.printf("ID: %s, Apellido: %s, Departamento: %d, Salario: %.2f %n", id,apellidos.trim(), dep, salario);
					break;
				}
				posicion += 36;
			} catch ( EOFException e ) {
				System.out.println("No se encontraron datos con ese ID");
				break;
			}
		}
		f.close();
	}
}
