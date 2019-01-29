import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import primero.*;

public class Main2 {

	public static void main(String[] args) {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();
		System.out.println("Inserto una fila en emple");
		Depart dep = new Depart();
		dep.setDeptNo(10);
		Emple emp = new Emple();
		emp.setEmpNo(7289);
		emp.setComision(2);
		emp.setApellido("Stallman");
		emp.setDepart(dep);
		emp.setFechaAlt(new Date());
		emp.setOficio("Dev");
		emp.setSalario(20);
		emp.setDir(7698);
		session.save(emp);
		tx.commit();
		session.close();
		System.exit(0);
		//Creamos un nuevo objeto Depart y damos valor a sus atributos
		
	}

}
