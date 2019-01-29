package primero;
// Generated 22-ene-2019 18:43:46 by Hibernate Tools 5.1.10.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Depart generated by hbm2java
 */
public class Depart implements java.io.Serializable {

	private int deptNo;
	private String dnombre;
	private String loc;
	private Set<Emple> emples = new HashSet<Emple>(0);

	public Depart() {
	}

	public Depart(int deptNo) {
		this.deptNo = deptNo;
	}

	public Depart(int deptNo, String dnombre, String loc, Set<Emple> emples) {
		this.deptNo = deptNo;
		this.dnombre = dnombre;
		this.loc = loc;
		this.emples = emples;
	}

	public int getDeptNo() {
		return this.deptNo;
	}

	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}

	public String getDnombre() {
		return this.dnombre;
	}

	public void setDnombre(String dnombre) {
		this.dnombre = dnombre;
	}

	public String getLoc() {
		return this.loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public Set<Emple> getEmples() {
		return this.emples;
	}

	public void setEmples(Set<Emple> emples) {
		this.emples = emples;
	}

}
