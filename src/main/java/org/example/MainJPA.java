package org.example;

import com.github.javafaker.Faker;
import org.example.entities.Cliente;
import org.example.entities.Dettagliordine;
import org.example.entities.Ordine;
import org.example.entities.Prodotto;
import org.example.entitiesDAO.ClienteDAO;
import org.example.entitiesDAO.DettagliordineDAO;
import org.example.entitiesDAO.OrdineDAO;
import org.example.entitiesDAO.ProdottoDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.Locale;


public class MainJPA {
    private static ClienteDAO clienteDao = new ClienteDAO();
    private static ProdottoDAO prodottoDao = new ProdottoDAO();
    private static OrdineDAO ordineDao = new OrdineDAO();
    private static DettagliordineDAO DettagliordineDao = new DettagliordineDAO();
    private static Faker faker = new Faker(new Locale("it-IT"));
    private static EntityManagerFactory emf= Persistence.createEntityManagerFactory("projectJPA");
    private static EntityManager em= emf.createEntityManager();
    public static void main(String[] args) throws SQLException {

//        //generateData(200);
//        Prodotto prodfind= prodottoDao.getProdotto(2);
//        //System.out.println(prodfind);
//        prodfind.setDescrizione("bla bla bla");
//
//        prodottoDao.saveProdotto(prodfind);
//        //System.out.println(prodfind);
//        //efOrders(5);
//
//
//        Cliente c= clienteDao.getCliente(2);
//       Ordine o = new Ordine(c);
//        OrdineDAO.inserOrdine(o);
//       o=ordineDao.getOrdine(1);
//        int num = faker.number().numberBetween(0, 20);
//
//        Dettagliordine d = new Dettagliordine(num, prodfind.getPrezzo(), o, prodfind);
//
//        DettagliordineDAO.inserOrdineDetaggli(d);


    }



    public static void generateData(int n) throws SQLException {

        for(int i=1;i<=n;i++){

            String nome = faker.name().firstName().replace("\"", "'");
            String cognome = faker.name().lastName().replace("\"", "'");
            String telefono = faker.phoneNumber().cellPhone();


            String email = faker.internet().emailAddress().replace("\"", "'");

            Cliente u=new Cliente(nome,cognome,email,telefono);
            clienteDao.inserClient(u);
            System.out.println(u.getNome()+u.getCognome()+" e creato.");

        }
        for(int i=1;i<=n;i++){

            String nome = faker.name().firstName()+faker.beer().name().replace("\"", "'");
            String descrizione=faker.book().title();
            Double prezzo=faker.random().nextDouble()*10;
            int quantita =faker.number().numberBetween(50,2000);





            Prodotto u=new Prodotto(nome,descrizione, prezzo, quantita);
            ProdottoDAO.inserProdotto(u);
            System.out.println("Prodotto "+u.getNome()+" e creato. Quantita: "+quantita+" e prezzo: "+prezzo+" id: "+u.getId_prodotto());
        }






    }
    public static void efOrders(int n) {
        for (int i = 1; i <= n; i++) {
            Cliente c = clienteDao.getCliente(faker.number().numberBetween(1, 20));
            Ordine o = new Ordine(c);
            ordineDao.inserOrdine(o); // Сохранить в базе
            Integer num_ordine=o.getId_ordine();
            o = ordineDao.getOrdine(num_ordine); // Убедиться, что ID установлен

            System.out.println(o);

            Prodotto p = ProdottoDAO.getProdotto(faker.number().numberBetween(1, 20));
            if (p == null) {
                System.out.println("Продукт не найден, пропускаем...");
                continue;
            }

            int num = faker.number().numberBetween(0, 20);
            //double prezzo = (p.getPrezzo() != null) ? p.getPrezzo() : 0.0; // Проверка на null
            double prezzo=p.getPrezzo();
            Dettagliordine d = new Dettagliordine(num, prezzo, o, p);

            DettagliordineDAO.inserOrdineDetaggli(d);
        }
    }

}
