package nl.hu.dp.p2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDaoPostgres implements ReizigerDAO {
    // Container for the connection
    private Connection connection = null;
    // Constructor that takes a connection
    public ReizigerDaoPostgres(Connection inConnection) throws SQLException {
        this.connection = inConnection;
    }

    @Override
    public boolean save(Reiziger reiziger) throws SQLException {



        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO reiziger (reiziger_id , " +
                    "voorletters," + " tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1,reiziger.getId());
            statement.setString(2, reiziger.getVoorletters());
            statement.setString(3, reiziger.getTussenvoegsel());
            statement.setString(4, reiziger.getAchternaam());
            statement.setDate(5, reiziger.getGeboortedatum());
            statement.executeUpdate();
            return true;

        } catch (SQLException throwables) {
            System.err.println("SQLException: " + throwables.getMessage());
        }

        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
//        https://stackoverflow.com/questions/40126787/update-sql-database-with-preparedstatement-in-java
        PreparedStatement statement = connection.prepareStatement("UPDATE reiziger SET  voorletters = ?, " +
                "tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?");
        try {
            statement.setString(1, reiziger.getVoorletters());
            statement.setString(2, reiziger.getTussenvoegsel());
            statement.setString(3, reiziger.getAchternaam());
            statement.setDate(4, reiziger.getGeboortedatum());
            statement.setInt(5,reiziger.getId());
            statement.executeUpdate();
            return true;

        } catch (SQLException throwables) {
            System.err.println("SQLException: " + throwables.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
//        https://stackoverflow.com/questions/9622858/preparedstatement-delete-in-java
        PreparedStatement statement = connection.prepareStatement("DELETE FROM reiziger WHERE reiziger_id =?");


        try {
            statement.setInt(1,reiziger.getId());
            statement.executeUpdate();
            return true;

        } catch (SQLException throwables) {
            System.err.println("SQLException: " + throwables.getMessage());
        }

        return false;
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM reiziger  WHERE reiziger_id =?");
        try {
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next() ) {
                int reizigerId = resultSet.getInt("reiziger_id");
                String voorletters = resultSet.getString("voorletters");
                String tussenvoegsel = resultSet.getString("tussenvoegsel");
                String achternaam = resultSet.getString("achternaam");
                java.sql.Date geboortedatum = resultSet.getDate("geboortedatum");;

              return new Reiziger(reizigerId,voorletters,tussenvoegsel,achternaam,geboortedatum);
            }

        } catch (SQLException throwables) {
            System.err.println("SQLException: " + throwables.getMessage());
        }

        return null;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        List<Reiziger> reizigers = new ArrayList<Reiziger>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM reiziger;");

        System.out.println("Alle reizigers:");
        while (resultSet.next() && resultSet != null) {
            int reizigerId =  resultSet.getInt("reiziger_id") ;
            String voorletter = resultSet.getString("voorletters") ;
            String tussenvoegsel = resultSet.getString("tussenvoegsel");
            String achternaam = resultSet.getString("achternaam") ;
            Date geboortedatum = resultSet.getDate("geboortedatum") ;

           reizigers.add(new Reiziger(reizigerId,voorletter,tussenvoegsel,achternaam,geboortedatum));

        }
        return reizigers;
    }
}