package com.github.conradovera.annotationmapping;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Main {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure("hibernateAnotations.cfg.xml") // configures settings from hibernate.cfg.xml
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
		session.save( new EventRamses( "Our very first event!", new Date() ) );
		session.save( new EventRamses( "A follow up event", new Date() ) );
		session.getTransaction().commit();
		
		//System.out.println(session.get(Event.class, 1L).getTitle());
		
		List<EventRamses> result = session.createQuery( "from Event" ).list();
		for ( EventRamses event : result ) {
			System.out.println( "Event (" + event.getDate() + ") : " + event.getTitle() );
		}
		
		session.close();
		
		sessionFactory.close();
		
	}

}
