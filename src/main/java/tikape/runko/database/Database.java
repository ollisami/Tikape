package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();
        for (String lause : lauseet) {

            // "try with resources" sulkee resurssin automaattisesti lopuksi
            try (Connection conn = getConnection()) {
                Statement st = conn.createStatement();

                // suoritetaan komennot
                //for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
                //}
            } catch (Throwable t) {
                // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
                System.out.println("Error >> " + t.getMessage());
            }
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

//        tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä lista
//        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Platon');");
//        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Aristoteles');");
//        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Homeros');");
        lista.add("CREATE TABLE Viesti (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, sisalto varchar(500), kirjoittaja varchar(20), aika DATETIME DEFAULT CURRENT_TIMESTAMP, keskustelu_id INTEGER, FOREIGN KEY(keskustelu_id) REFERENCES Keskustelu(id));");
        lista.add("CREATE TABLE Keskustelu (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, keskustelun_nimi varchar(255), kirjoittaja varchar(20), aihe_id integer NOT NULL, FOREIGN KEY(aihe_id) REFERENCES Aihe(id));");
        lista.add("CREATE TABLE Aihe (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, aiheen_nimi varchar(255), kirjoittaja varchar (50));");
//        lista.add("INSERT INTO Keskustelu(keskustelun_nimi, kirjoittaja, aihe_id) VALUES ('keskustelu1', 'Topi', 1);");
//        lista.add("INSERT INTO Viesti(sisalto, kirjoittaja, keskustelu_id) VALUES ('Java on haaaaaauska kieli', 'Topi', 3);");
//        lista.add("INSERT INTO Viesti(sisalto, kirjoittaja, keskustelu_id) VALUES ('Java on haaauuuuuuuaaauska kieli', 'Topi', 3);");
        //lista.add("INSERT INTO Aihe(aiheen_nimi, kirjoittaja) VALUES ('Vesiskootterit', 'Topi');");

        return lista;
    }
}
