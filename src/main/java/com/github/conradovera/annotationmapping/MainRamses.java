package com.github.conradovera.annotationmapping;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class MainRamses {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernateAnotationsRamses.cfg.xml") // configures
																															// settings
																															// from
																															// hibernate.cfg.xml
				.build();

		SessionFactory sessionFactory = null;

		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had
			// trouble building the SessionFactory
			// so destroy it manually.
			e.printStackTrace();
			StandardServiceRegistryBuilder.destroy(registry);
		}

		Session session = sessionFactory.openSession();

		//Rescato la transaccion ya creada previamente por JDBC
		Transaction transaccion = session.getTransaction();

		//Empiezo mi proceso transaccional
		transaccion.begin();
		session.save(new EventRamses("Our very first event!", new Date(), 1L));
		session.save(new EventRamses("A follow up event", new Date(), 2L));
		session.save(new LugarRamses("Vamos al cine", "Plaza Galerias Campeche"));
		session.save(new LugarRamses("Vamo a comer", "Boston"));
		//Aplicar cambios en BD
		transaccion.commit();
		
		List<EventRamses> result = session.createQuery("from EventRamses").list();
		for (EventRamses event : result) {
			System.out.println("Event (" + event.getDate() + ") : " + event.getTitle());
		}

		System.out.println("Pido los lugares por primera vez");
		List<LugarRamses> result2 = session.createQuery("from LugarRamses").list();
		for (LugarRamses event2 : result2) {
			System.out.println("Hoy " + event2.getNombre() + " pero en" + event2.getCiudad());
		}
		
		//Asi pido el registro entero con datos
		//LugarRamses lugarModificar = session.get(LugarRamses.class, 2L);
		
		//Asi pido unicamente la referencia del registro
		LugarRamses lugarModificar = session.load(LugarRamses.class, 2L);
		
		transaccion.begin();
		lugarModificar.setNombre("Vamos a comer");
		
		//Pregunto si la entidad esta en el contexto de persistencia
		//System.out.println("¿Estas en el contexto de persistencia? "+session.contains(lugarModificar));
		
		//Saco la entidad del contexto de persistencia para trabajar con ella en la vista por ejemplo
		//session.detach(lugarModificar);
		
		//Pregunto si la entidad esta en el contexto de persistencia
		//System.out.println("¿Estas en el contexto de persistencia? "+session.contains(lugarModificar));
		
		//Regreso la entidad sacada previamente al contexto de persistencia
		//session.merge(lugarModificar);
		transaccion.commit();
		

		System.out.println("Pido los lugares por segunda vez");
		List<LugarRamses> lugares = session.createQuery("from LugarRamses").list();
		for (LugarRamses event2 : lugares) {
			System.out.println("Hoy " + event2.getNombre() + " pero en" + event2.getCiudad());
		}
		
		session.close();

		sessionFactory.close();

	}

}
