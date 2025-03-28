package org.example.DAO;

import org.example.entities.Cliente;
import org.example.entities.Prodotto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class ClienteDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("projectJPA");
    private EntityManager em = emf.createEntityManager();

    public void saveCliente(Cliente cliente) {
        em.getTransaction().begin();
        em.persist(cliente);
        em.getTransaction().commit();
    }

    public void removeCliente(Cliente cliente) {
        em.getTransaction().begin();
        em.remove(cliente);
        em.getTransaction().commit();
    }

    public Cliente getCliente(Integer id) {
        return em.find(Cliente.class, id);
    }

    public List<Cliente> getAllCliente() {
        Query q = em.createNamedQuery("Cliente.findAll", Cliente.class);
        return q.getResultList();
    }
    public int countAllCliente() {
        Query q = em.createNamedQuery("Cliente.count", Long.class);
        return ((Number) q.getSingleResult()).intValue();

    }
}
