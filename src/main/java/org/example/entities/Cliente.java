package org.example.entities;


import javax.persistence.*;

@Entity
@Table(name="clienti")
@NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")
@NamedQuery(name = "Cliente.count", query = "SELECT count(c) FROM Cliente c")

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_cliente;
    @Column(name="nome_cliente", nullable=false, length=50)
    private String nome_cliente;
    @Column(nullable = false, length=50)
    private String cognome;
    @Column(nullable = false, unique=true)
    private String email;
    @Column(nullable = false, unique=true)
    private String telefono;


    public Cliente(int id_cliente, String nome_cliente, String cognome, String email, String telefono) {

        this.id_cliente = id_cliente;
    this.nome_cliente = nome_cliente;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
    }

    public Cliente(String nome_cliente, String cognome, String email, String telefono) {


        this.nome_cliente = nome_cliente;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
    }

    public Cliente(){}

    public int getId_cliente() {
        return id_cliente;
    }



    public String getNome_cliente() {
        return nome_cliente;
    }

    public void setNome_cliente(String nome) {
        this.nome_cliente = nome;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefone) {
        this.telefono = telefone;
    }

    @Override
    public String toString() {
        return "cliente{" +
                "idcliente=" + id_cliente +
                ", nome='" + nome_cliente + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefono + '\'' +
                '}';
    }
}
