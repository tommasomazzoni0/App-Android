package com.example.app_registro_elettronico.gestione;

/**
 * Rappresenta un'assenza di uno studente con la possibilità di indicare se è stata giustificata e con quale motivazione.
 */
public class Assenza {

    /**
     * La data dell'assenza.
     */
    private String data;

    /**
     * Indica se l'assenza è stata giustificata.
     */
    private boolean giustifica;

    /**
     * La motivazione della giustificazione dell'assenza.
     */
    private String giustificazione;

    /**
     * Costruttore della classe Assenza.
     *
     * @param data La data dell'assenza.
     * @param giustifica Indica se l'assenza è stata giustificata.
     * @param giustificazione La motivazione della giustificazione.
     */
    public Assenza(String data, boolean giustifica, String giustificazione) {
        this.data = data;
        this.giustifica = giustifica;
        this.giustificazione = giustificazione;
    }

    /**
     * Restituisce la data dell'assenza.
     * @return La data dell'assenza.
     */
    public String getData() {
        return data;
    }

    /**
     * Imposta la data dell'assenza.
     * @param data La nuova data dell'assenza.
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Verifica se l'assenza è stata giustificata.
     * @return true se l'assenza è stata giustificata, {@code false} altrimenti.
     */
    public boolean getgiustifica() {
        return giustifica;
    }

    /**
     * Imposta lo stato di giustificazione dell'assenza.
     *
     * @param giustifica {@code true} se l'assenza è stata giustificata, {@code false} altrimenti.
     */
    public void setgiustifica(boolean giustifica) {
        this.giustifica = giustifica;
    }

    /**
     * Restituisce la motivazione della giustificazione.
     *
     * @return La motivazione della giustificazione.
     */
    public String getgiustificazione() {
        return giustificazione;
    }

    /**
     * Imposta la motivazione della giustificazione.
     *
     * @param giustificazione La nuova motivazione della giustificazione.
     */
    public void setgiustificazione(String giustificazione) {
        this.giustificazione = giustificazione;
    }
}
