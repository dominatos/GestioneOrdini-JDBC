package org.example.entitiesDAO;

import org.example.entities.Prodotto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ProdottoDAO {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("projectJPA");
    private static EntityManager em = emf.createEntityManager();

    public static void inserProdotto(Prodotto p){
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }
    public static void saveProdotto(Prodotto p){
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }
    public static void deleteProdotto(Prodotto p){
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
    }
    public static Prodotto getProdotto(int id){
        return em.find(Prodotto.class, id);
    }
}
