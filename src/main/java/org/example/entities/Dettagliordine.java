package org.example.entities;



//@Entity
public class Dettagliordine {
    //    @Column(nullable = false)
    private int quantita;
//    @Column(nullable = false,scale=2)
    private double prezzo_unitario;
//    @ManyToOne
//    @JoinColumn(name = "id_ordine", nullable = false, foreignKey = @ForeignKey(name = "ordinidetagli_ordini_fk"))
    private Ordine ordine;
//    @ManyToOne
//    @JoinColumn(name = "id_prodotto", nullable = false, foreignKey = @ForeignKey(name = "ordinidetagli_prodotto_fk"))
    private Prodotto prodotto;

    public Dettagliordine(int quantita, double prezzo_unitario, Ordine ordine,Prodotto prodotto) {
        this.quantita = quantita;
        this.prezzo_unitario = prezzo_unitario;
        this.prodotto = prodotto;
        this.ordine = ordine;
    }

    public Dettagliordine() {
    }

    public Dettagliordine(int id_dettagli_ordini, int quantita, double prezzo_unitario, Ordine ordine, Prodotto prodotto) {
        //    @Id
        //    @GeneratedValue(strategy = GenerationType.IDENTITY)
        this.quantita = quantita;
        this.prezzo_unitario = prezzo_unitario;
        this.ordine = ordine;
        this.prodotto = prodotto;
    }





    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public double getPrezzo_unitario() {
        return prezzo_unitario;
    }

    public void setPrezzo_unitario(double prezzo_unitario) {
        this.prezzo_unitario = prezzo_unitario;
    }

    public Ordine getOrdine() {
        return ordine;
    }

    public void setOrdine(Ordine ordine) {
        this.ordine = ordine;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }
}
