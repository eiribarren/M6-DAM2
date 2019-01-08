import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tablero {
	
	JFrame main;
	Casilla[][] casillas; 
	
	
	public Tablero(Casilla.CasillaInterface listener) {
		this.start(listener);
	}
	
	public void start(Casilla.CasillaInterface listener) {
		main = new JFrame();
		main.setSize(new Dimension(200*3,200*3));
		casillas = new Casilla[3][3];
		for ( int i = 0 ; i < 3 ; i++ ) {
			for ( int j = 0 ; i < 3 ; i++ ) {
				casillas[i][j] = new Casilla(listener, i , j);
				main.add(casillas[i][j]);
			}
		}
		main.setVisible(true);
		main.pack();
	}
	
	public static class Casilla extends JPanel {
		
		private CasillaInterface listener;
		private Ficha ficha = null;
		private int fila, columna;
		
		public Casilla(CasillaInterface listener, int fila, int columna) {
			this.listener = listener;
			this.fila = fila;
			this.columna = columna;
			this.start();
		}
		
		public void start() {
			this.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if ( ponerFicha( listener.getFichaJugadorActual() ) ) {
						listener.cambiarTurno();
					};
				}
			});
			this.setSize(new Dimension(200,200));
			this.setVisible(true);
		}
		
		public boolean ponerFicha( Ficha ficha ) {
			if ( this.ficha == null ) {
				this.ficha = ficha;
				String urlImagen = "";
				switch ( ficha.getForma() ) {
					case CRUZ:
						urlImagen = "cruz.png";
						break;
					case CIRCULO:
						urlImagen = "patata.png";
						break;
				}
				ficha.ponerImagen(200,200,urlImagen);
				this.add(ficha);
				return true;
			} else {
				return false;
			}
		}
		
		public Ficha getFicha() {
			return this.ficha;
		}
		
		public interface CasillaInterface {
			abstract Ficha getFichaJugadorActual();
			abstract void cambiarTurno();
		}
	}
}
