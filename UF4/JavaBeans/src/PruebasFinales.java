
public class PruebasFinales {

	public static void main(String[] args) {
		Empleado empleado = new Empleado("Y9384892F", "Stependrive");
		PanelEmpleado panelEmpleado = new PanelEmpleado();
		
		empleado.addVetoableChangeListener(panelEmpleado);
		
		//No lanza error
		System.out.println("Probando con cargo CEO");
		empleado.setCargo("CEO");
		
		//Lanza error
		System.out.println("Probando con sueldo 899");
		empleado.setSueldo(899);
		
		//No lanza error
		System.out.println("Probando con sueldo 900");
		empleado.setSueldo(900);
		
		//Lanza error
		System.out.println("Probando con cargo Empleado");
		empleado.setCargo("Empleado");
		
		//No lanza error
		System.out.println("Probando con sueldo 1100");
		empleado.setSueldo(1100);
		
		//Lanza error
		System.out.println("Probando con sueldo 1101");
		empleado.setSueldo(1101);
	}

}
