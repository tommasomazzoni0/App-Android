package com.example.app_registro_elettronico.gestione;

/**
 * La classe Voti rappresenta un voto assegnato a uno studente.
 * Ogni voto include una data, un valore numerico e una descrizione opzionale.
 */
public class Voti {

    private String data;
    private float voto;
    private String descrizione;

    /**
     * Costruttore della classe Voti.
     *
     * @param data        La data in cui è stato assegnato il voto.
     * @param voto        Il valore numerico del voto.
     * @param descrizione Una descrizione del voto (es. "Verifica di matematica").
     */
    public Voti(String data, float voto, String descrizione) {
        this.data = data;
        this.voto = voto;
        this.descrizione = descrizione;
    }

    /**
     * Ottiene la data del voto.
     * @return La data del voto.
     */
    public String getdata() {
        return data;
    }

    /**
     * Imposta una nuova data per il voto.
     * @param data La nuova data del voto.
     */
    public void setdata(String data) {
        this.data = data;
    }

    /**
     * Ottiene il valore numerico del voto.
     * @return Il valore numerico del voto.
     */
    public float getvoto() {
        return voto;
    }

    /**
     * Imposta un nuovo valore numerico per il voto.
     * @param voto Il nuovo valore numerico del voto.
     */
    public void setvoto(float voto) {
        this.voto = voto;
    }

    /**
     * Ottiene la descrizione del voto.
     * @return La descrizione del voto.
     */
    public String getdescrizione() {
        return descrizione;
    }

    /**
     * Imposta una nuova descrizione per il voto.
     * @param descrizione La nuova descrizione del voto.
     */
    public void setdescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
