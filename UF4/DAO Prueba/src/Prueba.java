
public class Prueba {
	public static void main (String[] args) {
		DepartamentoDAO depDAO = new DepartamentoImpl();
		
		Departamento dept = new Departamento(1, "Dept molón", "Barcelona");
		depDAO.insertarDep(dept);
		
		System.out.println("Obteniendo departamento");
		Departamento consulta = depDAO.consultarDep(dept.getDeptno());
		
		System.out.println(imprimirDepartamento(consulta));
		
		dept.setDnombre("Dept menos molón");
		
		depDAO.modificarDep(dept.getDeptno(), dept);
		
		System.out.println("Modificando departamento");
		consulta = depDAO.consultarDep(dept.getDeptno());
		System.out.println(imprimirDepartamento(consulta));
		
		System.out.println("Eliminando departamento");
		depDAO.eliminarDep(dept.getDeptno());
	}
	
	public static String imprimirDepartamento(Departamento dep) {
		return "\n+------------------------------------------+" +
			   "\n|                 DEPART                   |" + 
			   "\n+------------------------------------------+" +
			   "\n DeptNo: " + dep.getDeptno() + 
			   "\n Nombre: " + dep.getDnombre() + 
			   "\n Loc: " + dep.getLoc() +
			   "\n============================================\n";
	}
}