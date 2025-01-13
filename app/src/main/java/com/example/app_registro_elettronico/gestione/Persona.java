package com.example.app_registro_elettronico.gestione;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Persona implements Serializable {
    private String nome;
    private String cognome;

    private Date dataDiNascita;

    private String CF;

    private Credenziali credenziali;

    /**
     * Funzione che serve per creare una nuova persona.
     *
     * @param nome Nome persona.
     * @param congome Cognome persona.
     * @param dataDiNascita Data di nascita persona.
     * @param CF Codice fiscale persona.
     */
    public Persona(String nome, String congome, Date dataDiNascita, String CF) {
        this.nome = nome;
        this.cognome = congome;
        this.dataDiNascita = dataDiNascita;
        this.CF = CF;
    }

    /**
     * Funzione che restituisce il nome della persona.
     *
     * @return Nome persona.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Funzione che restituisce il cognome della persona.
     *
     * @return Cognome persona.
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Funzione che restituisce la data di nascita della persona.
     *
     * @return Data di nascita persona.
     */
    public Date getDataDiNascita() {
        return dataDiNascita;
    }

    /**
     * Funzione che restituisce la data di nascita in formato stringa.
     *
     * @return Data di nascita come stringa.
     */
    public String getStringData(){
        Calendar calendar = Calendar. getInstance();
        calendar.setTime(dataDiNascita);
        return calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR);
    }

    /**
     * Funzione che restituisce il codice fiscale della persona.
     *
     * @return Codice fiscale persona.
     */
    public String getCF() {
        return CF;
    }

    /**
     * Funzione che restituisce le credenziali della persona.
     *
     * @return Credenziali persona.
     */
    public Credenziali getCredenziali() {
        return credenziali;
    }

    /**
     * Funzione che imposta il nome della persona.
     * @param nome Nome persona da impostare.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Funzione che imposta il cognome della persona.
     * @param cognome Nome persona da impostare.
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Funzione che imposta la data di nascita della persona.
     * @param dataDiNascita Nome persona da impostare.
     */
    public void setDataDiNascita(Date dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    /**
     * Funzione che imposta il codice fiscale della persona.
     * @param CF Nome persona da impostare.
     */
    public void setCF(String CF) {
        this.CF = CF;
    }

    /**
     * Funzione che imposta le credenziali della persona.
     * @param credenziali Nome persona da impostare.
     */
    public void setCredenziali(Credenziali credenziali) {
        this.credenziali = credenziali;
    }
}
