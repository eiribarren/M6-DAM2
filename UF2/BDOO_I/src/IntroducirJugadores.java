import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;

public class IntroducirJugadores {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Pais p1 = new Pais(1, "España");
		Pais p2 = new Pais(2, "Francia");
		Pais p3 = new Pais(3, "El digimundo");
		Jugador j1 = new Jugador("The Best", "comer patatas", "Madrid", 32, p1);
		Jugador j2 = new Jugador("Miguel", "tenis", "Madrid", 29, p1);
		Jugador j3 = new Jugador("Mario", "baloncesto", "Guadalajara", 15, p2);
		Jugador j4 = new Jugador("Alicia", "tenis", "Madrid", 14, p3);
		ODB odb = ODBFactory.open("EQUIPOS.DB");     
		odb.store(p1);
		odb.store(p2);
		odb.store(p3);
		odb.store(j1);                                      
		odb.store(j2);
		odb.store(j3);
		odb.store(j4);
		odb.close();
	}
}
