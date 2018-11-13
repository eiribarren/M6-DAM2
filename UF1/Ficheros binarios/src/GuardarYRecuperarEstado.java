import java.io.*;

public class GuardarYRecuperarEstado {
	public static void guardarEstado( String nombrePartida, EstadoPartida partida ) throws FileNotFoundException, IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(nombrePartida+".save")));
		oos.writeObject(partida);
		oos.close();
	}
	
	public static EstadoPartida recuperarEstado(String nombrePartida) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(nombrePartida+".save")));
		return (EstadoPartida) ois.readObject();
	}
}
