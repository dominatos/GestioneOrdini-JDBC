package org.example.entities;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="prodotti")
@NamedQuery(name = "Prodotto.findAll", query = "SELECT p FROM Prodotto p")
@NamedQuery(name = "Prodotto.count", query = "SELECT count(p) FROM Prodotto p")
public class Prodotto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_prodotto;
    @Column(name="nome_prodotto", nullable=false, length=100)
    private String nome;
    @Column(name="descr_prodotto",nullable = true)
    private String descrizione;
    @Column(nullable=false,scale=2)
    private double prezzo;
    @Column(nullable=false)
    private int quantita_disponibile;
    @Transient
    private int quantita_perordine;
    public Prodotto(String nome, String descrizione, double prezzo, int quantita_disponibile) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.quantita_disponibile = quantita_disponibile;
    }

    public Prodotto(String nome, String descrizione, double prezzo, int quantita_disponibile,int quantita_perordine) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.quantita_disponibile = quantita_disponibile;
        this.quantita_perordine = quantita_perordine;
    }

    public Prodotto() {
    }

    public Prodotto(int id_prodotto, String nome, String descrizione, double prezzo, int quantita_disponibile) {

        this.id_prodotto = id_prodotto;
    this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.quantita_disponibile = quantita_disponibile;
    }

    public int getId_prodotto() {
        return id_prodotto;
    }

    public int getQuantita_perordine() {
        return quantita_perordine;
    }

    public void setQuantita_perordine(int quantita_perordine) {
        this.quantita_perordine = quantita_perordine;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getQuantita_disponibile() {
        return quantita_disponibile;
    }

    public void setQuantita_disponibile(int quantita_disponibile) {
        this.quantita_disponibile = quantita_disponibile;
    }
    public int setQuantita_disponibiledopoordine(int x) {
        this.quantita_disponibile = this.quantita_disponibile-x;
    return this.quantita_disponibile;

    }

    public void setId_prodotto(Integer id_prodotto) {
        this.id_prodotto = id_prodotto;
    }


    @Override
    public String toString() {
        return "Prodotto{" +
                "id_prodotto=" + id_prodotto +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", prezzo=" + prezzo +
                ", quantita_disponibile=" + quantita_disponibile +
                '}';
    }
}
