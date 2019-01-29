import java.util.Set;
import java.util.Iterator;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import primero.*;

public class Main4 {
	public static void main (String[] args)
	{
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		Depart dep = new Depart();
		try {
			dep = session.load(Depart.class, 10);
		} catch ( ObjectNotFoundException o ) {
			System.out.println("El departamento no existe");
		}
		Set<Emple> empleados = dep.getEmples();
		Iterator<Emple> it = empleados.iterator();
		Emple emple;
		while (it.hasNext()) {
			emple = it.next();
			System.out.println("Apellido: " + emple.getApellido() + " Salario: " + emple.getSalario());
		}
		session.close();
	}
}
