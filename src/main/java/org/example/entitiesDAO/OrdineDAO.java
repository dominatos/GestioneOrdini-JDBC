package org.example.entitiesDAO;

import org.example.entities.Ordine;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class OrdineDAO {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("projectJPA");
    private static EntityManager em = emf.createEntityManager();
    public static void inserOrdine(Ordine o){
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
    }
    public static void updateOrdine(Ordine o){
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
    }
    public static void deleteOrdine(Ordine o){
        em.getTransaction().begin();
        em.remove(o);
        em.getTransaction().commit();
    }



    public static Ordine getOrdine(int id){
        return em.find(Ordine.class, id);
    }
}
