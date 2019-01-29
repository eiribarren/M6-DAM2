import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import primero.*;

public class Main5 {

	public static void main(String[] args) {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();
		Emple emple = new Emple();
		Depart depart = new Depart();
		
		try {
			emple = session.load(Emple.class, 7369);
		} catch ( ObjectNotFoundException o ) {
			System.out.println("El empleado no existe");
		}
		
		try {
			depart = session.load(Depart.class, 30);
		} catch ( ObjectNotFoundException o ) {
			System.out.println("El departamento no existe");
		}
		
		emple.setSalario(emple.getSalario() + 1000);
		emple.setDepart(depart);
		session.update(emple);
		tx.commit();
		session.close();
	}

}
