package klase;

import evidencija.funkcije.EvidencijaZaposlenog;
import exceptions.ZaposleniNePostojiException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Todorovic
 */
public abstract class Zaposleni {
    String ime,prezime,jmbg;

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getJmbg() {
        return jmbg;
    }

    public Zaposleni(String ime, String prezime, String jmbg) {
        this.ime = ime;
        this.prezime = prezime;
        this.jmbg = jmbg;
    }
    @Override
    public String toString() {
        return "Osoba{" + "ime=" + ime + ", prezime=" + prezime + ", jmbg=" + jmbg + '}';
    }
    public static ArrayList<Zaposleni> procitajJsonOsobe(String fajlIme) {
        ArrayList<Zaposleni> osobe = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try {
            // Parsiranje JSON fajla
            JSONArray osobeArray = (JSONArray) parser.parse(new FileReader(fajlIme));

            for (Object obj : osobeArray) {
                JSONObject osobaObject = (JSONObject) obj;

                String ime = osobaObject.get("ime").toString();
                String prezime = osobaObject.get("prezime").toString();
                String jmbg = osobaObject.get("jmbg").toString();

                if (osobaObject.containsKey("brojRadnika")) {
                    String pozicija = osobaObject.get("pozicija").toString();
                    int brojRadnika = Integer.parseInt(osobaObject.get("brojRadnika").toString());
                    double satnica = Double.parseDouble(osobaObject.get("satnica").toString());
                    ArrayList<EvidencijaZaposlenog> evidencijaRadnika = null;
                    if(osobaObject.containsKey("evidencijaRadnika")){
                        JSONArray evidencije = (JSONArray) osobaObject.get("evidencijaRadnika");
                        evidencijaRadnika = new ArrayList<EvidencijaZaposlenog>();
                        if (evidencije != null)
                            for (Object oEvi : evidencije) {
                                JSONObject evidencija = (JSONObject) oEvi;
                                LocalTime vremeDolaska = LocalTime.parse(evidencija.get("vremeDolaska").toString());
                                LocalTime vremeOdlaska = null;
                                if (evidencija.containsKey("vremeOdlaska")) {
                                    vremeOdlaska = LocalTime.parse(evidencija.get("vremeOdlaska").toString());
                                }
                                LocalDateTime datum = LocalDateTime.parse(evidencija.get("datum").toString());
                                evidencijaRadnika.add(new EvidencijaZaposlenog(vremeDolaska, vremeOdlaska, datum));
                            }
                    }
                    
                    Radnik radnik = new Radnik(ime, prezime, jmbg, brojRadnika, satnica, pozicija, evidencijaRadnika);
                    osobe.add(radnik);
                } 
                else if (osobaObject.containsKey("brojSefa")) {
                    int brojSefa = Integer.parseInt(osobaObject.get("brojSefa").toString());
                    Sef sef = new Sef(ime, prezime,jmbg, brojSefa);
                    osobe.add(sef);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return osobe;
    }
    public static void upisiOsobe(ArrayList<Zaposleni> osobe, String naziv){
        JSONArray jsonOsobe=new JSONArray(); // [ ]
        PrintWriter pw = null;
        for (Zaposleni osoba : osobe) {
            JSONObject obj = new JSONObject(); // { }
            obj.put("ime", osoba.ime);
            obj.put("prezime", osoba.prezime);
            obj.put("jmbg", osoba.jmbg);
            if(osoba instanceof Radnik){
                Radnik r = (Radnik) osoba;
                obj.put("brojRadnika", r.brojRadnika);
                obj.put("satnica", r.satnica);
                obj.put("pozicija", r.pozicija);
                JSONArray evidencija = new JSONArray();
                if(r.evidencijaRadnika != null && r.evidencijaRadnika.size() > 0){
                    for (EvidencijaZaposlenog evi : r.evidencijaRadnika) {
                        JSONObject objEvi = new JSONObject();
                        objEvi.put("vremeDolaska", evi.getVremeDolaska().toString());
                        if (evi.getVremeOdlaska() != null) {
                            objEvi.put("vremeOdlaska", evi.getVremeOdlaska().toString());
                        }
                        objEvi.put("datum", evi.getDatum().toString());
                        evidencija.add(objEvi);
                    }
                    obj.put("evidencijaRadnika", evidencija);
                }
            }
            if(osoba instanceof Sef){
                Sef s = (Sef) osoba;
                obj.put("brojSefa", s.brojSefa);
            }
            jsonOsobe.add(obj); 
        }
        try {
            pw=new PrintWriter(naziv);
            pw.write(jsonOsobe.toJSONString()); // pw.write(jsonOsobe);
            
        } catch (FileNotFoundException ex) {
            System.out.println("Greska prilikom upisa!\n"+ex.getMessage());
        }
        finally{
            if(pw != null)
                pw.close();
        }
    }

    public static Zaposleni pronadjiOsobu(int sifra,ArrayList<Zaposleni> lista) throws ZaposleniNePostojiException{
        for(Zaposleni o : lista){
            if(o instanceof Radnik){
               Radnik r = (Radnik)o;
               if(r.brojRadnika == sifra){
                   return o;
               }
            }
            else if(o instanceof Sef){
               Sef s = (Sef)o;
               if(s.brojSefa == sifra){
                   return o;
               }
            }
        }
        
        throw new ZaposleniNePostojiException("Zaposleni sa sifrom " + sifra + " ne postoji.");
    }
    
}
