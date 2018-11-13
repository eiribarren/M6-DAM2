import java.io.FileNotFoundException;
import java.io.IOException;

public class UsaEstadoPartida {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		EstadoPartida ep;
		try {
			ep = GuardarYRecuperarEstado.recuperarEstado("EstadoPartida");
			System.out.println(ep);
		} catch ( Exception FileNotFoundException ) {
			ep = new EstadoPartida(1, new int[3][3]);
			System.out.println(ep);
		}
		
		ep.ponerFicha(1, 1);
		ep.setJugadorActual(2);
		ep.ponerFicha(0, 2);
		ep.setJugadorActual(1);
		ep.ponerFicha(2, 2);
		ep.setJugadorActual(2);
		ep.ponerFicha(0, 0);
		
		GuardarYRecuperarEstado.guardarEstado("EstadoPartida", ep);
		System.out.println(ep);
	}

}
