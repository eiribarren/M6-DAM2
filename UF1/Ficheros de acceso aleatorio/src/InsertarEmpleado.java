import java.io.*;

public class InsertarEmpleado {
	public static void main (String[] args) throws IOException {
		int id = 0, dep = 0, posicion = 0;
		Double salario = 0d;
		String apellido = "";
		StringBuffer buffer;
		try {
			id = Integer.parseInt(args[0]);
			buffer = new StringBuffer(args[1]);
			dep = Integer.parseInt(args[2]);
			salario = Double.parseDouble(args[3]);
			buffer.setLength(10);
			apellido = buffer.toString();
		} catch ( IndexOutOfBoundsException e ) {
			System.out.println("Faltan argumentos");
			System.out.println("java InsertarEmpleado id apellido departamento salario");
			System.exit(-1);
		} catch ( NumberFormatException e) {
			System.out.println("Argumentos no válidos");
			System.out.println("java InsertarEmpleado id apellido departamento salario");
			System.exit(-1);
		}
		File fichero = new File ("AleatorioEmpleado.dat");
		RandomAccessFile file = new RandomAccessFile (fichero , "rw");
		while ( true ) {
			try {
				file.seek(posicion);
				int id_f = file.readInt();
				if ( id_f == id ) {
					System.out.printf("El id ya existe");
					System.exit(-1);
				}
				posicion += 36;
			} catch ( EOFException e ) {
				break;
			}
		}
		file.seek(file.length());
		file.writeInt(id);
		file.writeChars(apellido);
		file.writeInt(dep);
		file.writeDouble(salario);
		file.close();
	}
}
