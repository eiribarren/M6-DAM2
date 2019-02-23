package objetos;

public class Emple {
	public static final String __empNo__ = "empNo";
	public static final String __apellido__ = "Apellido";
	public static final String __oficio__ = "Oficio";
	public static final String __dir__ = "Director";
	public static final String __comision__ = "Comision";
	public static final String __dept__ = "Departamento";
	
	int empNo;
	String apellido;
	String oficio;
	Emple dir;
	java.sql.Date fechaAlt;
	float comision;
	Depart dept;
	
	public Emple() {}
	
	public Emple(int empNo, String apellido, String oficio, Emple dir, java.sql.Date fechaAlt, float comision, Depart dept) {
		this.empNo = empNo;
		this.apellido = apellido;
		this.oficio = oficio;
		this.dir = dir;
		this.fechaAlt = fechaAlt;
		this.comision = comision;
		this.dept = dept;
	}
	
	public int getEmpNo() {
		return empNo;
	}

	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getOficio() {
		return oficio;
	}

	public void setOficio(String oficio) {
		this.oficio = oficio;
	}

	public Emple getDir() {
		return dir;
	}

	public void setDir(Emple dir) {
		this.dir = dir;
	}

	public java.sql.Date getFechaAlt() {
		return fechaAlt;
	}

	public void setFechaAlt(java.sql.Date fechaAlt) {
		this.fechaAlt = fechaAlt;
	}

	public float getComision() {
		return comision;
	}

	public void setComision(float comision) {
		this.comision = comision;
	}

	public Depart getDept() {
		return dept;
	}

	public void setDept(Depart dept) {
		this.dept = dept;
	}
	
	public String toString() {
		return this.apellido;
	}
}
