package org.example.DAO;

import org.example.entities.Cliente;
import org.example.entities.Prodotto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class ProdottoDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("projectJPA");
    private EntityManager em = emf.createEntityManager();

    public void saveProdotto(Prodotto prodotto) {
        em.getTransaction().begin();
        em.persist(prodotto);
        em.getTransaction().commit();
    }

    public void removeProdotto(Prodotto prodotto) {
        em.getTransaction().begin();
        em.remove(prodotto);
        em.getTransaction().commit();
    }

    public Prodotto getProdotto(Integer id) {
        return em.find(Prodotto.class, id);
    }

    public List<Prodotto> getAllProdotto() {
        Query q = em.createNamedQuery("Prodotto.findAll", Prodotto.class);
        return q.getResultList();
    }
    public int countAllProdotti() {
        Query q = em.createNamedQuery("Prodotto.count", Long.class);
        return ((Number) q.getSingleResult()).intValue();

    }

}
