package com.example.app_registro_elettronico.gestione;

import java.io.Serializable;
import java.util.Date;

public class Genitore extends Persona implements Serializable {
    private Studente figlio;

    /**
     * Funzione che serve per creare un nuovo genitore.
     *
     * @param nome Nome genitore.
     * @param congome Cognome genitore.
     * @param dataDiNascita Data di nascita genitore.
     * @param CF Codice fiscale genitore.
     * @param figlio Studente associato al genitore.
     */
    public Genitore(String nome, String congome, Date dataDiNascita, String CF, Studente figlio) {
        super(nome, congome, dataDiNascita, CF);
        this.figlio = figlio;
    }

    /**
     * Funzione che restituisce lo studente appartenente al genitore.
     *
     * @return Studente associato al genitore.
     */
    public Studente getFiglio() {
        return figlio;
    }

    /**
     * Funzione che imposta lo studente associato al genitore.
     *
     * @param figlio Studente da associare al genitore.
     */
    public void setFiglio(Studente figlio){
        this.figlio = figlio;
    }
    public double getmedia(){
        double media = 0;
        for (Voti v: figlio.getVoti()) {
            media += v.getvoto();
        }
        if(figlio.getVoti().isEmpty()){
            return 0;
        }
        else{
            return media/figlio.getVoti().size();
        }
    }

}
