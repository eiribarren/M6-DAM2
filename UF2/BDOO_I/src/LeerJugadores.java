import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;

public class LeerJugadores {
	public static void main(String[] args) {
		
		ODB odb = ODBFactory.open("EQUIPOS.DB"); 
		Objects<Jugador> objects = odb.getObjects(Jugador.class);
		Objects<Pais> paises = odb.getObjects(Pais.class);
		System.out.printf("%d Jugadores: %n", objects.size());
		int i = 1;
		while(objects.hasNext()){  
			Jugador jug = objects.next();
			System.out.println(  i++ + ": " +
								jug.getNombre() + ", " +
								jug.getDeporte() + ", " +
								jug.getCiudad() + ", " +
								jug.getEdad() + ", " +
								jug.getPais());  
		}
		
		System.out.printf("%d Paises: %n", paises.size());
		while(paises.hasNext()) {
			Pais pais = paises.next();
			System.out.printf("%d: %s %n",
							  pais.getId(),
							  pais.getNombre());
		}
		odb.close(); 
	}

}
