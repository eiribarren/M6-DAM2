import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import primero.*;

public class Hibernate_V_1_4 {

	public static void main(String[] args) {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		String sql = "select avg(salario) from Emple";
		Query q = session.createQuery(sql);
		List<Double> salarioMedio = q.list();
		Iterator<Double> salarioMedioIterator = salarioMedio.iterator();
		Double salarioMedioValor;
		while (salarioMedioIterator.hasNext()) {
			salarioMedioValor = salarioMedioIterator.next();
			System.out.println("Salario medio: " + salarioMedioValor);
		}
		session.close();
	}

}
