package com.github.conradovera.annotationmapping;

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
				.configure("hibernateAnotationsRamses.cfg.xml") // configures settings from hibernate.cfg.xml
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
                //MUCHO OJO AQUI, EN LA SIGUIENTE LINEA EMPIEZA UNA TRANSACCION
		session.beginTransaction();
		session.save( new EventRamses( "Our very first event!", new Date(),1L) );
		session.save( new EventRamses( "A follow up event", new Date() ,2L));
		session.save( new LugarRamses( "Vamos al cine", "Plaza Galerias Campeche" ) );
		session.save( new LugarRamses( "Vamo a comer", "Boston" ) );
                //MUCHO OJO AQUI, EN LA SIGUIENTE LINEA FINALIZA LA TRANSACCION
		session.getTransaction().commit();
		
		//System.out.println(session.get(Event.class, 1L).getTitle());
		
		List<EventRamses> result = session.createQuery( "from EventRamses" ).list();
		for ( EventRamses event : result ) {
			System.out.println( "Event (" + event.getDate() + ") : " + event.getTitle() );
		}
                
                List<LugarRamses> result2 = session.createQuery( "from LugarRamses" ).list();
		for ( LugarRamses event2 : result2 ) {
			System.out.println( "Hoy " + event2.getNombre() + " pero en" + event2.getCiudad() );
		}
		
                
                LugarRamses lugarModificar = session.get(LugarRamses.class, 2L);
                lugarModificar.setNombre("Vamos a comer");
                //UPDATE
                //MUCHO OJO AQUI, EN LA SIGUIENTE LINEA EMPIEZA UNA TRANSACCION
		session.beginTransaction();
                
                
                
                
                //MUCHO OJO AQUI, EN LA SIGUIENTE LINEA FINALIZA LA TRANSACCION
		session.getTransaction().commit();
                
		session.close();
		
		sessionFactory.close();
		
	}

}
