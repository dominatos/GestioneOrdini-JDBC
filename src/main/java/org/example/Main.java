package org.example;

import com.github.javafaker.Faker;
import org.example.entities.Cliente;
import org.example.entities.Ordine;
import org.example.entities.Prodotto;

import java.sql.SQLException;
import java.util.*;

/**
 * Hello world!
 *
 */
public class Main 
{
    private static Faker faker = new Faker(new Locale("it-IT"));
    private static DBConnection db;
    private static Scanner sc=new Scanner(System.in);
    public static void main( String[] args ) {
        try {

//per modificare prodotto/ordine/cliente devi usare methodi ma non menu!
            stampaMenu();

           //generateDB(100);
            //generateOrders(1);






//            Ordine o=new Ordine();
//            o.setId_ordine(1);
//            o.setCliente(db.findclient(72));
//
//           Prodotto pr1= db.findProdotto(96);
//           pr1.setQuantita_perordine(5);
//
//
//            Prodotto pr2= db.findProdotto(97);
//            pr2.setQuantita_perordine(2);
//
//            Prodotto pr3= db.findProdotto(98);
//            pr3.setQuantita_perordine(1);
//            List<Prodotto> listaProdotti2=List.of(pr1,pr2,pr3);
//            o.setListaProdotti(listaProdotti2);
            //db.editOrdinemulti(o);
            //System.out.println(db.getOrdineById(1));

            //db.removeOrdine(2);
            //db.listaOrdine();




            //db.stampaOrdine(189);


//db.listaprodotti();
//db.removeProdotto(db.findProdotto(67));

            //System.out.println(db.findclient(68));
            //db.removeCliente(db.findclient(68));


        }
        catch (ProdottoNonTrovateException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        catch (ClienteNonTrovateException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        catch (NonAbbastanzaQuantException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        catch (OrdineNonTrovatoException e) {
            System.out.println("Errore: " + e.getMessage());
        }


        catch (SQLException e) {
            System.out.println("Errore SQL: " + e.getMessage());
        }
    }
    public static void stampaMenu() throws SQLException {
        while(true) {
            db=new DBConnection();
            System.out.println("Cosa vuoi fare?" +
                    "\n\n scrivi 1 per generare prodotti e clienti" +
                    "\n scrivi 2 per generare ordini" +
                    "\n scrivi 3 per vedere ordine" +
                    "\n scrivi 4 per vedere prodotto" +
                    "\n scrivi 5 per vedere cliente" +
                    "\n scrivi 6 per remove prodotto" +
                    "\n scrivi 7 per remove cliente" +
                    "\n scrivi 8 per remove ordine" +
                    "\n scrivi 9 per vedere tutti ordini" +
                    "\n scrivi 10 per vedere tutti prodotti" +
                    "\n scrivi 11 per vedere tutti clienti" +
                    "\n scrivi 12 per truncate DB" +
                    "\n scrivi 0 per uscita\n");
            String sinoval = sc.nextLine();
            if (sinoval.equals("1")) {
                System.out.println("Quanti prodotti e clienti vuoi aggungere?");

                String input = sc.nextLine();
                int q=0;

                if (input.matches("\\d+")) {


                    q = Integer.parseInt(input);
                    generateDB(q);

                } else {

                    System.out.println("Errore: devi insere il numero positivo e non double!");
                }



            }
            else if (sinoval.equals("2")) {
                System.out.println("Quanti ordini vuoi generare?");

                String input = sc.nextLine();
                int q=0;

                if (input.matches("\\d+")) {


                    q = Integer.parseInt(input);
                    generateOrders(q);
                } else {

                    System.out.println("Errore: devi insere il numero positivo e non double!");
                }




            }
            else if (sinoval.equals("3")) {
                System.out.println("Inserisci numero di ordine che vuoi vedere?");

                String input = sc.nextLine();
                int q=0;

                if (input.matches("\\d+")) {


                    q = Integer.parseInt(input);
                    db.getOrdineById(q).stampa();
                } else {

                    System.out.println("Errore: devi insere il numero positivo e non double!");
                }




            }
            else if (sinoval.equals("4")) {
                System.out.println("Inserisci numero di prodotto:");

                String input = sc.nextLine();
                int q=0;

                if (input.matches("\\d+")) {


                    q = Integer.parseInt(input);
                    System.out.println(db.findProdotto(q));
                } else {

                    System.out.println("Errore: devi insere il numero positivo e non double!");
                }




            }
            else if (sinoval.equals("5")) {
                System.out.println("Inserisci numero di cliente:");

                String input = sc.nextLine();
                int q=0;

                if (input.matches("\\d+")) {


                    q = Integer.parseInt(input);
                    System.out.println(db.findclient(q));
                } else {

                    System.out.println("Errore: devi insere il numero positivo e non double!");
                }




            }
            else if (sinoval.equals("6")) {
                System.out.println("Inserisci numero di prodotto:");

                String input = sc.nextLine();
                int q=0;

                if (input.matches("\\d+")) {


                    q = Integer.parseInt(input);
                    db.removeProdotto(db.findProdotto(q));
                } else {

                    System.out.println("Errore: devi insere il numero positivo e non double!");
                }




            }
            else if (sinoval.equals("7")) {
                System.out.println("Inserisci numero di cliente:");

                String input = sc.nextLine();
                int q=0;

                if (input.matches("\\d+")) {


                    q = Integer.parseInt(input);
                    db.removeCliente(db.findclient(q));
                } else {

                    System.out.println("Errore: devi insere il numero positivo e non double!");
                }




            }
            else if (sinoval.equals("8")) {
                System.out.println("Inserisci numero di ordine:");

                String input = sc.nextLine();
                int q=0;

                if (input.matches("\\d+")) {


                    q = Integer.parseInt(input);
                    db.removeOrdine(q);
                } else {

                    System.out.println("Errore: devi insere il numero positivo e non double!");
                }




            }
            else if (sinoval.equals("9")) {
                db.listaOrdine();




            }
            else if (sinoval.equals("10")) {
                db.listaprodotti();




            }
            else if (sinoval.equals("11")) {
                db.stampaTuttiClienti();




            }else if (sinoval.equals("12")) {
                db.dropDatabase();




            }


            else if (sinoval.equals("0")) {
                break;
            } else {
                System.out.println("commanda sconoscuta. Prova ancora");


            }


        }
    }





    public static void generateOrders(int n) throws SQLException {
        // Conta il numero totale di clienti nel database
        int clientCount = db.countCliente();
        // Se non ci sono clienti, genera un'eccezione
        if (clientCount == 0) throw new SQLException("Non ci sono clienti disponibili.");

        // Conta il numero totale di prodotti nel database
        int productCount = db.countProdotti();
        // Se non ci sono prodotti, genera un'eccezione
        if (productCount == 0) throw new SQLException("Non ci sono prodotti disponibili.");

        // Ciclo principale per generare "n" ordini
        for (int i = 1; i <= n; i++) {
            // Seleziona un cliente casuale utilizzando un ID casuale
            Cliente randomClient = db.findclient(new Random().nextInt(clientCount) + 1);

            // Genera un numero casuale di prodotti per l'ordine (minimo 1, massimo 10)
            int randProdN = new Random().nextInt(10) + 1;
            // Lista per salvare i prodotti dell'ordine
            List<Prodotto> listProdotti = new ArrayList<>();

            // Ciclo per selezionare "randProdN" prodotti casuali
            for (int j = 0; j < randProdN; j++) {
                // Trova un prodotto casuale utilizzando un ID casuale
                Prodotto randomProdotto = db.findProdotto(new Random().nextInt(productCount) + 1);

                // Ottieni la quantità disponibile del prodotto selezionato
                int availableQuantity = randomProdotto.getQuantita_disponibile();
                // Se la quantità è zero, salta al prossimo prodotto
                if (availableQuantity == 0) continue;

                // Genera una quantità casuale da 1 fino alla quantità disponibile
                int randomProdQuant = new Random().nextInt(availableQuantity) + 1;
                // Imposta la quantità generata sul prodotto
                randomProdotto.setQuantita_perordine(randomProdQuant);

                // Aggiungi il prodotto alla lista dei prodotti dell'ordine
                listProdotti.add(randomProdotto);
            }

            // Controlla se la lista dei prodotti è vuota
            if (listProdotti.isEmpty()) {
                Prodotto fallbackProdotto = null;
                int retries = 0;

                // Trova un prodotto disponibile utilizzando tentativi
                while (fallbackProdotto == null || fallbackProdotto.getQuantita_disponibile() == 0) {
                    fallbackProdotto = db.findProdotto(new Random().nextInt(productCount) + 1);
                    retries++;
                    // Evita un ciclo infinito se tutti i prodotti non sono disponibili
                    if (retries > productCount) throw new SQLException("Non ci sono prodotti disponibili da aggiungere all'ordine.");
                }

                // Imposta una quantità minima (1) per il prodotto di fallback
                fallbackProdotto.setQuantita_perordine(1);
                // Aggiungi il prodotto di fallback alla lista
                listProdotti.add(fallbackProdotto);
            }

            // Crea l'ordine utilizzando il cliente e la lista di prodotti
            db.effetuareOrdineListProdottiQuantitaRandom(listProdotti, randomClient);
        }
    }
    public static void generateDB(int n) throws SQLException {

        for(int i=1;i<=n;i++){

            String nome = faker.name().firstName().replace("\"", "'");
            String cognome = faker.name().lastName().replace("\"", "'");
            String telefono = faker.phoneNumber().cellPhone();

        int addX=new Random().nextInt(100);
            String email = faker.internet().emailAddress().replace("\"", "'")+addX;

            Cliente u=new Cliente(nome,cognome,email,telefono);
            db.insertCliente(u);
            System.out.println(u.getNome()+u.getCognome()+" e creato.");

        }
        for(int i=1;i<=n;i++){

            String nome = faker.name().firstName()+faker.beer().name().replace("\"", "'");
           String descrizione=faker.book().title();
           Double prezzo=faker.random().nextDouble()*10;
           int quantita =faker.number().numberBetween(50,2000);





            Prodotto u=new Prodotto(nome,descrizione, prezzo, quantita);
            db.insertProdotti(u);
            System.out.println("Prodotto "+u.getNome()+" e creato. Quantita: "+quantita+" e prezzo: "+prezzo);
        }

    }
}
