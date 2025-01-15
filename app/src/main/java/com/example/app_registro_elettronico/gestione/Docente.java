package com.example.app_registro_elettronico.gestione;

import java.io.Serializable;
import java.util.*;

public class Docente extends Persona implements Serializable {
    private ArrayList<Classe> classi;
    private ArrayList<String> materie;

    /**
     * Funzione che serve per creare un nuovo docente.
     *
     * @param nome Nome docente.
     * @param congome Cognome docente.
     * @param dataDiNascita Data di nascita docente.
     * @param CF Codice fiscale docente.
     * @param classe Lista classi appartenenti al docente.
     * @param materie Lista materie insegnate dal docente.
     */
    public Docente(String nome, String congome, Date dataDiNascita, String CF, ArrayList<Classe> classe, ArrayList<String> materie) {
        super(nome, congome, dataDiNascita, CF);
        this.classi = classe;
        this.materie = materie;
    }

    /**
     * Funzione che restituisce la lista delle classi che appartengono al docente.
     *
     * @return Lista delle classi.
     */
    public ArrayList<Classe> getClassi() {
        return classi;
    }

    /**
     * Funzione che restituisce la lista delle materie che appartengono al docente.
     *
     * @return Lista delle materie.
     */
    public ArrayList<String> getMaterie() {
        return materie;
    }

    /**
     * Funzione che imposta la lista delle classi che appartengono al docente.
     *
     * @param classi Nuova lista delle classi.
     */
    public void setClassi(ArrayList<Classe> classi) {
        this.classi = classi;
    }

    /**
     * Funzione che imposta la lista delle materie che insegna il docente.
     *
     * @param materie Nuova lista delle classi.
     */
    public void setMaterie(ArrayList<String> materie) {
        this.materie = materie;
    }

    /**
     * Funzione che restituisce le materie insegnate dal docente in formato stringa.
     *
     * @return Stringa contenente le materie.
     */
    public String getStringMaterie(){
        String s = "";
        for (String tmp: materie) {
            s += tmp + ";";
        }
        return s;
    }

    /**
     * Funzione che restituisce le classi assegnate al docente in formato stringa.
     *
     * @return Stringa contenente le classi.
     */
    public String getStringClassi(){
        String s = "";
        for (Classe c: classi) {
            s += c.toString() + ";";
        }
        return s;
    }

}
