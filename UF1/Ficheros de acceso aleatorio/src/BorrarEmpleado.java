import java.io.*;

public class BorrarEmpleado {

	public static void main(String[] args) throws IOException {
		int id, id_busqueda, posicion;
		StringBuffer buffer = null;
		RandomAccessFile f;
		id_busqueda = 0;
		try {
			id_busqueda = Integer.parseInt(args[0]);
			buffer = new StringBuffer(args[0]);
			buffer.setLength(10);
		} catch ( IndexOutOfBoundsException e ) {
			System.out.println("Introduce el id como argumento");
			System.exit(-1);
		} catch ( NumberFormatException e ) {
			System.out.println("Argumentos no válidos");
			System.out.println("java ConsultaEmpleado id");
			System.exit(-1);
		}
		
		f = new RandomAccessFile(new File("AleatorioEmpleado.dat"),"rw");
		
		posicion = 0;
		while ( true ) {
			try {
				f.seek(posicion);
				id = f.readInt();
				if ( id == id_busqueda ) {
					String apellidos = new String (buffer);
					long puntero = f.getFilePointer();
					f.seek(puntero-4);
					f.writeInt(-1);
					f.writeChars(apellidos);
					f.writeInt(0);
					f.writeDouble(0d);
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
