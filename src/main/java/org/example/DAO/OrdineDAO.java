package org.example.DAO;

import org.example.entities.Dettagliordine;
import org.example.entities.Ordine;
import org.example.entities.Prodotto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.example.Main.ordineDAO;
import static org.example.Main.prodottoDAO;

public class OrdineDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("projectJPA");
    private EntityManager em = emf.createEntityManager();
    private DettagliordineDAO dettagliordineDAO = new DettagliordineDAO();
    public void saveOrdine(Ordine ordine) {
        em.getTransaction().begin();
        em.persist(ordine);
        em.getTransaction().commit();
    }

    public void removeOrdine(Ordine ordine) {
        em.getTransaction().begin();

        // Удаляем связанные записи
        em.createQuery("DELETE FROM Dettagliordine d WHERE d.ordine = :ordine")
                .setParameter("ordine", ordine)
                .executeUpdate();

        // Удаляем родительскую запись
        em.remove(em.contains(ordine) ? ordine : em.merge(ordine));

        em.getTransaction().commit();

        System.out.println("Ordine n." + ordine.getId_ordine() + " eliminato dal DB!");
    }


    public Ordine getOrdineNodata(Integer id) {
        return em.find(Ordine.class, id);
    }
    public Ordine getOrdineByidwithDATA(Integer id){
        Ordine ordine = this.getOrdineNodata(id);
        List<Prodotto> listprod=new ArrayList<>();


        List<Dettagliordine> detord=dettagliordineDAO.getAllDettagliordine().stream().filter(dettagliordine1 -> dettagliordine1.getOrdine().getId_ordine()==id).toList();
        //detord.forEach(System.out::println);
        detord.stream().forEach(dettagliordine1 ->{
                Prodotto prod=prodottoDAO.getProdotto(dettagliordine1.getProdotto().getId_prodotto());
                prod.setQuantita_perordine(dettagliordine1.getQuantita());
                listprod.add(prod); });
        ordine.setListaProdotti(listprod);

        return ordine;

    }

    public List<Ordine> getAllOrdine() {
        Query q = em.createNamedQuery("Ordine.findAll", Ordine.class);
        List<Ordine> orList=q.getResultList();
        orList.stream().forEach(ordine -> {
            ordine.setListaProdotti(this.getOrdineByidwithDATA(ordine.getId_ordine()).getListaProdotti());

        }
        );

        return q.getResultList();
    }
}
