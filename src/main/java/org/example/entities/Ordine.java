package org.example.entities;


import org.example.DAO.DettagliordineDAO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.Main.*;

@Entity
@Table(name="ordini")
@NamedQuery(name = "Ordine.findAll", query = "SELECT o FROM Ordine o")
public class Ordine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_ordine;
    @Column(nullable = false,columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime data_ordine;
    @Transient
    private List<Prodotto> listaProdotti=new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false, foreignKey = @ForeignKey(name = "ordini_clienti_fk"))
    private Cliente cliente;

    public Ordine(Cliente cliente) {
        this.cliente = cliente;
    }

    public Ordine() {
    }

    public Ordine(int id_ordine, LocalDateTime data_ordine, Cliente cliente) {
        this.id_ordine = id_ordine;
        this.data_ordine = data_ordine;
        this.cliente = cliente;
    }
    public Ordine(int id_ordine, LocalDateTime data_ordine, Cliente cliente, List<Prodotto> listaProdotti) {
        this.id_ordine = id_ordine;
        this.data_ordine = data_ordine;
        this.cliente = cliente;
        this.listaProdotti = listaProdotti;
    }

    public Cliente getCliente() {
        return this.cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getId_ordine() {
        return id_ordine;
    }

    public void setId_ordine(int id_ordine) {
        this.id_ordine = id_ordine;
    }

    public LocalDateTime getData_ordine() {
        return data_ordine;
    }

    public void setData_ordine(LocalDateTime data_ordine) {
        this.data_ordine = data_ordine;
    }

    public List<Prodotto> getListaProdotti() {
        return listaProdotti;
    }

    public void setListaProdotti(List<Prodotto> listaProdotti) {
        this.listaProdotti = listaProdotti;
    }
    public void stamaListaProdotti() {
        this.getListaProdotti().forEach(System.out::println);
    }
    public BigDecimal totale(){
        BigDecimal totale=new BigDecimal("0.00");
        totale = totale.setScale(2, BigDecimal.ROUND_HALF_UP);
                totale= BigDecimal.valueOf(this.listaProdotti.stream().mapToDouble(p -> p.getPrezzo() * p.getQuantita_perordine()).sum());
        return totale;
    }
    public String stampProdotti() {
        String stampe = "";
        BigDecimal prezzo=new BigDecimal("0.00");


        for (Prodotto prodotto : this.listaProdotti) {

            prezzo= BigDecimal.valueOf(prodotto.getPrezzo());
            prezzo = prezzo.setScale(2, BigDecimal.ROUND_HALF_UP);
            stampe =stampe+" ID: " + prodotto.getId_prodotto() + " Nome prodotto: " +
                    prodotto.getNome() + " -" +
                    prodotto.getQuantita_perordine() + "pz. - " +
                    prezzo + "€\n";
        }
        return stampe;
    }


    public void stampa() {
        BigDecimal totale;
        totale= this.totale();
        totale = totale.setScale(2, BigDecimal.ROUND_HALF_UP);



        System.out.println(
                "********************Ordine " + this.id_ordine +

                "********************\n" +"Cliente ID:" + this.cliente.getId_cliente()+" "+cliente.getNome_cliente()+" "+cliente.getCognome() +
                        "\nData:" + this.data_ordine.toLocalDate() +
                "\n Prodotti in ordine:\n" + this.stampProdotti() +
                " \nOrdine totale:"+totale+"€\n************************************************"
        );

    }
}
