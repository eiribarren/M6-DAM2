import java.io.*;

public class EstadoPartida implements Serializable {
	int jugadorActual;
	int[][] casillas;
	
	public EstadoPartida( int jugadorActual, int[][] casillas ) {
		this.jugadorActual = jugadorActual;
		this.casillas = casillas;
	}
	
	public int[][] getCasillas() {
		return this.casillas;
	}
	
	public int getJugadorActual() {
		return this.jugadorActual;
	}
	
	public void setJugadorActual( int jugadorActual ) {
		this.jugadorActual = jugadorActual;
	}
	
	public boolean ponerFicha(int fila, int columna) {
		if ( this.casillas[fila][columna] == 0 ) {
			this.casillas[fila][columna] = this.jugadorActual;
			return true;
		}
		return false;
	}
	
	public String toString() {
		String partida = "";
		for ( int[] filas : this.casillas ) {
			partida += "+-+-+-+\n";
			for ( int casilla : filas ) {
				if ( casilla == 0 ) {
					partida += "| ";
				} else if ( casilla == 1 ) {
					partida += "|X";
				} else if ( casilla == 2 ) {
					partida += "|O";
				}
			}
			partida += "|\n";
		}
		partida += "+-+-+-+";
		return partida;
	}
}
