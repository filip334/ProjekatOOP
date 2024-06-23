/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package klase;

import exceptions.ZaposleniNePostojiException;
import java.util.ArrayList;
import klase.Zaposleni;

/**
 *
 * @author Todorovic
 */
public class Sef extends Zaposleni {
    int brojSefa;

    public Sef(String ime, String prezime, String jmbg,int brojSefa) {
        super(ime, prezime, jmbg);
        this.brojSefa = brojSefa;
    }

    @Override
    public String toString() {
        return "Sef{" + super.toString() + " brojSefa=" + brojSefa + '}';
    }
    
}
