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
import tikape.runko.domain.Keskustelu;

/**
 *
 * @author laatopi
 */
public class KeskusteluDao implements Dao<Keskustelu, Integer> {

    private Database database;

    public KeskusteluDao(Database database) {
        this.database = database;
    }

    @Override
    public Keskustelu findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        int id = rs.getInt("id");
        String keskustelunNimi = rs.getString("keskustelun_nimi");
        String kirjoittaja = rs.getString("kirjoittaja");
        int aiheId = rs.getInt("aihe_id");

        Keskustelu k = new Keskustelu(id, keskustelunNimi, kirjoittaja, aiheId);
        rs.close();
        stmt.close();
        connection.close();

        return k;
    }

    @Override
    public List<Keskustelu> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu");

        ResultSet rs = stmt.executeQuery();
        List<Keskustelu> keskustelut = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String keskustelunNimi = rs.getString("keskustelun_nimi");
            String kirjoittaja = rs.getString("kirjoittaja");
            int aiheId = rs.getInt("aihe_id");

            keskustelut.add(new Keskustelu(id, keskustelunNimi, kirjoittaja, aiheId));
        }

        rs.close();
        stmt.close();
        connection.close();

        return keskustelut;
    }

    public List<Keskustelu> findAllWithAiheId(int id) throws SQLException {
        List<Keskustelu> keskustelut = findAll();
        List<Keskustelu> a = new ArrayList<>();

        for (Keskustelu keskustelu : keskustelut) {
            if (keskustelu.getAiheId() == id) {
                a.add(keskustelu);
            }
        }
        return a;

    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void lisaa(String keskustelu, String nimi, int aiheId) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Keskustelu (keskustelun_nimi, kirjoittaja, aihe_id) "
                + "VALUES (?, ?, ?)");
        stmt.setString(1, keskustelu);
        stmt.setString(2, nimi);
        stmt.setInt((3), aiheId);
        stmt.execute();

        connection.close();
    }

}
