package objetos;

/* +===================================================================+ *
 * |      PRIMERA PARTE: MONTAR LA ESTRUCTURA DE LA BASE DE DATOS      | * 
 * +===================================================================+ */
public class Depart {
	public static final String __deptNo__ = "deptNo";
	public static final String __dnombre__ = "Nombre";
	public static final String __loc__ = "Localizacion";
	
	int deptNo;
	String dnombre;
	String loc;
	
	public Depart() {}
	
	public Depart(int deptNo, String dnombre, String loc) {
		this.deptNo = deptNo;
		this.dnombre = dnombre;
		this.loc = loc;
	}

	public int getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}

	public String getDnombre() {
		return dnombre;
	}

	public void setDnombre(String dnombre) {
		this.dnombre = dnombre;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}
	
	public String toString() {
		return this.dnombre;
	}
}
