package br.com.vendagenerica;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class AbstractTestCase {
    protected static EntityManagerFactory emf;

    protected EntityManager manager;

    @BeforeClass
    public static void createEntityManagerFactory() {
    	try {
    		emf = Persistence.createEntityManagerFactory("VendaGenerica");
		} catch (PersistenceException ex) {
			System.out.println("ERROR oq houve: " + ex);
		}
        
    }

    @AfterClass
    public static void closeEntityManagerFactory() {
        if(emf.isOpen())
        	emf.close();
    }

    @Before
    public void beginTransaction() {
        manager = emf.createEntityManager();
        manager.getTransaction().begin();
    }

    @After
    public void rollbackTransaction() {   
        if (manager.getTransaction().isActive()) {
            manager.getTransaction().rollback();
        }

        if (manager.isOpen()) {
            manager.close();
        }
    }
}