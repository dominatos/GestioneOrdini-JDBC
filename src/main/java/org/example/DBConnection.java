package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {

    private String url = "jdbc:mysql://localhost:3306/";
    private String user = "root";
    private String pass = "root1701";
    private String db_name = "gestordini";
    private Statement st;
    private Connection conn;

    public DBConnection() throws SQLException {
        conn = DriverManager.getConnection(url, user, pass);
        st = conn.createStatement();
        System.out.println("DB Connesso!!");
        createDatabase();
        createTableClienti();
        createTableProdotti();
        createTableOrdini();
        createTableDettagliOrdine();
    }

    private void createDatabase() throws SQLException {
        String sql = "CREATE DATABASE IF NOT EXISTS " + this.db_name;
        st.executeUpdate(sql);
        System.out.println("DB " + this.db_name + " connesso!!");
        conn = DriverManager.getConnection(url+db_name, user, pass);
        st = conn.createStatement();
    }
/// GESTIONE CLIENTI
    private void createTableClienti() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS clienti (" +
                "id_cliente INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "nome VARCHAR(100) NOT NULL," +
                "cognome VARCHAR(100) NOT NULL," +
                "telefono VARCHAR(20) NULL," +
                "email VARCHAR(100) NOT NULL UNIQUE" +
                ")";
        st.executeUpdate(sql);
        System.out.println("Table clienti creata!!");
    }



    // CRUD
    public void insertCliente(Cliente u) throws SQLException {
        String sql = "INSERT INTO clienti(nome, cognome, telefono, email) " +
                "VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, u.getNome());
        ps.setString(2, u.getCognome());
        ps.setString(3, u.getTelefone());
        ps.setString(4, u.getEmail());

        ps.executeUpdate();
        System.out.println(u.getNome() + " " + u.getCognome() + " salvato nel DB!!");
    }

    public Cliente findclient(int id) throws SQLException {
        String sql = "SELECT * FROM clienti WHERE id_cliente = " + id;
        ResultSet rs = st.executeQuery(sql);

        Cliente u = null;
        if(rs.next()) {
            int id_cliente = rs.getInt("id_cliente");
            String firstname = rs.getString("nome");
            String lastname = rs.getString("cognome");

            String telefono = rs.getString("telefono");
            String email = rs.getString("email");
            u = new Cliente(id_cliente, firstname, lastname, telefono, email);
        }
        return u;
    }

    public void updateCliente(Cliente u) throws SQLException {
        String sql = "UPDATE clienti SET nome = ?, cognome = ?, telefono = ?, email = ? " +
                "WHERE id_cliente = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, u.getNome());
        ps.setString(2, u.getCognome());
        ps.setString(3, u.getTelefone());
        ps.setString(4, u.getEmail());
        ps.setInt(5, u.getId_cliente());
        ps.executeUpdate();
        System.out.println(u.getNome() + " " + u.getCognome() + " modificato nel DB!!");
    }

    public void removeCliente(Cliente u) throws SQLException {
        String sql = "DELETE FROM clienti WHERE id_cliente = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, u.getId_cliente());
        ps.executeUpdate();
        System.out.println(u.getNome() + " " + u.getCognome() + " eliminato dal DB!!");
    }

    public List<Cliente> findAllclienti() throws SQLException {
        String sql = "SELECT * FROM clienti";
        ResultSet rs = st.executeQuery(sql);
        List<Cliente> lista = new ArrayList<Cliente>();
        while (rs.next()) {
            int id_cliente = rs.getInt("id_cliente");
            String nome = rs.getString("nome");
            String cognome = rs.getString("cognome");

            String telefono = rs.getString("telefono");
            String email = rs.getString("email");
            Cliente u = new Cliente(id_cliente, nome, cognome, telefono, email);
            lista.add(u);
        }
        return lista;
    }
    /// //GESTIONE PRODOTTI
    private void createTableProdotti() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS prodotti (" +
                "id_prodotto INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "nome VARCHAR(150) NOT NULL," +
                "descrizione TEXT(150) NULL," +
                "prezzo double(10,2) NOT NULL," +
                "quantita_disponibile int NOT NULL" +
                ")";
        st.executeUpdate(sql);
        System.out.println("Table prodotti creata!!");
    }

    public void insertProdotti(Prodotto u) throws SQLException {
        String sql = "INSERT INTO prodotti(nome, descrizione, prezzo, quantita_disponibile) " +
                "VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, u.getNome());
        ps.setString(2, u.getDescrizione());
        ps.setDouble(3, u.getPrezzo());
        ps.setInt(4, u.getQuantita_disponibile());

        ps.executeUpdate();
        System.out.println(u.getNome() + " salvato nel DB!!");
    }
    public Prodotto findProdotto(int id) throws SQLException {
        String sql = "SELECT * FROM prodotti WHERE id_prodotto = " + id;
        ResultSet rs = st.executeQuery(sql);

        Prodotto u = null;
        if(rs.next()) {
            int id_prodotto = rs.getInt("id_prodotto");
            String nome = rs.getString("nome");
            String descrizione = rs.getString("descrizione");

            Double prezzo = rs.getDouble("prezzo");
            int quantita_disponibile = rs.getInt("quantita_disponibile");
            u = new Prodotto(id_prodotto, nome, descrizione, prezzo, quantita_disponibile);
        }
        return u;
    }

    public void updateProdotto(Prodotto u) throws SQLException {
        String sql = "UPDATE prodotti SET nome = ?, descrizione = ?, prezzo = ?, quantita_disponibile = ? " +
                "WHERE id_prodotto = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, u.getNome());
        ps.setString(2, u.getDescrizione());
        ps.setDouble(3, u.getPrezzo());
        ps.setInt(4, u.getQuantita_disponibile());
        ps.setInt(5, u.getId_prodotto());
        ps.executeUpdate();
        System.out.println(u.getNome() + " modificato nel DB!!");
    }

    public void removeProdotto(Prodotto u) throws SQLException {
        String sql = "DELETE FROM prodotti WHERE id_prodotto = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, u.getId_prodotto());
        ps.executeUpdate();
        System.out.println(u.getNome() + " eliminato dal DB!!");
    }
    /// ///GETSIONI ORDINI
    private void createTableOrdini() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS ordini (" +
                "id_ordine INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "id_cliente int (150) NOT NULL," +
                "data_ordine TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "CONSTRAINT ordini_clienti_fk \n" +
                "\t\t\tFOREIGN KEY(id_cliente) \n" +
                "            REFERENCES clienti(id_cliente)\n" +
                "            ON DELETE CASCADE\n" +
                "            ON UPDATE CASCADE" +
                ")";
        st.executeUpdate(sql);
        System.out.println("Table ordini creata!!");
    }
    private void createTableDettagliOrdine() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS dettagliordine (" +
                "id_dettagli_ordini INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "id_ordine INT NOT NULL," +
                "id_prodotto INT NOT NULL," +
                "quantita INT NOT NULL," +
                "prezzo_unitario double(10,2) NOT NULL," +
                "CONSTRAINT ordinidetagli_ordini_fk \n" +
                "\t\t\tFOREIGN KEY(id_ordine) \n" +
                "            REFERENCES ordini(id_ordine)\n" +
                "            ON DELETE CASCADE\n" +
                "            ON UPDATE CASCADE," +
                "CONSTRAINT ordinidetagli_prodotto_fk \n" +
                "\t\t\tFOREIGN KEY(id_prodotto) \n" +
                "            REFERENCES prodotti(id_prodotto)\n" +
                "            ON DELETE CASCADE\n" +
                "            ON UPDATE CASCADE" +
                ")";
        st.executeUpdate(sql);
        System.out.println("Table dettagliordine creata!!");
    }
    public void effetuareOrdine(Prodotto prodotto, Cliente cliente, int quantitaO) throws SQLException {
        String sql = "INSERT INTO ordini(id_cliente) VALUES (?);";

        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, cliente.getId_cliente());

        ps.executeUpdate();

//
        ResultSet rs = ps.getGeneratedKeys();
        int idOrdine = 0;
        if (rs.next()) {
            idOrdine = rs.getInt(1); // Получаем последний сгенерированный ключ
        }

        if (idOrdine == -1) {
            throw new SQLException("Il ordine non e trovato");
        }

        sql = "INSERT INTO dettagliordine(id_ordine, id_prodotto, quantita, prezzo_unitario) VALUES (?, ?, ?, ?)";
        ps = conn.prepareStatement(sql);
        ps.setInt(1, idOrdine);  // Передаём реальный ID
        ps.setInt(2, prodotto.getId_prodotto());
        ps.setInt(3, quantitaO);
        ps.setDouble(4, prodotto.getPrezzo());
        ps.executeUpdate();

        System.out.println("Ordine da " + cliente.getNome() + " per " + prodotto.getNome() + " salvato nel DB!");
        //Update gestordini.prodotti Set quantita_disponibile=20  where id_prodotto=1;
        int idp = prodotto.getId_prodotto();
        sql = "UPDATE gestordini.prodotti SET quantita_disponibile = ? WHERE id_prodotto = ?;";
        ps = conn.prepareStatement(sql);

        int q = prodotto.setQuantita_disponibiledopoordine(quantitaO); // Проверить этот метод, скорее всего, он должен возвращать void
        //System.out.println("WTFWTF");
        //System.out.println(q);

        ps.setInt(1, q);
        ps.setInt(2, idp);

        ps.executeUpdate();
    }
//    public void modificaOrdine(int idProdModifica,int nuovaQuantita,int ordineDaModifica, double nuovaPrezzo) throws SQLException {
//    String sql="Update gestordini.dettagliordine set (quantita,prezzo_unitario) values (?,?) where id_prodotto = ? and id_ordine = ?;";
//    PreparedStatement ps = conn.prepareStatement(sql);
//    ps.setInt(1, nuovaQuantita);
//    ps.setDouble(2, nuovaPrezzo);
//    ps.setInt(3, idProdModifica);
//        ps.setInt(4, ordineDaModifica);
//    ps.executeUpdate();
//        System.out.println("Ordine " + ordineDaModifica + " modificato nel DB! con nuovo quantita "+ nuovaQuantita+" di prodotto"+this.findProdotto(idProdModifica).getNome());
//    }
    public void listaOrdine() throws SQLException {
//    SELECT * FROM gestordini.ordini  AS o
//    INNER JOIN gestordini.dettagliordine AS d ON o.id_ordine = d.id_ordine
//    INNER JOIN gestordini.clienti AS c ON c.id_cliente = o.id_cliente;
    String sql ="SELECT * FROM gestordini.ordini  AS o " +
            "INNER JOIN gestordini.dettagliordine AS d ON o.id_ordine = d.id_ordine " +
            "INNER JOIN gestordini.clienti AS c ON c.id_cliente = o.id_cliente " +
            "INNER JOIN gestordini.prodotti as p ON p.id_prodotto=d.id_prodotto;";
        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet rs=ps.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();

        int columnCount = metaData.getColumnCount();

        while (rs.next()) {
            //for (int i = 1; i <= columnCount; i++) {
                //System.out.println("*********************************");
                //System.out.print(metaData.getColumnName(i) + ": " + rs.getString(i) + "  ");
                int idOrdine = rs.getInt(1); // Первая колонка
                int idCliente = rs.getInt(2); // Вторая колонка
                String date= rs.getString(3);
                int quantita = rs.getInt(7);
                String nomecliente = rs.getString(10)+" "+rs.getString(11);
                double prezzo = rs.getDouble(8);
                String nomeprodotto = rs.getString(15);
                double totale=prezzo*quantita;
                System.out.println("ID Ordine: " + idOrdine);
                System.out.println("ID Cliente: " + idCliente);
                System.out.println("Nome Cliente: " + nomecliente);
                System.out.println("Data Ordine: " + date);
            System.out.println("ID Prodotto: " + rs.getInt(6));
                System.out.println("Nome Prodotto: " + nomeprodotto);
                System.out.println("Quantità: " + quantita);
                System.out.println("Prezzo: " + prezzo + "€");
            System.out.println("Totale da faturare: " + totale);



            //}
            System.out.println("*********************************");
        }
//        while(rs.next()) {
//            rs=toString();
//            //System.out.println("Ordine ID " + rs.getString("id_ordine")+" ID prodotto:"+rs.getString("id_prodotto")+rs.getString("prodotto.nome")+" Quantita;"+rs.getString("quantita")+" Prezzo a pz:"+rs.getString("prezzo_unitario")+" Cliente:"+rs.getString("nome")+" "+rs.getString("Cognome"));
//            }
    }
    public void listaprodotti() throws SQLException {
        String sql="Select * from prodotti;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs=ps.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();

        int columnCount = metaData.getColumnCount();
        while (rs.next()) {

            System.out.println("*********************************");
            int id = rs.getInt(1); // Первая колонка
            String nome = rs.getString(2); // Вторая колонка
            Double prezzo= rs.getDouble(4);
            int quantita = rs.getInt(5);
            System.out.println("ID: " +id);
            System.out.println("Nome: " +nome);
            System.out.println("Prezzo: " +prezzo);
            System.out.println("Quantita: " +quantita);




            System.out.println("*********************************");
        }
    }
//    }


}
