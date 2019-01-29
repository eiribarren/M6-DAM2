import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import primero.*;

public class Main3 {
	public static void main (String[] args)
	{
		// TODO Auto-generated method stub
		//En primer lugar se obtiene la sesión creada por el Singleton.
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		Emple emple = new Emple();
		try {
			emple = (Emple) session.load(Emple.class, 7369);
		} catch (ObjectNotFoundException o) {
			System.out.println("No existe el empleado");
		}
		System.out.println("Apellido: " + emple.getApellido() + " Salario: " + emple.getSalario());
		session.close();
	}
}
