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

public class Problema_3 {
	public static void main(String[] args) {
		ODB odb = ODBFactory.open("EQUIPOS.DB");
		
		Objects<Jugador> objects = odb.getObjects(Jugador.class);
		
		while(objects.hasNext()){  
			Jugador jug = objects.next();
			jug.setEdad(jug.getEdad() + 1);
			odb.store(jug);
		}
		
		odb.commit();
		odb.close(); 
		
		
	}
}
