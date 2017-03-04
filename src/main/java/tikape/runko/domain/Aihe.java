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
public class Aihe {

    private int id;
    private String aiheenNimi;
    private String kirjoittaja;
    private int viesteja;
    private String viimeisinViesti;

    public Aihe(int id, String aiheenNimi, String kirjoittaja) {
        this.id = id;
        this.aiheenNimi = aiheenNimi;
        this.kirjoittaja = kirjoittaja;
        
        this.viesteja = 0;
        this.viimeisinViesti = "";
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public void setAiheenNimi(String aiheenNimi) {
        this.aiheenNimi = aiheenNimi;
    }

    public void setKirjoittaja(String kirjoittaja) {
        this.kirjoittaja = kirjoittaja;
    }

    public int getId() {
        return id;
    }

    public String getAiheenNimi() {
        return aiheenNimi;
    }

    public String getKirjoittaja() {
        return kirjoittaja;
    }
    
    public void setViesteja(int i) {
        this.viesteja = i;
    }
    
    public void setViimeisinViesti (String pvm) {
        this.viimeisinViesti = pvm;
    }
    
    public int getViesteja() {
        return this.viesteja;
    }
    
    public String getViimeisinViesti() {
        return this.viimeisinViesti;
    }
    
    

}
