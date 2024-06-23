package projekatoop;

import java.time.LocalTime;
import klase.Sef;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import klase.*;
import java.util.Scanner;

public class ProjekatOOP {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random rnd = new Random();
        ArrayList<Zaposleni> osobe = new ArrayList<Zaposleni>();
        Zaposleni pronadjenaOsoba = null;
        try {
            osobe = Zaposleni.procitajJsonOsobe("C:\\Users\\Todorovic\\Documents\\Radnici1.json");
            /*for(Zaposleni o : osobe){  
                if(o instanceof Radnik){
                    Radnik r = (Radnik) o;
                    System.out.println(r);
                }
                if(o instanceof Sef){
                    Sef s = (Sef) o;
                    System.out.println(s);
                }
            }*/
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        while(true){
            System.out.println("SIFRA:: ");
            int sifra = scanner.nextInt();
            
            try{
                pronadjenaOsoba = Zaposleni.pronadjiOsobu(sifra, osobe);
                
                System.out.println("Dobrodosli " + pronadjenaOsoba.getIme());
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
                pronadjenaOsoba = null;
            }
            
            if(pronadjenaOsoba instanceof Radnik){
                Radnik r = (Radnik) pronadjenaOsoba;
                if (r.proveriPoslednjuEvidenciju()) {
                    r.dolazak();
                    osveziPodatke(osobe);
                    System.out.println("Uspesno ste prijavili dolazak na posao " + LocalTime.now());
                } else {
                    r.odlazak();
                    osveziPodatke(osobe);
                    r.status();
                    System.out.println("Uspesno ste prijavili odlazak sa posla " + LocalTime.now());
                }
            }
            else if(pronadjenaOsoba instanceof Sef){
                Sef s = (Sef) pronadjenaOsoba;
                //ODABERI OPCIJU
                System.out.println("Prijavljeni ste na sefa : " + s.getIme());
                System.out.println("Izaberite opciju :: ...");
                System.out.println("1. prikazi radnike\n2. prikazi radnike koji su na poslu\n3. prikazi zaduzenje za plate\n4.dodaj radnika\nOpcija : ");
                int opcija = scanner.nextInt();
                
                switch(opcija){
                    case 1: 
                        for(Zaposleni osoba : osobe){
                            if(osoba instanceof Radnik){
                                System.out.println(osoba);
                            }
                        }
                        break;
                    case 2: 
                        boolean ispis = false;
                        for (Zaposleni osoba : osobe) {
                            if (osoba instanceof Radnik) {
                                Radnik r = (Radnik) osoba;
                                if(!r.proveriPoslednjuEvidenciju()){
                                    System.out.println(r);
                                    ispis = true;
                                }
                            }
                        }
                        if(!ispis){
                            System.out.println("Trenutno nema aktivnih radnika");
                        }
                        break;
                    case 3: 
                        double zaduzenje = 0;
                        for (Zaposleni osoba : osobe) {
                            if (osoba instanceof Radnik) {
                                Radnik r = (Radnik) osoba;
                                zaduzenje += r.trenutnaPlata();
                            }
                        }
                        System.out.println("Trenutno zaduzenje firme za plate radnika je : " + zaduzenje);
                        break;
                    case 4: 
                        System.out.print("Unesite ime radnika : ");scanner.nextLine();String ime = scanner.nextLine();
                        System.out.print("Unesite prezime radnika : ");String prezime = scanner.nextLine();
                        System.out.print("Unesite jmbg radnika : ");String jmbg = scanner.nextLine();
                        System.out.println("Unesite satnicu radnika : ");double satnica = scanner.nextDouble();
                        int brojRadnika;
                        boolean brojPostoji = true;
                        do{
                            brojRadnika = rnd.nextInt(10000 - 1) + 1;
                            try {
                                Zaposleni z = Zaposleni.pronadjiOsobu(sifra, osobe);
                                brojPostoji = false;
                                //System.out.println("Dobrodosli " + pronadjenaOsoba.getIme());
                            } catch (Exception ex) {
                                //System.out.println(ex.getMessage());
                                brojPostoji = true;
                            }
                        }while(brojPostoji);
                        
                        Radnik r = new Radnik(ime,prezime,jmbg,brojRadnika,satnica);
                        
                        osobe.add(r);
                        osveziPodatke(osobe);
                        
                        System.out.println("Uspesno kreiran radnik : ");
                        System.out.println(r);
                        
                        break;
                    default: break;
                }
            }
        }
        
       
        
        
    }
    
    public static void osveziPodatke(ArrayList<Zaposleni> osobe){
        Zaposleni.upisiOsobe(osobe, "C:\\Users\\Todorovic\\Documents\\Radnici1.json");
        osobe = Zaposleni.procitajJsonOsobe("C:\\Users\\Todorovic\\Documents\\Radnici1.json");
    }
    
}
