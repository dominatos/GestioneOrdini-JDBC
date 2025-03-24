package org.example;

import org.example.entities.Cliente;
import org.example.entities.Ordine;
import org.example.entities.Prodotto;

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
        System.out.println("DB " + this.db_name + " e creato!!!");
        conn = DriverManager.getConnection(url+db_name, user, pass);
        st = conn.createStatement();
    }
    public void dropDatabase() throws SQLException {
        String sql = "DROP DATABASE IF EXISTS " + this.db_name;
        st.execute(sql);
        System.out.println("DB " + this.db_name + " e eliminato!!");

    }
/// GESTIONE CLIENTI
    private void createTableClienti() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS clienti (" +
                "id_cliente INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "nome_cliente VARCHAR(100) NOT NULL," +
                "cognome VARCHAR(100) NOT NULL," +
                "telefono VARCHAR(20) NULL," +
                "email VARCHAR(100) NOT NULL UNIQUE" +
                ")";

        st.executeUpdate(sql);
        System.out.println("Table clienti ha inizializzato!!!");
    }



    // CRUD
    // Metodo per inserire un nuovo cliente nel database
// Prende come parametro un oggetto Cliente e salva i suoi dettagli nel database
    public void insertCliente(Cliente u) throws SQLException {
        // Query SQL per inserire i dati del cliente
        String sql = "INSERT INTO clienti(nome_cliente, cognome, telefono, email) " +
                "VALUES (?, ?, ?, ?)";
        // Preparazione della query con i parametri forniti
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, u.getNome()); // Imposta il nome del cliente
        ps.setString(2, u.getCognome()); // Imposta il cognome del cliente
        ps.setString(3, u.getTelefone()); // Imposta il telefono del cliente
        ps.setString(4, u.getEmail()); // Imposta l'email del cliente

        // Esecuzione della query SQL per salvare il cliente
        ps.executeUpdate();
        System.out.println(u.getNome() + " " + u.getCognome() + " salvato nel DB!!");
    }

    // Metodo per trovare un cliente nel database tramite il suo ID
// Restituisce un oggetto Cliente se trovato, altrimenti null
    public Cliente findclient(int id) throws SQLException {
        // Query SQL per cercare un cliente con uno specifico id_cliente
        String sql = "SELECT * FROM clienti WHERE id_cliente = " + id;
        ResultSet rs = st.executeQuery(sql);

        Cliente u = null; // Oggetto Cliente da restituire
        // Se un risultato è trovato, inizializza l'oggetto Cliente
        if(rs.next()) {
            int id_cliente = rs.getInt("id_cliente"); // Ottieni l'ID del cliente
            String firstname = rs.getString("nome_cliente"); // Ottieni il nome del cliente
            String lastname = rs.getString("cognome"); // Ottieni il cognome del cliente
            String telefono = rs.getString("telefono"); // Ottieni il telefono
            String email = rs.getString("email"); // Ottieni l'email
            u = new Cliente(id_cliente, firstname, lastname, telefono, email); // Crea l'oggetto Cliente
        }
        return u; // Restituisce il cliente trovato o null
    }

    // Metodo per aggiornare i dettagli di un cliente esistente nel database
// Prende come parametro un oggetto Cliente con i nuovi dati
    public void updateCliente(Cliente u) throws SQLException {
        // Query SQL per aggiornare i dati del cliente
        String sql = "UPDATE clienti SET nome_cliente = ?, cognome = ?, telefono = ?, email = ? " +
                "WHERE id_cliente = ?";
        // Preparazione della query con i dati aggiornati
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, u.getNome()); // Aggiorna il nome
        ps.setString(2, u.getCognome()); // Aggiorna il cognome
        ps.setString(3, u.getTelefone()); // Aggiorna il telefono
        ps.setString(4, u.getEmail()); // Aggiorna l'email
        ps.setInt(5, u.getId_cliente()); // Specifica l'ID del cliente da aggiornare

        // Esecuzione della query SQL
        ps.executeUpdate();
        System.out.println(u.getNome() + " " + u.getCognome() + " modificato nel DB!!");
    }

    // Metodo per eliminare un cliente dal database
// Prende come parametro un oggetto Cliente per identificare il record da eliminare
    public void removeCliente(Cliente u) throws SQLException {
        // Query SQL per eliminare il cliente con uno specifico id_cliente
        String sql = "DELETE FROM clienti WHERE id_cliente = ?";
        // Preparazione della query
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, u.getId_cliente()); // Specifica l'ID del cliente da eliminare

        // Esecuzione della query SQL
        ps.executeUpdate();
        System.out.println(u.getNome() + " " + u.getCognome() + " eliminato dal DB!!");
    }

    // Metodo per ottenere tutti i clienti dal database
// Restituisce una lista di oggetti Cliente
    public List<Cliente> findAllclienti() throws SQLException {
        // Query SQL per selezionare tutti i clienti
        String sql = "SELECT * FROM clienti";
        ResultSet rs = st.executeQuery(sql);

        // Lista per salvare tutti i clienti trovati
        List<Cliente> lista = new ArrayList<Cliente>();
        // Ciclo attraverso il ResultSet per ogni cliente trovato
        while (rs.next()) {
            int id_cliente = rs.getInt("id_cliente"); // Ottieni l'ID del cliente
            String nome = rs.getString("nome_cliente"); // Ottieni il nome del cliente
            String cognome = rs.getString("cognome"); // Ottieni il cognome del cliente
            String telefono = rs.getString("telefono"); // Ottieni il telefono
            String email = rs.getString("email"); // Ottieni l'email
            Cliente u = new Cliente(id_cliente, nome, cognome, telefono, email); // Crea un oggetto Cliente
            lista.add(u); // Aggiungi il cliente alla lista
        }
        return lista; // Restituisce la lista di tutti i clienti
    }
    /// //GESTIONE PRODOTTI
    private void createTableProdotti() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS prodotti (" +
                "id_prodotto INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "nome_prodotto VARCHAR(150) NOT NULL," +
                "descr_prodotto TEXT(150) NULL," +
                "prezzo double(10,2) NOT NULL," +
                "quantita_disponibile int NOT NULL" +
                ")";
        st.executeUpdate(sql);
        System.out.println("Table prodotti ha inizializzato!!");
    }

    // Metodo per inserire un nuovo prodotto nel database
// Prende come parametro un oggetto Prodotto e salva i suoi dettagli nel database
    public void insertProdotti(Prodotto u) throws SQLException {
        // Query SQL per inserire i dati del prodotto
        String sql = "INSERT INTO prodotti(nome_prodotto, descr_prodotto, prezzo, quantita_disponibile) " +
                "VALUES (?, ?, ?, ?)";
        // Preparazione della query con i parametri forniti
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, u.getNome()); // Imposta il nome del prodotto
        ps.setString(2, u.getDescrizione()); // Imposta la descrizione del prodotto
        ps.setDouble(3, u.getPrezzo()); // Imposta il prezzo del prodotto
        ps.setInt(4, u.getQuantita_disponibile()); // Imposta la quantità disponibile del prodotto

        // Esecuzione della query SQL per salvare il prodotto
        ps.executeUpdate();
        System.out.println(u.getNome() + " salvato nel DB!!");
    }

    // Metodo per trovare un prodotto nel database tramite il suo ID
// Restituisce un oggetto Prodotto se trovato, altrimenti null
    public Prodotto findProdotto(int id) throws SQLException {
        // Query SQL per cercare un prodotto con uno specifico id_prodotto
        String sql = "SELECT * FROM prodotti WHERE id_prodotto = " + id;
        ResultSet rs = st.executeQuery(sql);

        Prodotto u = null; // Oggetto Prodotto da restituire
        // Se un risultato è trovato, inizializza l'oggetto Prodotto
        if (rs.next()) {
            int id_prodotto = rs.getInt("id_prodotto"); // Ottieni l'ID del prodotto
            String nome = rs.getString("nome_prodotto"); // Ottieni il nome del prodotto
            String descrizione = rs.getString("descr_prodotto"); // Ottieni la descrizione del prodotto
            Double prezzo = rs.getDouble("prezzo"); // Ottieni il prezzo del prodotto
            int quantita_disponibile = rs.getInt("quantita_disponibile"); // Ottieni la quantità disponibile

            // Crea l'oggetto Prodotto
            u = new Prodotto(id_prodotto, nome, descrizione, prezzo, quantita_disponibile);
        }
        return u; // Restituisce il prodotto trovato o null
    }

    // Metodo per aggiornare i dettagli di un prodotto esistente nel database
// Prende come parametro un oggetto Prodotto con i nuovi dati
    public void updateProdotto(Prodotto u) throws SQLException {
        // Query SQL per aggiornare i dati del prodotto
        String sql = "UPDATE prodotti SET nome_prodotto = ?, descr_prodotto = ?, prezzo = ?, quantita_disponibile = ? " +
                "WHERE id_prodotto = ?";
        // Preparazione della query con i dati aggiornati
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, u.getNome()); // Aggiorna il nome del prodotto
        ps.setString(2, u.getDescrizione()); // Aggiorna la descrizione del prodotto
        ps.setDouble(3, u.getPrezzo()); // Aggiorna il prezzo del prodotto
        ps.setInt(4, u.getQuantita_disponibile()); // Aggiorna la quantità disponibile
        ps.setInt(5, u.getId_prodotto()); // Specifica l'ID del prodotto da aggiornare

        // Esecuzione della query SQL
        ps.executeUpdate();
        System.out.println(u.getNome() + " modificato nel DB!!");
    }

    // Metodo per eliminare un prodotto dal database
// Prende come parametro un oggetto Prodotto per identificare il record da eliminare
    public void removeProdotto(Prodotto u) throws SQLException {
        if (u != null) {
            // Query SQL per eliminare il prodotto con uno specifico id_prodotto
            String sql = "DELETE FROM prodotti WHERE id_prodotto = ?";
            // Preparazione della query
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, u.getId_prodotto()); // Specifica l'ID del prodotto da eliminare

            // Esecuzione della query SQL
            ps.executeUpdate();
            System.out.println(u.getNome() + " eliminato dal DB!!");
        }
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
            System.out.println("Table prodotti ha inizializzato!!");
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
        System.out.println("Table dettagliordine ha inizializzato!!");

    }
    // Metodo per effettuare un ordine per un singolo prodotto
// Prende come parametri un oggetto Prodotto, un Cliente e una quantità specifica (quantitaO)
// L'ordine viene inserito nella tabella `ordini`, i dettagli del prodotto nella tabella `dettagliordine`,
// e la quantità disponibile del prodotto viene aggiornata nel database.
    public void effetuareOrdinePerUnoProdotto(Prodotto prodotto, Cliente cliente, int quantitaO)
            throws SQLException, ClienteNonTrovateException, ProdottoNonTrovateException, NonAbbastanzaQuantException {

        // Controlla se il cliente è null
        if (cliente == null) {
            System.out.println("Cliente non è trovato");
            throw new ClienteNonTrovateException("Cliente non è trovato"); // Lancia eccezione se cliente non esiste
        }
        // Controlla se il prodotto è null
        else if (prodotto == null) {
            throw new ProdottoNonTrovateException("Prodotto non è trovato"); // Lancia eccezione se prodotto non esiste
        }
        // Controlla se la quantità disponibile è sufficiente
        else if (prodotto.getQuantita_disponibile() >= quantitaO) {
            // Query per inserire un nuovo ordine nella tabella `ordini`
            String sql = "INSERT INTO ordini(id_cliente) VALUES (?);";

            // Preparazione della query per inserire l'ordine
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, cliente.getId_cliente()); // Imposta l'ID del cliente per l'ordine

            ps.executeUpdate(); // Esegue la query di inserimento

            // Ottiene la chiave generata per l'ordine appena inserito
            ResultSet rs = ps.getGeneratedKeys();
            int idOrdine = 0; // Variabile per memorizzare l'ID dell'ordine
            if (rs.next()) {
                idOrdine = rs.getInt(1); // Ottiene l'ID dell'ordine generato
            }else {
                
                throw new SQLException("Non è stato possibile generare l'ordine!");
            }


            // Query per inserire i dettagli dell'ordine nella tabella `dettagliordine`
            sql = "INSERT INTO dettagliordine(id_ordine, id_prodotto, quantita, prezzo_unitario) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idOrdine); // ID dell'ordine
            ps.setInt(2, prodotto.getId_prodotto()); // ID del prodotto
            ps.setInt(3, quantitaO); // Quantità specificata per l'ordine
            ps.setDouble(4, prodotto.getPrezzo()); // Prezzo unitario del prodotto
            ps.executeUpdate(); // Esegue la query

            // Messaggio di conferma
            System.out.println("Ordine da " + cliente.getNome() + " per " + prodotto.getNome() + " salvato nel DB!");

            // Query per aggiornare la quantità disponibile del prodotto
            int idp = prodotto.getId_prodotto(); // ID del prodotto da aggiornare
            sql = "UPDATE gestordini.prodotti SET quantita_disponibile = ? WHERE id_prodotto = ?;";
            ps = conn.prepareStatement(sql);

            // Calcola la nuova quantità disponibile
            int q = prodotto.getQuantita_disponibile() - quantitaO;
            ps.setInt(1, q); // Imposta la nuova quantità disponibile
            ps.setInt(2, idp); // Specifica l'ID del prodotto

            ps.executeUpdate(); // Esegue la query di aggiornamento
        }
        // Se la quantità disponibile non è sufficiente
        else {
            throw new NonAbbastanzaQuantException("Non ci sono abbastanza quantità disponibile per effettuare l'ordine!");
        }
    }



    // Metodo per effettuare un ordine con una quantità fissa per ogni prodotto
// Prende come parametri una lista di prodotti, un cliente e la quantità fissa (quantitaO)
// L'ordine viene inserito nella tabella `ordini`, i dettagli dei prodotti nella tabella `dettagliordine`,
// e la quantità disponibile di ciascun prodotto viene aggiornata nel database.
    public void effetuareOrdineListProdottiQuantitaFIssa(List<Prodotto> listProdotti, Cliente cliente, int quantitaO)
            throws SQLException, ClienteNonTrovateException, ProdottoNonTrovateException, NonAbbastanzaQuantException {

        int idOrdine = 0; // Variabile per memorizzare l'ID dell'ordine generato

        // Controlla se il cliente è null
        if (cliente == null) {
            System.out.println("Cliente non è trovato");
            throw new ClienteNonTrovateException("Cliente non è trovato");
        }
        // Controlla se la lista dei prodotti è vuota
        else if (listProdotti.isEmpty()) {
            throw new ProdottoNonTrovateException("La lista dei prodotti è vuota");
        }
        // Controlla se la quantità specificata è maggiore di 0
        else if (quantitaO > 0) {
            // Query per inserire un nuovo ordine nella tabella `ordini`
            String sql = "INSERT INTO ordini(id_cliente) VALUES (?);";

            // Preparazione della query per inserire l'ordine
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, cliente.getId_cliente()); // Imposta l'ID del cliente per l'ordine

            ps.executeUpdate(); // Esegue la query di inserimento

            // Ottiene la chiave generata per l'ordine appena inserito
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idOrdine = rs.getInt(1); // Ottiene l'ID dell'ordine
            }

            // Controlla se l'ID dell'ordine non è valido
            if (idOrdine == -1) {
                throw new SQLException("L'ordine non è trovato");
            }
        }
        // Se la quantità è inferiore o uguale a 0
        else if (quantitaO <= 0) {
            System.out.println("Quantità di prodotto deve essere > 0");
        }

        int finalIdOrdine = idOrdine; // Variabile finale per l'ID dell'ordine
        System.out.println("*******************************************************");
        System.out.println("Ordine " + finalIdOrdine + ":");

        // Ciclo per ogni prodotto nella lista
        listProdotti.forEach(p -> {
            if (p != null) {
                // Controlla se la quantità disponibile è sufficiente
                if (p.getQuantita_disponibile() >= quantitaO) {
                    // Query per inserire i dettagli dell'ordine nella tabella `dettagliordine`
                    String sql = "INSERT INTO dettagliordine(id_ordine, id_prodotto, quantita, prezzo_unitario) VALUES (?, ?, ?, ?)";
                    PreparedStatement ps = null;
                    try {
                        ps = conn.prepareStatement(sql);
                        ps.setInt(1, finalIdOrdine); // ID dell'ordine
                        ps.setInt(2, p.getId_prodotto()); // ID del prodotto
                        ps.setInt(3, quantitaO); // Quantità fissa specificata
                        ps.setDouble(4, p.getPrezzo()); // Prezzo unitario del prodotto
                        ps.executeUpdate(); // Esegue la query
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Prodotto " + p.getId_prodotto() + " " + p.getNome() +
                            " dal cliente: " + cliente.getId_cliente() + " " + cliente.getNome() +
                            " salvato nel DB!");

                    int idp = p.getId_prodotto();
                    try {
                        // Query per aggiornare la quantità disponibile del prodotto
                        sql = "UPDATE gestordini.prodotti SET quantita_disponibile = ? WHERE id_prodotto = ?;";
                        ps = conn.prepareStatement(sql);

                        // Calcola la nuova quantità disponibile
                        int q = p.getQuantita_disponibile() - quantitaO;
                        ps.setInt(1, q); // Imposta la nuova quantità disponibile
                        ps.setInt(2, idp); // ID del prodotto da aggiornare

                        ps.executeUpdate(); // Esegue la query di aggiornamento
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                // Se la quantità disponibile non è sufficiente
                else {
                    throw new NonAbbastanzaQuantException("Non ci sono abbastanza quantità disponibili per effettuare l'ordine!");
                }
            }
            // Se il prodotto è null
            else if (p == null) {
                throw new ProdottoNonTrovateException("Prodotto non è trovato");
            }
        });

        System.out.println("*******************************************************");
    }
    // Metodo per contare il numero totale di prodotti nella tabella `prodotti`
    public int countProdotti() throws SQLException {
        // Query SQL per contare il numero totale di righe nella tabella `prodotti`
        String sql = "SELECT COUNT(*) FROM prodotti;";

        // Utilizzo di un blocco try-with-resources per gestire automaticamente le risorse
        try (PreparedStatement ps = conn.prepareStatement(sql); // Prepara la query
             ResultSet rs = ps.executeQuery()) { // Esegue la query e ottiene il risultato
            // Controlla se esiste un risultato e restituisce il valore della prima colonna
            if (rs.next()) {
                return rs.getInt(1); // Restituisce il conteggio dei prodotti
            }
        }
        return 0; // Restituisce 0 se non ci sono prodotti o se non è stato trovato alcun risultato
    }

    // Metodo per contare il numero totale di clienti nella tabella `clienti`
    public int countCliente() throws SQLException {
        // Query SQL per contare il numero totale di righe nella tabella `clienti`
        String sql = "SELECT COUNT(*) FROM clienti;";

        // Utilizzo di un blocco try-with-resources per gestire automaticamente le risorse
        try (PreparedStatement ps = conn.prepareStatement(sql); // Prepara la query
             ResultSet rs = ps.executeQuery()) { // Esegue la query e ottiene il risultato
            // Controlla se esiste un risultato e restituisce il valore della prima colonna
            if (rs.next()) {
                return rs.getInt(1); // Restituisce il conteggio dei clienti
            }
        }
        return 0; // Restituisce 0 se non ci sono clienti o se non è stato trovato alcun risultato
    }



    // Metodo per effettuare un ordine con una lista di prodotti e un cliente specifico
// L'ordine viene inserito nella tabella `ordini`, i dettagli dei prodotti vengono inseriti in `dettagliordine`,
// e la quantità disponibile di ogni prodotto viene aggiornata.
    public void effetuareOrdineListProdottiQuantitaRandom(List<Prodotto> listProdotti, Cliente cliente)
            throws SQLException, ClienteNonTrovateException, ProdottoNonTrovateException, NonAbbastanzaQuantException {

        int idOrdine = 0; // Variabile per memorizzare l'ID dell'ordine generato

        // Controllo se il cliente è null
        if (cliente == null) {
            System.out.println("Cliente non è trovato");
            throw new ClienteNonTrovateException("Cliente non è trovato");
        }
        // Controllo se la lista dei prodotti è vuota
        else if (listProdotti.isEmpty()) {
            throw new ProdottoNonTrovateException("La lista dei prodotti è vuota");
        }
        // Se il cliente esiste e la lista dei prodotti non è vuota
        else {
            // Query per inserire un nuovo ordine nella tabella `ordini`
            String sql = "INSERT INTO ordini(id_cliente) VALUES (?);";

            // Preparazione della query per inserire l'ordine
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, cliente.getId_cliente()); // Imposta l'ID del cliente per l'ordine

            ps.executeUpdate(); // Esegue la query di inserimento

            // Ottiene la chiave generata per l'ordine appena inserito
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idOrdine = rs.getInt(1); // Ottiene l'ID dell'ordine
            }

            // Controllo se l'ID dell'ordine non è valido
            if (idOrdine == -1) {
                throw new SQLException("L'ordine non è trovato");
            }
        }

        int finalIdOrdine = idOrdine; // Variabile finale per l'ID dell'ordine
        System.out.println("*******************************************************");
        System.out.println("Ordine " + finalIdOrdine + ":");

        // Ciclo per ogni prodotto nella lista
        listProdotti.forEach(p -> {
            if (p != null) {
                // Controlla se la quantità disponibile è sufficiente
                if (p.getQuantita_disponibile() >= p.getQuantita_perordine()) {
                    // Query per inserire i dettagli dell'ordine nella tabella `dettagliordine`
                    String sql = "INSERT INTO dettagliordine(id_ordine, id_prodotto, quantita, prezzo_unitario) VALUES (?, ?, ?, ?)";
                    PreparedStatement ps = null;
                    try {
                        ps = conn.prepareStatement(sql);
                        ps.setInt(1, finalIdOrdine); // ID dell'ordine
                        ps.setInt(2, p.getId_prodotto()); // ID del prodotto
                        ps.setInt(3, p.getQuantita_perordine()); // Quantità dell'ordine per il prodotto
                        ps.setDouble(4, p.getPrezzo()); // Prezzo unitario del prodotto
                        ps.executeUpdate(); // Esegue la query
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Prodotto " + p.getId_prodotto() + " " + p.getNome() +
                            " dal cliente: " + cliente.getId_cliente() + " " + cliente.getNome() +
                            " salvato nel DB!");

                    int idp = p.getId_prodotto();
                    try {
                        // Query per aggiornare la quantità disponibile del prodotto
                        sql = "UPDATE gestordini.prodotti SET quantita_disponibile = ? WHERE id_prodotto = ?;";
                        ps = conn.prepareStatement(sql);

                        // Calcola la nuova quantità disponibile
                        int q = p.getQuantita_disponibile() - p.getQuantita_perordine();
                        ps.setInt(1, q); // Imposta la nuova quantità disponibile
                        ps.setInt(2, idp); // ID del prodotto da aggiornare

                        ps.executeUpdate(); // Esegue la query di aggiornamento
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                // Se la quantità disponibile non è sufficiente
                else {
                    throw new NonAbbastanzaQuantException("Non ci sono abbastanza quantità disponibili per effettuare l'ordine!");
                }
            }
            // Se il prodotto è null
            else if (p == null) {
                throw new ProdottoNonTrovateException("Prodotto non è trovato");
            }
        });

        System.out.println("*******************************************************");
    }

    public void editOrdinemulti(Ordine ordine)
            throws SQLException, ClienteNonTrovateException, ProdottoNonTrovateException, NonAbbastanzaQuantException {
        int finalIdOrdine = ordine.getId_ordine();










        // Controllo se la lista dei prodotti è vuota
        if (ordine.getListaProdotti().isEmpty()) {
            throw new ProdottoNonTrovateException("La lista dei prodotti è vuota");
        } else if (finalIdOrdine <= 0) {
            throw new IllegalArgumentException("ID Ordine non valido: " + finalIdOrdine);
        }
        // Se il cliente esiste e la lista dei prodotti non è vuota
        else {

            String sql="Delete FROM dettagliordine WHERE id_ordine = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, finalIdOrdine);
            ps.executeUpdate();
            // Variabile finale per l'ID dell'ordine
            System.out.println("*******************************************************");
            System.out.println("Ordine " + finalIdOrdine + ":");

            // Ciclo per ogni prodotto nella lista
            ordine.getListaProdotti().forEach(p -> {
                if (p != null) {
                    // Controlla se la quantità disponibile è sufficiente
                    if (p.getQuantita_disponibile() >= p.getQuantita_perordine()) {
                        // Query per inserire i dettagli dell'ordine nella tabella `dettagliordine`
                        String sqlinsert = "INSERT INTO dettagliordine(id_ordine, id_prodotto, quantita, prezzo_unitario) VALUES (?, ?, ?, ?)";
                        //ps = null;
                        try {
                            PreparedStatement psins = conn.prepareStatement(sqlinsert);
                            psins.setInt(1, finalIdOrdine); // ID dell'ordine
                            psins.setInt(2, p.getId_prodotto()); // ID del prodotto
                            psins.setInt(3, p.getQuantita_perordine()); // Quantità dell'ordine per il prodotto
                            psins.setDouble(4, p.getPrezzo()); // Prezzo unitario del prodotto
                            psins.executeUpdate(); // Esegue la query
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("Prodotto " + p.getId_prodotto() + " " + p.getNome() +
                                " dal cliente: " + ordine.getCliente().getId_cliente() + " " + ordine.getCliente().getNome() +" "+ordine.getCliente().getCognome()+
                                " modificato nel DB!");

                        int idp = p.getId_prodotto();
                        try {
                            // Query per aggiornare la quantità disponibile del prodotto
                            String sqlupdate = "UPDATE gestordini.prodotti SET quantita_disponibile = ? WHERE id_prodotto = ?;";
                            PreparedStatement psupd = conn.prepareStatement(sqlupdate);

                            // Calcola la nuova quantità disponibile
                            int q = p.getQuantita_disponibile() - p.getQuantita_perordine();
                            psupd.setInt(1, q); // Imposta la nuova quantità disponibile
                            psupd.setInt(2, idp); // ID del prodotto da aggiornare

                            psupd.executeUpdate(); // Esegue la query di aggiornamento
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    // Se la quantità disponibile non è sufficiente
                    else {
                        throw new NonAbbastanzaQuantException("Non ci sono abbastanza quantità disponibili per effettuare l'ordine!");
                    }
                }
                // Se il prodotto è null
                else if (p == null) {
                    throw new ProdottoNonTrovateException("Prodotto non è trovato");
                }
            });

            System.out.println("*******************************************************");


        }


    }




    public void stampaOrdine(int idOrdine) throws OrdineNonTrovatoException, SQLException {
        // Query SQL per ottenere tutte le informazioni relative a un ordine specifico
        String sql = "SELECT * FROM gestordini.ordini AS o " +
                "INNER JOIN gestordini.dettagliordine AS d ON o.id_ordine = d.id_ordine " +
                "INNER JOIN gestordini.clienti AS c ON c.id_cliente = o.id_cliente " +
                "INNER JOIN gestordini.prodotti AS p ON p.id_prodotto = d.id_prodotto " +
                "WHERE o.id_ordine = ?;";

        // Prepara la query con il parametro idOrdine
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idOrdine); // Inserisce il valore dell'id dell'ordine nel parametro
        ResultSet rs = ps.executeQuery();

        // Flag per verificare se l'ordine è stato trovato
        boolean isOrderFound = false;

        // Ciclo per leggere tutte le righe del risultato della query
        while (rs.next()) {
            // Se troviamo dati per il primo ordine, impostiamo il flag
            isOrderFound = true;

            // Informazioni relative al cliente
            int idCliente = rs.getInt("id_cliente");
            String nomeCliente = rs.getString("nome_cliente");
            String cognome = rs.getString("cognome");
            String email = rs.getString("email");
            String telefono = rs.getString("telefono");

            // Informazioni generali dell'ordine
            int idOrdineDB = rs.getInt("id_ordine");
            String dataOrdine = rs.getString("data_ordine");

            // Informazioni sui prodotti
            int idProdotto = rs.getInt("id_prodotto");
            String nomeProdotto = rs.getString("nome_prodotto");
            int quantita = rs.getInt("quantita");
            double prezzoUnitario = rs.getDouble("prezzo_unitario");
            double totaleProdotto = prezzoUnitario * quantita;

            // Stampare informazioni del cliente e ordine solo una volta (prima riga)
            if (rs.isFirst()) {
                System.out.println("*********************************");
                System.out.println("ID Ordine: " + idOrdineDB);
                System.out.println("Data Ordine: " + dataOrdine);
                System.out.println("ID Cliente: " + idCliente);
                System.out.println("Nome Cliente: " + nomeCliente + " " + cognome);
                System.out.println("Email Cliente: " + email);
                System.out.println("Telefono Cliente: " + telefono);
                System.out.println("*********************************");
            }

            // Stampare informazioni per ogni prodotto nell'ordine
            System.out.println("ID Prodotto: " + idProdotto);
            System.out.println("Nome Prodotto: " + nomeProdotto);
            System.out.println("Quantità: " + quantita);
            System.out.println("Prezzo Unitario: " + prezzoUnitario + "€");
            System.out.println("Totale Prodotto: " + totaleProdotto + "€");
            System.out.println("*********************************");
        }

        // Se non ci sono righe nel ResultSet, genera un'eccezione
        if (!isOrderFound) {
            throw new OrdineNonTrovatoException("L'ordine con ID " + idOrdine + " non è stato trovato.");
        }
    }

    public void stampaOrdineConUnoProdotto(int idOrdine) throws OrdineNonTrovatoException, SQLException {

//        SELECT * FROM gestordini.ordini  AS o
//        INNER JOIN gestordini.dettagliordine AS d ON o.id_ordine = d.id_ordine
//        INNER JOIN gestordini.clienti AS c ON c.id_cliente = o.id_cliente
//        INNER JOIN gestordini.prodotti as p ON p.id_prodotto=d.id_prodotto
//        WHERE o.id_ordine = 2;
        String sql ="SELECT * FROM gestordini.ordini  AS o " +
                "INNER JOIN gestordini.dettagliordine AS d ON o.id_ordine = d.id_ordine " +
                "INNER JOIN gestordini.clienti AS c ON c.id_cliente = o.id_cliente " +
                "INNER JOIN gestordini.prodotti as p ON p.id_prodotto=d.id_prodotto" +
                " WHERE o.id_ordine = ? ;";
        //System.out.println(sql);
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idOrdine);
        ResultSet rs=ps.executeQuery();
        ResultSetMetaData metaData = rs.getMetaData();

        int columnCount = metaData.getColumnCount();
        while (rs.next()) {
            //for (int i = 1; i <= columnCount; i++) {
            //System.out.println("*********************************");
            //System.out.print(metaData.getColumnName(i) + ": " + rs.getString(i) + "  ");
            //int idOrdine = rs.getInt(1); // Первая колонка
            int idCliente = rs.getInt("id_cliente"); // Вторая колонка
            String date= rs.getString("data_ordine");
            int quantita = rs.getInt("quantita");
            String nomecliente = rs.getString("nome_cliente");
            String cognome = rs.getString("cognome");
            double prezzo = rs.getDouble("prezzo_unitario");
            String nomeprodotto = rs.getString("nome_prodotto");
            String email = rs.getString("email");
            String telcliente = rs.getString("telefono");
            int idProdotto = rs.getInt("id_prodotto");
            double totale=prezzo*quantita;
            System.out.println("ID Ordine: " + idOrdine);
            System.out.println("Data Ordine: " + date);
            System.out.println("ID Cliente: " + idCliente);
            System.out.println("Nome Cliente: " + nomecliente+" "+cognome);

            System.out.println("ID Prodotto: " + idProdotto);
            System.out.println("Nome Prodotto: " + nomeprodotto);
            System.out.println("Quantità: " + quantita);
            System.out.println("Prezzo: " + prezzo + "€");
            System.out.println("Totale da faturare: " + totale);






            System.out.println("*********************************");
        }
    }

    public Ordine getOrdineById(int id) throws SQLException {


        String sql ="SELECT * FROM gestordini.ordini  AS o " +
                "INNER JOIN gestordini.dettagliordine AS d ON o.id_ordine = d.id_ordine " +
                "INNER JOIN gestordini.clienti AS c ON c.id_cliente = o.id_cliente " +
                "INNER JOIN gestordini.prodotti as p ON p.id_prodotto=d.id_prodotto" +
                " where o.id_ordine = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);


        ResultSet rs = ps.executeQuery();

        Ordine ordine = new Ordine();
        List<Prodotto> listprod=new ArrayList<>();
        while (rs.next()) {

            int idOrdine = rs.getInt("id_ordine");
            ordine.setId_ordine(rs.getInt("id_ordine"));
            int idCliente = rs.getInt("id_cliente");
            ordine.setCliente(this.findclient(idCliente));
            Timestamp date= rs.getTimestamp("data_ordine");
            ordine.setData_ordine(date.toLocalDateTime());

        Prodotto prod = new Prodotto();
//            for (int i = 1; i <= columnCount; i++) {
//
//                System.out.print(i+" "+metaData.getColumnName(i) + ": " + rs.getString(i) + " \n");}
//            System.out.println("*********************************");

            prod=this.findProdotto(rs.getInt("id_prodotto"));
            int quantita = rs.getInt("quantita");
            prod.setQuantita_perordine(quantita);
            double prezzo = rs.getDouble("prezzo_unitario");
            prod.setPrezzo(prezzo);
            //double totaleperprod=prezzo*quantita;
            listprod.add(prod);









        }
//        while(rs.next()) {
//            rs=toString();
//            //System.out.println("Ordine ID " + rs.getString("id_ordine")+" ID prodotto:"+rs.getString("id_prodotto")+rs.getString("prodotto.nome")+" Quantita;"+rs.getString("quantita")+" Prezzo a pz:"+rs.getString("prezzo_unitario")+" Cliente:"+rs.getString("nome")+" "+rs.getString("Cognome"));
//            }
        ordine.setListaProdotti(listprod);
        return ordine;
    }


    public void listaOrdine() throws SQLException {


    String sql ="SELECT id_ordine FROM gestordini.ordini;";
        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet rs=ps.executeQuery(sql);


        while (rs.next()) {
            Ordine ordine = this.getOrdineById(rs.getInt("id_ordine"));
            ordine.stampa();

        }

    }




    public void listaprodotti() throws SQLException {
        String sql="Select * from prodotti;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs=ps.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();

        int columnCount = metaData.getColumnCount();
        while (rs.next()) {

            System.out.println("*********************************");
            int id = rs.getInt("id_prodotto"); // Первая колонка
            String nomeProdotto = rs.getString("nome_prodotto"); // Вторая колонка
            Double prezzo= rs.getDouble("prezzo");
            int quantitaDisponibile = rs.getInt("quantita_disponibile");
            System.out.println("ID: " +id);
            System.out.println("Nome: " +nomeProdotto);
            System.out.println("Prezzo: " +prezzo);
            System.out.println("Quantita: " +quantitaDisponibile);




            System.out.println("*********************************");
        }
    }

    public void removeOrdine(int idOrdine) throws SQLException {
        String sql="DELETE from dettagliordine WHERE id_ordine=?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idOrdine);
        ps.executeUpdate();
         sql="DELETE from ordini WHERE id_ordine=?;";
         ps = conn.prepareStatement(sql);
        ps.setInt(1, idOrdine);
        ps.executeUpdate();
        System.out.println("Ordine: " + idOrdine+ " eliminato");
    }
    public void stampaTuttiClienti() throws SQLException {
        String sql="SELECT * FROM clienti";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery(sql);
        Cliente u = null;
        while(rs.next()) {
            int id_cliente = rs.getInt("id_cliente");
            String firstname = rs.getString("nome_cliente");
            String lastname = rs.getString("cognome");

            String telefono = rs.getString("telefono");
            String email = rs.getString("email");
            u = new Cliente(id_cliente, firstname, lastname, telefono, email);
            System.out.println(u);
        }


    }



}
