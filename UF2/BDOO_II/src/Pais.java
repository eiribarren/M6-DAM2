import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;

public class Pais {
	private int id;
	private String nombre;
	
	public Pais() {}
	
	public Pais(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	public int getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	
	public String toString() {
		return nombre;
	}
	
}
