soluzione al esercizio:<br><br>

Gestione di un Database Relazionale MySQL con JDBC in Java<br><br>

Creare un database se non esiste chiamato GestioneOrdini.<br>
Definire le seguenti tabelle:<br>
    Tabella Clienti:<br>
        id_cliente (INT, chiave primaria, autoincrement)<br>
        nome (VARCHAR(100), non nullo)<br>
        cognome (VARCHAR(100), non nullo)<br>
        email (VARCHAR(150), unico, non nullo)<br>
        telefono (VARCHAR(20), opzionale)<br>
    Tabella Prodotti:<br>
        id_prodotto (INT, chiave primaria, autoincrement)<br>
        nome (VARCHAR(150), non nullo)<br>
        descrizione (TEXT, opzionale)<br>
        prezzo (DECIMAL(10,2), non nullo)<br>
        quantità_disponibile (INT, non nullo)<br>
    Tabella Ordini:<br>
        id_ordine (INT, chiave primaria, autoincrement)<br>
        id_cliente (INT, chiave esterna riferita a Clienti(id))<br>
        data_ordine (TIMESTAMP, valore di default CURRENT_TIMESTAMP)<br>
    Tabella DettagliOrdine:<br>
        id_dettagli_ordini (INT, chiave primaria, autoincrement)<br>
        id_ordine (INT, chiave esterna riferita a Ordini(id))<br>
        id_prodotto (INT, chiave esterna riferita a Prodotti(id))<br>
        quantità (INT, non nullo)<br>
        prezzo_unitario (DECIMAL(10,2), non nullo)<br><br>

L’applicazione deve permettere di:<br>
Connessione al database utilizzando JDBC.<br>
Creare le tabelle se non esistono già.<br>
Gestire i clienti (CRUD).<br>
Gestire i prodotti (CRUD).<br>
Effettuare un ordine, salvando:<br>
    Il cliente che effettua l'ordine.<br>
    I prodotti acquistati con relative quantità e prezzi unitari.<br>
    Visualizzare lo storico degli ordini con dettagli sui prodotti acquistati.<br>
    Aggiornare ed eliminare ordini e prodotti.<br>
