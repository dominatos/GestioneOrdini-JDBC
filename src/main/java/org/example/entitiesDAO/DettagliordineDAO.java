package org.example.entitiesDAO;

import org.example.entities.Dettagliordine;
import org.example.entities.Ordine;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DettagliordineDAO {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("projectJPA");
    private static EntityManager em = emf.createEntityManager();
    public static void inserOrdineDetaggli(Dettagliordine d){
        em.getTransaction().begin();
        em.persist(d);
        em.getTransaction().commit();
    }
    public static void updateOrdineDetaggli(Dettagliordine d){
        em.getTransaction().begin();
        em.merge(d);
        em.getTransaction().commit();
    }
    public static void deleteOrdineDetaggli(Dettagliordine d){
        em.getTransaction().begin();
        em.remove(d);
        em.getTransaction().commit();
    }



    public static Dettagliordine getOrdineDetaggli(int id){
        return em.find(Dettagliordine.class, id);
    }
}
