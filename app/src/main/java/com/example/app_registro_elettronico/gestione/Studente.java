package com.example.app_registro_elettronico.gestione;

import java.io.Serializable;
import java.util.*;

public class Studente extends Persona implements Serializable {
    private ArrayList<Voti> voti;
    private ArrayList<Note> note;
    private ArrayList<Assenza> assenze;
    private Classe classe;

    /**
     * Funzione che serve per creare un nuovo studente.
     *
     * @param nome Nome studente.
     * @param congome Cognome studente.
     * @param dataDiNascita Data di nascita studente.
     * @param CF Codice fiscale studente.
     * @param classe Classe studente.
     */
    public Studente(String nome, String congome, Date dataDiNascita, String CF, Classe classe) {
        super(nome, congome, dataDiNascita, CF);
        this.classe = classe;
        this.voti = new ArrayList<>();
        this.note = new ArrayList<>();
        this.assenze = new ArrayList<>();
    }

    /**
     * Funzione che restituisce la lista dei voti dello studente.
     *
     * @return Nome studente.
     */
    public ArrayList<Voti> getVoti() {
        return voti;
    }

    /**
     * Funzione che restituisce i voti dello studente in formato stringa.
     *
     * @return Voti come stringa.
     */
    public String getStringVoti(){
        String s = "";
        for (Voti v: voti) {
            s += v.toString() + ";";
        }
        return s;
    }

    /**
     * Funzione che restituisce le note dello studente in formato stringa.
     *
     * @return Note come stringa.
     */
    public String getStringNote(){
        String s = "";
        for (Note n: note) {
            s += n.toString() + ";";
        }
        return s;
    }

    /**
     * Funzione che restituisce le assenze dello studente in formato stringa.
     *
     * @return Assenze come stringa.
     */
    public String getStringAssenze(){
        String s = "";
        for (Assenza a: assenze) {
            s += a.toString() + ";";
        }
        return s;
    }

    /**
     * Funzione che imposta i voti dello studente in una lista.
     * @param voti Voti dello studente da impostare nella lista.
     */
    public void setVoti(ArrayList<Voti> voti) {
        this.voti = voti;
    }

    /**
     * Funzione che imposta le note dello studente in una lista.
     * @param note Note dello studente da impostare nella lista.
     */
    public void setNote(ArrayList<Note> note) {
        this.note = note;
    }

    /**
     * Funzione che imposta le assenze dello studente in una lista.
     * @param assenze Assenze dello studente da impostare nella lista.
     */
    public void setAssenze(ArrayList<Assenza> assenze) {
        this.assenze = assenze;
    }

    /**
     * Funzione che restituisce la lista delle note dello studente.
     *
     * @return Note studente.
     */
    public ArrayList<Note> getNote() {
        return note;
    }

    /**
     * Funzione che restituisce la lista delle assenze dello studente.
     *
     * @return Assenze studente.
     */
    public ArrayList<Assenza> getAssenze() {
        return assenze;
    }

    /**
     * Funzione che restituisce la classe a cui appartiene lo studente.
     *
     * @return Classe dello studente.
     */
    public Classe getClasse() {
        return classe;
    }

    /**
     * Funzione che imposta la classe allo studente.
     * @param classe Classe da impostare allo studente.
     */
    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    /**
     * funzione per ritornare la media
     * @return la media dei voti
     */
    public double getmedia(){
        double media = 0;
        for (Voti v: voti) {
            media += v.getvoto();
        }
        if(voti.isEmpty()){
            return 0;
        }
        else{
            return media/voti.size();
        }
    }

}
