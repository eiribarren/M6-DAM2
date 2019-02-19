import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.criteria.And;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

public class Problema_2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ODB odb = ODBFactory.open("EQUIPOS.DB");
		
		String nombreJugadorABuscar = null;
		
		ICriterion criterio = new And().add(Where.ge("edad", 14)).add(Where.le("edad", 20));
		CriteriaQuery query = new CriteriaQuery (Jugador.class, criterio);
		Objects<Jugador> objects = odb.getObjects(query);
		if (objects.size() > 0) {
			while (objects.hasNext()) {
				Jugador jugador = objects.next();
				System.out.println("Nombre: " + jugador.getNombre() + "\n" +
									   "Edad: " + jugador.getEdad() + "\n" +
									   "País: " + jugador.getPais() + "\n" +
									   "Ciudad: " + jugador.getCiudad() + "\n" +
									   "Deporte: " + jugador.getDeporte() + "\n");
			}
		} else {
			System.out.println("No se encontró ningún jugador");
		}
	}

}
