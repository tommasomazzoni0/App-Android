package com.example.app_registro_elettronico.gestione;

import java.io.Serializable;
import java.util.ArrayList;

public class Classe implements Serializable {
    private final int anno;
    private final String indirizzo;
    private final char sezione;
    private final ArrayList<Studente> studenti;

    /**
     * Funzione che inzializza una nuova classe.
     *
     * @param anno Anno della classe.
     * @param indirizzo Indirizzo della classe.
     * @param sezione Sezione della classe.
     */
    public Classe(int anno, String indirizzo, char sezione) {
        this.anno = anno;
        this.indirizzo = indirizzo;
        this.sezione = sezione;
        studenti = new ArrayList<>();
    }

    /**
     * Restituisce l'anno della classe.
     *
     * @return Anno classe.
     */
    public int getAnno() {
        return anno;
    }

    /**
     * Restituisce l'indirizzo della classe.
     *
     * @return Indirizzo classe.
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Restituisce la sezione della classe.
     *
     * @return Sezione classe.
     */
    public char getSezione() {
        return sezione;
    }

    /**
     * Restituisce la lista degli studenti della classe.
     *
     * @return Lista studenti classe.
     */
    public ArrayList<Studente> getStudenti() {
        return studenti;
    }

    /**
     * Metodo che serve per restituire la classe in formato stringa.
     * Include: anno, sezione e indirizzo (tutto in minuscolo).
     *
     * @return Stringa che rappresenta la classe.
     */
    @Override
    public String toString(){
        return (String.valueOf(anno) + sezione + indirizzo).toLowerCase();
    }
}
