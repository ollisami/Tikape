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
public class Keskustelu {

    private int id;
    private String keskustelunNimi;
    private String kirjoittaja;
    private int aiheId;

    private int viesteja;
    private String viimeisinViesti;

    public Keskustelu(int id, String keskustelunNimi, String kirjoittaja, int aiheId) {
        this.id = id;
        this.keskustelunNimi = keskustelunNimi;
        this.kirjoittaja = kirjoittaja;
        this.aiheId = aiheId;

        this.viesteja = 0;
        this.viimeisinViesti = "";
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKeskustelunNimi(String keskustelunNimi) {
        this.keskustelunNimi = keskustelunNimi;
    }

    public void setKirjoittaja(String kirjoittaja) {
        this.kirjoittaja = kirjoittaja;
    }

    public void setAiheId(int aiheId) {
        this.aiheId = aiheId;
    }

    public int getId() {
        return id;
    }

    public String getKeskustelunNimi() {
        return keskustelunNimi;
    }

    public String getKirjoittaja() {
        return kirjoittaja;
    }

    public int getAiheId() {
        return aiheId;
    }

    public void setViesteja(int i) {
        this.viesteja = i;
    }

    public void setViimeisinViesti(String pvm) {
        this.viimeisinViesti = pvm;
    }

    public int getViesteja() {
        return this.viesteja;
    }

    public String getViimeisinViesti() {
        return this.viimeisinViesti;
    }

}
