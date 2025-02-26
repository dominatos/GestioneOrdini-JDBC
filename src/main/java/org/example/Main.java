package org.example;

import com.github.javafaker.Faker;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Random;

/**
 * Hello world!
 *
 */
public class Main 
{
    private static Faker faker = new Faker(new Locale("it-IT"));
    private static DBConnection db;
    public static void main( String[] args ) {
        try {
            db=new DBConnection();
           //generateDB(100);


            //generateOrders(10);

            db.listaOrdine();
//db.listaprodotti();
            //db.modificaOrdine(90,2,8,4.99);
            //db.listaOrdine();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





    public static void generateOrders(int n) throws SQLException {
        for(int i=1;i<=n;i++){
            int randomclient=new Random().nextInt(100);
            int randomprodotto=new Random().nextInt(100);
            Prodotto test=db.findProdotto(randomprodotto);
            Cliente test2=db.findclient(randomclient);
            int randomprodquant=new Random().nextInt(10);
            db.effetuareOrdine(test,test2,randomprodquant);
        }
    }
    public static void generateDB(int n) throws SQLException {

        for(int i=1;i<=n;i++){

            String nome = faker.name().firstName().replace("\"", "'");
            String cognome = faker.name().lastName().replace("\"", "'");
            String telefono = faker.phoneNumber().cellPhone();


            String email = faker.internet().emailAddress().replace("\"", "'");

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
            System.out.println("Prodotto "+u.getNome()+" e creato. Quantita: "+quantita+" e prezzo: "+prezzo+" id: "+u.getId_prodotto());
        }

    }
}
