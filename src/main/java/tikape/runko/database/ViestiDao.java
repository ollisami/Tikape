/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Viesti;

/**
 *
 * @author laatopi
 */
public class ViestiDao implements Dao<Viesti, Integer> {

    private Database database;

    public ViestiDao(Database database) {
        this.database = database;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        int id = rs.getInt("id");
        String sisalto = rs.getString("sisalto");
        String kirjoittaja = rs.getString("kirjoittaja");
        String aika = rs.getString("aika");
        int aiheId = rs.getInt("keskustelu_id");

        Viesti v = new Viesti(id, sisalto, kirjoittaja, aika, aiheId);
        rs.close();
        stmt.close();
        connection.close();

        return v;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti");

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String sisalto = rs.getString("sisalto");
            String kirjoittaja = rs.getString("kirjoittaja");
            String aika = rs.getString("aika");
            int aiheId = rs.getInt("keskustelu_id");

            viestit.add(new Viesti(id, sisalto, kirjoittaja, aika, aiheId));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }
    
    public List<Viesti> findAllWithKeskusteluId(int id) throws SQLException{
        List<Viesti> viestit = findAll();
        List<Viesti> a = new ArrayList<>();
        
        for (Viesti viesti : viestit) {
            if(viesti.getKeskusteluId() == id) {
                a.add(viesti);
            }
        }
        return a;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void lisaa(String viesti, String nimi, int keskusteluId) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Viesti(sisalto, kirjoittaja, keskustelu_id) "
                + "VALUES (?, ?, ?)");
        stmt.setString(1, viesti);
        stmt.setString(2, nimi);
        stmt.setInt((3), keskusteluId);
        stmt.execute();

        connection.close();
    }

}
