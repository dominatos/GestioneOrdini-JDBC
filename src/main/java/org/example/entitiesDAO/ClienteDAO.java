package org.example.entitiesDAO;

import org.example.entities.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ClienteDAO {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("projectJPA");
    private static EntityManager em = emf.createEntityManager();
    public static void inserClient(Cliente c){
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
    }
    public static Cliente getCliente(int id){
        return em.find(Cliente.class, id);
    }
    public static void updateClient(Cliente c){
        em.getTransaction().begin();
        em.merge(c);
        em.getTransaction().commit();
    }
    public static void deleteClient(Cliente c){
        em.getTransaction().begin();
        em.remove(c);
        em.getTransaction().commit();
    }
}
