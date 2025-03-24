package org.example.entities;

import javax.persistence.*;

//@Entity
//@Table(name="clienti")
public class Cliente {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cliente;
//    @Column(name="nome_cliente", nullable=false, length=50)
    private String nome;
//    @Column(nullable = false, length=50)
    private String cognome;
//    @Column(nullable = false, unique=true)
    private String email;
//    @Column(nullable = false, unique=true)
    private String telefone;

    public Cliente(int id_cliente,String nome, String cognome, String email, String telefone) {

        this.id_cliente = id_cliente;
    this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.telefone = telefone;
    }

    public Cliente(String nome, String cognome, String email, String telefone) {


        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.telefone = telefone;
    }

    public Cliente(){}

    public int getId_cliente() {
        return id_cliente;
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "cliente{" +
                "idcliente=" + id_cliente +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}
