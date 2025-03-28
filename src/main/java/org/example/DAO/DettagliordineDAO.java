package org.example.DAO;

import org.example.entities.Dettagliordine;
import org.example.entities.Ordine;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class DettagliordineDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("projectJPA");
    private EntityManager em = emf.createEntityManager();

    public void saveDettagliordine(Dettagliordine dettagliordine) {
        em.getTransaction().begin();
        em.persist(dettagliordine);
        em.getTransaction().commit();
    }

    public void removeDettagliordine(Dettagliordine dettagliordine) {
        em.getTransaction().begin();
        em.remove(dettagliordine);
        em.getTransaction().commit();
    }


    public Dettagliordine getDettagliordine(Integer id) {
        return em.find(Dettagliordine.class, id);
    }

    public List<Dettagliordine> getAllDettagliordine() {
        Query q = em.createNamedQuery("Dettagliordine.findAll", Dettagliordine.class);
        return q.getResultList();
    }
}
