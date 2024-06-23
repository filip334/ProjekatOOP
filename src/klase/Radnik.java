package klase;

import evidencija.funkcije.EvidencijaZaposlenog;
import exceptions.ZaposleniNePostojiException;
import java.io.FileReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import evidencija.funkcije.FunkcijeZaposlenog;

public class Radnik extends Zaposleni implements FunkcijeZaposlenog {
    int brojRadnika;

    
    String pozicija;
    double satnica;
    ArrayList<EvidencijaZaposlenog> evidencijaRadnika;

    public Radnik(String ime, String prezime, String jmbg, int brojRadnika, double satnica, String pozicija, ArrayList<EvidencijaZaposlenog> evidencijaRadnika) {
        super(ime, prezime, jmbg);
        this.brojRadnika = brojRadnika;
        this.pozicija = pozicija;
        this.satnica = satnica;
        this.evidencijaRadnika = evidencijaRadnika;
    }
   
    public Radnik(String ime, String prezime,  String jmbg, int brojRadnika, double satnica, String pozicija) {
        super(ime, prezime, jmbg);
        this.brojRadnika = brojRadnika;
        this.pozicija = pozicija;
        this.satnica = satnica;
        evidencijaRadnika = new ArrayList<>();
    }

    public Radnik(String ime, String prezime, String jmbg,int brojRadnika, double satnica) {
        super(ime, prezime, jmbg);
        this.brojRadnika = brojRadnika;
        this.satnica = satnica;
        this.pozicija = "Izvrsilac radova";
    }

    @Override
    public String toString() {
        return "Radnik{"+ super.toString() +" brojRadnika=" + brojRadnika + ", pozicija=" + pozicija + ", satnica=" + satnica + '}';
        
    }
    
    public String prikaziEvidenciju(){
        String evidencija = "";
        if (evidencijaRadnika != null && evidencijaRadnika.size() > 0) {
            for (EvidencijaZaposlenog evi : this.evidencijaRadnika) {
                evidencija += evi;
            }
            return evidencija;
        }
        
        return "Nema evidencija";
    }
    public void status(){
        double plata = 0;
        System.out.println("--STATUS RADNIKA :: " + this.ime + "--");
        System.out.println("=============================================");
        if (evidencijaRadnika != null && evidencijaRadnika.size() > 0) {
            for (EvidencijaZaposlenog evi : evidencijaRadnika) {
                LocalTime dolazak = evi.getVremeDolaska();
                LocalTime odlazak = evi.getVremeOdlaska();
                double vreme = Duration.between(dolazak, odlazak).getSeconds() / 3600;
                System.out.println("Datum :: " + evi.getDatum().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")) + " // Ukupno sati ::  " + vreme);

                double vrednostDana = vreme * satnica;
                plata += vrednostDana;
            }
            System.out.println("=============================================");
            System.out.println("Trenutno zaradjen novac : " + plata);
        
        }
        else{
            System.out.println("Radnik nema nikakvih evidencija");
        }
        
    }
    
    public boolean proveriPoslednjuEvidenciju(){
        //System.out.println(evidencijaRadnika.get(evidencijaRadnika.size() - 1).getVremeOdlaska() + "  " + evidencijaRadnika.get(evidencijaRadnika.size() - 1).getVremeDolaska());
        if(evidencijaRadnika!= null && evidencijaRadnika.size() > 0){
            if(evidencijaRadnika.get(evidencijaRadnika.size() - 1).getVremeOdlaska() == null)
                return false;
        }
        return true;
    }
    
    public double trenutnaPlata(){
        //DODATI VECU PRECIZNOST PRI RACUNANJU PLATE U SLUCAJU ULASKA NA PAR SEKUNDI NEMA NIKAKVIH REZULTATA
        double plata =0;
        if(evidencijaRadnika != null && evidencijaRadnika.size() > 0){
            for (EvidencijaZaposlenog evi : evidencijaRadnika) {
                LocalTime dolazak = evi.getVremeDolaska();
                LocalTime odlazak = evi.getVremeOdlaska();
                double vreme = Duration.between(dolazak, odlazak).getSeconds() / 3600;
                double vrednostDana = vreme * satnica;
                plata += vrednostDana;
            }
        }
        return plata;
    }
    
    @Override
    public void dolazak() {
        if(evidencijaRadnika == null){
            evidencijaRadnika = new ArrayList<EvidencijaZaposlenog>();
        }
        EvidencijaZaposlenog noviDolazak = new EvidencijaZaposlenog(LocalTime.now(),null,LocalDateTime.now());
        evidencijaRadnika.add(noviDolazak);
    }

    @Override
    public void odlazak() {
        if (!evidencijaRadnika.isEmpty()) {
            EvidencijaZaposlenog zadnjiUnos = evidencijaRadnika.get(evidencijaRadnika.size() - 1);
            if (zadnjiUnos.getVremeOdlaska() == null) {
                zadnjiUnos.setVremeOdlaska(LocalTime.now());
            }
        }
    }
}
