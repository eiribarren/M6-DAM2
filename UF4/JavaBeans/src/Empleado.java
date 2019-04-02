import java.beans.*;
import java.io.Serializable;

public class Empleado implements Serializable {
	private String nif, nombre, cargo;
	private int sueldo;
	private VetoableChangeSupport vetoableSupport;
	
	public Empleado() {
		vetoableSupport = new VetoableChangeSupport (this);
		this.cargo = "Junior";
		this.sueldo = 1000;
	}
	
	public Empleado(String nif, String nombre) {
		this();
		this.nif = nif;
		this.nombre = nombre;
	}
	
	public void addVetoableChangeListener (VetoableChangeListener listener) {
		vetoableSupport.addVetoableChangeListener(listener);
	}
	
	public void removeVetoableChangeListener (VetoableChangeListener listener) {
		vetoableSupport.removeVetoableChangeListener(listener);
	}
	
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		if (cargo != null && !cargo.equals("")) {
			try {
				vetoableSupport.fireVetoableChange("cargo", this.cargo, cargo);
				this.cargo = cargo;
			} catch (PropertyVetoException e) {
				System.out.println("hey");
				e.printStackTrace();
			}
		}
	}
	public int getSueldo() {
		return sueldo;
	}
	public void setSueldo(int sueldo) {
		if (sueldo > 0) {
			try {
				vetoableSupport.fireVetoableChange("sueldo", this.sueldo, sueldo);
				this.sueldo = sueldo;
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
		}
	}
	
}
