import java.io.*;

public class EscribirNombresYEdades {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String[] nombres = {"Epumer","Damn Daniel","Elvio","Jan"};
		int[] edades = {12,19,22,44};
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File("FicherosDatos.dat")));
		for ( int i = 0 ; i < nombres.length; i++ ) {
			dos.writeUTF(nombres[i]);
			dos.writeInt(edades[i]);
		}
		dos.close();
	}

}
