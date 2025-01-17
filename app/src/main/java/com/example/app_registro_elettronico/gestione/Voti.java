package com.example.app_registro_elettronico.gestione;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * La classe Voti rappresenta un voto assegnato a uno studente.
 * Ogni voto include una data, un valore numerico e una descrizione opzionale.
 */
public class Voti implements Serializable {

    private Date data;
    private double voto;
    private Docente docente;
    private String materia;

    /**
     * Costruttore della classe Voti.
     *
     * @param data        La data in cui è stato assegnato il voto.
     * @param voto        Il valore numerico del voto.
     * @param docente Una descrizione del voto (es. "Verifica di matematica").
     */
    public Voti(double voto, String materia, Docente docente, Date data) {
        this.voto = voto;
        this.materia = materia;
        this.data = data;
        this.docente = docente;
    }

    /**
     * Ottiene la data del voto.
     * @return La data del voto.
     */
    public Date getdata() {
        return data;
    }

    /**
     * Imposta una nuova data per il voto.
     * @param data La nuova data del voto.
     */
    public void setdata(Date data) {
        this.data = data;
    }

    /**
     * Ottiene il valore numerico del voto.
     * @return Il valore numerico del voto.
     */
    public double getvoto() {
        return voto;
    }

    /**
     * Imposta un nuovo valore numerico per il voto.
     * @param voto Il nuovo valore numerico del voto.
     */
    public void setvoto(double voto) {
        this.voto = voto;
    }

    /**
     * Ottiene la descrizione del voto.
     * @return La descrizione del voto.
     */
    public Docente getdocente() {
        return docente;
    }

    public String getMateria() {
        return materia;
    }

    /**
     * Imposta una nuova descrizione per il voto.
     * @param docente La nuova descrizione del voto.
     */
    public void setdocente(Docente docente) {
        this.docente = docente;
    }

    public String getstringData(){
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(data);
        return calendar.get(Calendar.DAY_OF_MONTH)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.YEAR);
    }

}
