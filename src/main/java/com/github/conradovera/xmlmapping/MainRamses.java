package com.github.conradovera.xmlmapping;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class MainRamses {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				//.configure() // configures settings from hibernate.cfg.xml
				.configure("hibernateMySqlRamses.cfg.xml") // configures settings from maquina de ramses
				.build();
		
		SessionFactory sessionFactory = null;
		
		try {
			sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		}
		catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			e.printStackTrace();
			StandardServiceRegistryBuilder.destroy( registry );
		}
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save( new EventRamses( "Our very first event!", new Date(),"1") );
		session.save( new EventRamses( "A follow up event", new Date(),"2") );
		session.save(new LugarRamses("Viernes social","Rosarito, Campeche"));
		session.save(new LugarRamses("A remojarse","Progreso, Yucatan"));
		session.getTransaction().commit();
		
		//System.out.println(session.get(Event.class, 1L).getTitle());
		
		List<EventRamses> result = session.createQuery( "from EventRamses" ).list();
		for ( EventRamses event : result ) {
			System.out.println( "Event (" + event.getDate() + ") : " + event.getTitle() + " : " + event.getIdLugar());
		}
		
		List<LugarRamses> result2 = session.createQuery( "from LugarRamses" ).list();
		for ( LugarRamses event : result2 ) {
			System.out.println( "Hoy toca " + event.getNombre() + " en " + event.getCiudad() );
		}
		
		session.close();
		
		sessionFactory.close();
		
	}

}
