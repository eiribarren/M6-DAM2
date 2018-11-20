import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class AumentoSalario {
	public static void main (String[] args) throws IOException {
		int id, id_busqueda, posicion;
		Double salario;
		Double aumento = 0d;
		RandomAccessFile f;
		char apellido[]= new char[10];
		id_busqueda = 0;
		try {
			id_busqueda = Integer.parseInt(args[0]);
			aumento = Double.parseDouble(args[1]);
		} catch ( IndexOutOfBoundsException e ) {
			System.out.println("Introduce el id y el aumento de salario como argumento");
			System.exit(-1);
		} catch ( NumberFormatException e ) {
			System.out.println("Argumentos no válidos");
			System.out.println("java AumentoSalario id aumento");
			System.exit(-1);
		}
		
		f = new RandomAccessFile(new File("AleatorioEmpleado.dat"),"rw");
		
		posicion = 0;
		while ( true ) {
			try {
				f.seek(posicion);
				id = f.readInt();
				if ( id == id_busqueda ) {
					long puntero = f.getFilePointer();
					for ( int i = 0 ; i < apellido.length ; i++ ) {
						apellido[i] = f.readChar();
					}
					f.skipBytes(4);
					salario = f.readDouble();
					f.seek(puntero);
					f.skipBytes(24);
					f.writeDouble(salario + aumento);
					String apellidos = new String(apellido);
					System.out.print("Apellido: "+apellidos.trim()+", Salario: "+salario+", Salario nuevo: "+(salario+aumento));
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

