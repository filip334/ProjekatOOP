package evidencija.funkcije;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import klase.Radnik;

public class EvidencijaZaposlenog{
    LocalTime vremeDolaska;
    LocalTime vremeOdlaska;
    LocalDateTime datum;

    
    
    public EvidencijaZaposlenog(LocalTime vremeDolaska, LocalTime vremeOdlaska, LocalDateTime datum) {
        this.vremeDolaska = vremeDolaska;
        this.vremeOdlaska = vremeOdlaska;
        this.datum = datum;
    }


    public void setVremeDolaska(LocalTime vremeDolaska) {
        this.vremeDolaska = vremeDolaska;
    }

    public void setVremeOdlaska(LocalTime vremeOdlaska) {
        this.vremeOdlaska = vremeOdlaska;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }

    public LocalTime getVremeDolaska() {
        return vremeDolaska;
    }

    public LocalTime getVremeOdlaska() {
        return vremeOdlaska;
    }

    public LocalDateTime getDatum() {
        return datum;
    }
    

    public EvidencijaZaposlenog() {
    }
    
    
    @Override
    public String toString() {
        return "evidencijaRadnika{" + "vremeDolaska=" + vremeDolaska + ", vremeOdlaska=" + vremeOdlaska + ", datum=" + datum + '}' + "\n";
    }
    
    public static void dolazak(int brojRadnika){
        //Upisi novu evidencuju u evidenvijaRadnika u klasi Radnik
    }
    
    
}
