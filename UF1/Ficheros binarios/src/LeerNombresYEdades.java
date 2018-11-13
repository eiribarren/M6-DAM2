import java.io.*;

public class LeerNombresYEdades {
	public static void main(String[] args) throws IOException {
		DataInputStream dis = new DataInputStream(new FileInputStream(new File("FicherosDatos.dat")));
		
		try {
			while ( true ) {
				System.out.println("Nombre: "+dis.readUTF()+" Edad: "+dis.readInt());
			}
		} catch ( EOFException e ) {
			System.out.println("Se terminaron de leer los datos");
		}
	}
}
