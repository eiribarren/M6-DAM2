import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;

public class Jugador {
	private Pais pais;

	private String nombre;
	private String deporte;
	private String ciudad;
	private int edad;

	public Jugador() {
	}

	public Jugador(String nombre, String deporte, String ciudad, int edad, Pais pais) {
		this.nombre = nombre;
		this.deporte = deporte;
		this.ciudad = ciudad;
		this.edad = edad;
		this.pais = pais;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setDeporte(String deporte) {
		this.deporte = deporte;
	}

	public String getDeporte() {
		return deporte;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public int getEdad() {
		return edad;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}
}
