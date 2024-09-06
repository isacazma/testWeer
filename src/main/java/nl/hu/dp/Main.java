package nl.hu.dp;

import nl.hu.dp.p2.Reiziger;
import nl.hu.dp.p2.ReizigerDAO;
import nl.hu.dp.p2.ReizigerDaoPostgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {

    private static Connection connection;
    public static void main(String[] args) throws SQLException {
        getConnection();

        testReizigerDAO(new ReizigerDaoPostgres(connection));

        connection.close();


    }
    private static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/ovchip", "postgres", "kayadibi");
        }
        return connection;
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println("\n maak nieuwe reiziger \n ");

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(8, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        Reiziger sietska = new Reiziger(8, "Z", "", "Beers", java.sql.Date.valueOf(gbdatum));

        rdao.update(sietska);

        reizigers = rdao.findAll();
        System.out.println("\n [Test] ReizigerDAO.update 8 geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        rdao.delete(sietska);

        reizigers = rdao.findAll();
        System.out.println("\n [Test] na delet reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

        System.out.println("\n"+rdao.findById(2));
    }
}
