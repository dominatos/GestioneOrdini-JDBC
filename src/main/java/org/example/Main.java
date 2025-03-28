package org.example;

import com.github.javafaker.Faker;
import org.example.DAO.ClienteDAO;
import org.example.DAO.DettagliordineDAO;
import org.example.DAO.OrdineDAO;
import org.example.DAO.ProdottoDAO;
import org.example.entities.Cliente;
import org.example.entities.Dettagliordine;
import org.example.entities.Ordine;
import org.example.entities.Prodotto;
import org.example.exceptions.ClienteNonTrovateException;
import org.example.exceptions.ProdottoNonTrovateException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Hello world!
 *
 */
public class Main 
{
    private static Faker faker = new Faker(new Locale("it-IT"));
    private static DBinizialize db;

    static {
        try {
            db = new DBinizialize(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Scanner sc=new Scanner(System.in);
    private static EntityManagerFactory emf =Persistence.createEntityManagerFactory("projectJPA");
    private static EntityManager em = emf.createEntityManager();
    private static ClienteDAO clienteDAO = new ClienteDAO();
    private static DettagliordineDAO dettagliordineDAO = new DettagliordineDAO();
    public static OrdineDAO ordineDAO = new OrdineDAO();
    public static ProdottoDAO prodottoDAO = new ProdottoDAO();
    public static void main( String[] args ) throws SQLException {


        generateDB(100);
        generateOrders(5);



        ordineDAO.getAllOrdine().forEach(Ordine::stampa);
       //ordineDAO.removeOrdine(ordineDAO.getOrdineNodata(1));

    }






    public static void generateOrders(int n){
        // Conta il numero totale di clienti nel database
        int clientCount = clienteDAO.countAllCliente();
        // Se non ci sono clienti, genera un'eccezione
        if (clientCount == 0) throw new ClienteNonTrovateException("Non ci sono clienti disponibili.");

        // Conta il numero totale di prodotti nel database
        int productCount = prodottoDAO.countAllProdotti();
        if (productCount == 0) throw new ProdottoNonTrovateException("Non ci sono clienti disponibili.");
//        // Se non ci sono prodotti, genera un'eccezione
//        if (productCount == 0) throw new SQLException("Non ci sono prodotti disponibili.");

        // Ciclo principale per generare "n" ordini
        for (int i = 1; i <= n; i++) {
            // Seleziona un cliente casuale utilizzando un ID casuale

            Cliente randomClient = clienteDAO.getCliente(new Random().nextInt(clientCount) + 1);

            // Genera un numero casuale di prodotti per l'ordine (minimo 1, massimo 3)
            int randProdN = new Random().nextInt(3) + 1;
            // Lista per salvare i prodotti dell'ordine
            List<Prodotto> listProdotti = new ArrayList<>();
            Ordine ordine = new Ordine();

            ordine.setCliente(randomClient);
            // Crea l'ordine utilizzando il cliente e la lista di prodotti
            ordine.setData_ordine( LocalDateTime.now());
            ordineDAO.saveOrdine(ordine);
            // Ciclo per selezionare "randProdN" prodotti casuali
            for (int j = 0; j < randProdN; j++) {
                Dettagliordine dettagliordine = new Dettagliordine();
                // Trova un prodotto casuale utilizzando un ID casuale
                Prodotto randomProdotto = prodottoDAO.getProdotto(new Random().nextInt(productCount) + 1);

                // Ottieni la quantità disponibile del prodotto selezionato
                int availableQuantity = randomProdotto.getQuantita_disponibile();
                // Se la quantità è zero, salta al prossimo prodotto
                if (availableQuantity == 0) continue;
                int randomProdQuant;
                if (availableQuantity > 0) {
                    // Генерация случайного количества, начиная с 1
                    randomProdQuant = 1 + new Random().nextInt(availableQuantity);
                } else {
                    // Логика для обработки случая, когда нет доступного количества
                    throw new IllegalArgumentException("non e abbastanza prodotto quantita disponibile.");
                }

                randomProdotto.setQuantita_perordine(randomProdQuant);

                // Aggiungi il prodotto alla lista dei prodotti dell'ordine

                dettagliordine.setProdotto(randomProdotto);
                dettagliordine.setPrezzo_unitario(randomProdotto.getPrezzo());
                dettagliordine.setQuantita(randomProdotto.getQuantita_perordine());
                dettagliordine.setOrdine(ordine);
                dettagliordineDAO.saveDettagliordine(dettagliordine);
                listProdotti.add(randomProdotto);
                ordine.setListaProdotti(listProdotti);



            }

            // Controlla se la lista dei prodotti è vuota
            if (listProdotti.isEmpty()) {
                Dettagliordine dettagliordine = new Dettagliordine();
                Prodotto fallbackProdotto = null;
                int retries = 0;

                // Trova un prodotto disponibile utilizzando tentativi
                while (fallbackProdotto == null || fallbackProdotto.getQuantita_disponibile() == 0) {
                    fallbackProdotto = prodottoDAO.getProdotto(new Random().nextInt(productCount) + 1);
                    retries++;
                    // Evita un ciclo infinito se tutti i prodotti non sono disponibili
                    if (retries > productCount) throw new ProdottoNonTrovateException("Non ci sono prodotti disponibili da aggiungere all'ordine.");
                }

                // Imposta una quantità minima (1) per il prodotto di fallback
                fallbackProdotto.setQuantita_perordine(1);
                // Aggiungi il prodotto di fallback alla lista


                dettagliordine.setProdotto(fallbackProdotto);
                dettagliordine.setPrezzo_unitario(fallbackProdotto.getPrezzo());
                dettagliordine.setQuantita(fallbackProdotto.getQuantita_perordine());
                dettagliordine.setOrdine(ordine);
                ordine.setListaProdotti(listProdotti);










                dettagliordineDAO.saveDettagliordine(dettagliordine);


            }

            //db.effetuareOrdineListProdottiQuantitaRandom(listProdotti, randomClient);
        }
    }
    public static void generateDB(int n) {

        for(int i=1;i<=n;i++){

            String nome = faker.name().firstName().replace("\"", "'");
            String cognome = faker.name().lastName().replace("\"", "'");
            String telefono = faker.phoneNumber().cellPhone();

        int addX=new Random().nextInt(100);
            String email = faker.internet().emailAddress().replace("\"", "'")+addX;

            Cliente u=new Cliente(nome,cognome,email,telefono);
            clienteDAO.saveCliente(u);
            System.out.println(u.getNome_cliente()+u.getCognome()+" e creato.");

        }
        for(int i=1;i<=n;i++){

            String nome = faker.name().firstName()+faker.beer().name().replace("\"", "'");
           String descrizione=faker.book().title();
            double prezzo= faker.random().nextDouble()*10;
           int quantita =faker.number().numberBetween(50,2000);





            Prodotto u=new Prodotto(nome,descrizione, prezzo, quantita);
            prodottoDAO.saveProdotto(u);
            System.out.println("Prodotto "+u.getNome()+" e creato. Quantita: "+quantita+" e prezzo: "+prezzo);
        }

    }
}
