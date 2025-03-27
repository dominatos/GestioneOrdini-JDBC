package org.example.entities;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Entity
//@Table(name="ordini")
public class Ordine {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_ordine;
//    @Column(nullable = false,columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime data_ordine;
    private List<Prodotto> listaProdotti=new ArrayList<>();
//    @ManyToOne
//    @JoinColumn(name = "id_cliente", nullable = false, foreignKey = @ForeignKey(name = "ordini_clienti_fk"))
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
        for (Prodotto prodotto : this.listaProdotti) {
            stampe =stampe+" ID: " + prodotto.getId_prodotto() + " Nome prodotto: " +
                    prodotto.getNome() + " " +
                    prodotto.getQuantita_perordine() + "pz. Prezzo " +
                    prodotto.getPrezzo() + "€\n";
        }
        return stampe;
    }


    public void stampa() {
        System.out.println(
                "********************Ordine " + id_ordine +

                "********************\n" +"Cliente ID:" + cliente.getId_cliente()+" "+cliente.getNome()+" "+cliente.getCognome() +
                        "Data=" + data_ordine +
                "\n Prodotti in ordine:\n" + stampProdotti() +
                " \nOrdine totale:"+totale()+"\n************************************************"
        );

    }
}
