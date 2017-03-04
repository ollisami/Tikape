/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author laatopi
 */
public class Viesti {

    private int id;
    private String sisalto;
    private String kirjoittaja;
    private String aika;
    private int keskusteluId;

    public Viesti(int id, String sisalto, String kirjoittaja, String aika, int aiheId) {
        this.id = id;
        this.sisalto = sisalto;
        this.kirjoittaja = kirjoittaja;
        this.aika = aika;
        this.keskusteluId = aiheId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSisalto() {
        return sisalto;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }

    public String getKirjoittaja() {
        return kirjoittaja;
    }

    public void setKirjoittaja(String kirjoittaja) {
        this.kirjoittaja = kirjoittaja;
    }

    public String getAika() {
        return aika;
    }

    public void setAika(String aika) {
        this.aika = aika;
    }

    public int getKeskusteluId() {
        return keskusteluId;
    }

    public void setKeskusteluId(int keskusteluId) {
        this.keskusteluId = keskusteluId;
    }
    
    
}
