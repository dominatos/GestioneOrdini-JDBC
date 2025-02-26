package org.example;

public class Cliente {
    private int id_cliente;
    private String nome;
    private String cognome;
    private String email;
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
