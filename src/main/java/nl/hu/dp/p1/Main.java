package nl.hu.dp.p1;

import java.sql.*;

public class Main {

    private static Connection connection;
    public static void main(String[] args) throws SQLException {
        getConnection();
        testConnection();

//        try {
//            String url = "jdbc:postgresql://localhost5433/fabriek?user=postgres&password=kayadibi";
//
//            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/ovchip", "postgres", "kayadibi");
//
//
//        }
//
//         catch (SQLException e) {
//            throw new RuntimeException(e);
//        }



    }
    private static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/ovchip", "postgres", "kayadibi");
        }
        return connection;
    }

    private static void closeConnection() throws
            SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    private static void testConnection() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM reiziger;");

        System.out.println("Alle reizigers:");
        while (resultSet.next() && resultSet != null) {
            String reizigerId =  resultSet.getString("reiziger_id") ;
            String voorletter = resultSet.getString("voorletters") ;
            String tussenvoegsel = resultSet.getString("tussenvoegsel");
            String achternaam = resultSet.getString("achternaam") ;
            String geboortedatum = resultSet.getString("geboortedatum") ;

            System.out.printf("     #%s: %s. %s%s (%s)%n", reizigerId, voorletter,
                    (tussenvoegsel != null ? tussenvoegsel + " " : ""), achternaam, geboortedatum);

        }
        connection.close();
    }



}