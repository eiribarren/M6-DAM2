import java.io.*;
public class LeerFicheroAleatorio {
	public static void main(String[] args) throws IOException {
		File fichero = new File ("AleatorioEmpleado.dat");
		RandomAccessFile file = new RandomAccessFile (fichero, "r");
		int id, dep ,posicion;
		Double salario;
		char apellido[]= new char[10], aux;
		posicion =0;
		for ( ; ; ){
			file.seek(posicion); 
			id = file.readInt(); 
			// Obtengo identificar de Empleado
			for ( int i =0; i<apellido.length; i++) {
				aux = file.readChar(); 
				// Voy leyendo carácter a carácter el apellido y lo guardo
				apellido[i]=aux;     
			}
			String apellidos = new String (apellido);
			dep = file.readInt();              
			//Lectura de departamento y salario
			salario = file.readDouble();
			if (id >0)
			System.out.printf("ID: %s, Apellido: %s, Departamento: %d, Salario: %.2f %n", id,apellidos.trim(), dep, salario);
			posicion = posicion + 36;
			if (file.getFilePointer() ==file.length()) break; 
		}                                                                                    
		//del for
		file.close();
	}
}
