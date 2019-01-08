
public class Partida implements Tablero.Casilla.CasillaInterface {
	
	Ficha.Forma[] fichas = new Ficha.Forma[2];
	int turnoActual;
	
	public void start() {
		Tablero tablero = new Tablero(this);
		this.fichas[0] = Ficha.Forma.CIRCULO;
		this.fichas[1] = Ficha.Forma.CRUZ;
		this.turnoActual = 0;
	}

	@Override
	public Ficha getFichaJugadorActual() {
		return new Ficha(this.fichas[this.turnoActual]);
	}
	
	@Override
	public void cambiarTurno() {
		// TODO Auto-generated method stub
		if ( this.turnoActual == 0 ) {
			this.turnoActual = 1;
		} else {
			this.turnoActual = 0;
		}
	}

	public static void main(String[] args) {
		Partida partida = new Partida();
		partida.start();
	}
}
