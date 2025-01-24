package com.example.app_registro_elettronico.gestione;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * La classe Note rappresenta una nota registrata nel sistema.
 * Ogni nota contiene una data, il nome del professore e il testo della nota.
 */
public class Note implements Serializable {

    private Date date;
    private Docente professor;
    private String text;

    /**
     * Costruttore della classe Note.
     *
     * @param date      La data della nota.
     * @param professor Il nome del professore associato alla nota.
     * @param text      Il testo della nota.
     */
    public Note(Date date, Docente professor, String text) {
        this.date = date;
        this.professor = professor;
        this.text = text;
    }

    /**
     * Ottiene la data della nota.
     * @return La data della nota.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Imposta una nuova data per la nota.
     * @param date La nuova data della nota.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Ottiene il nome del professore associato alla nota.
     * @return Il nome del professore.
     */
    public Docente getProfessor() {
        return professor;
    }

    /**
     * Imposta il nome del professore associato alla nota.
     * @param professor Il nuovo nome del professore.
     */
    public void setProfessor(Docente professor) {
        this.professor = professor;
    }

    /**
     * Ottiene il testo della nota.
     * @return Il testo della nota.
     */
    public String getText() {
        return text;
    }

    /**
     * Imposta un nuovo testo per la nota.
     * @param text Il nuovo testo della nota.
     */
    public void setText(String text) {
        this.text = text;
    }

    public String getstringData(){
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.YEAR);
    }


    public String toStringServer(){
        if(professor==null){
            return getstringData() + "*" + "docente_nonregistrato" + "*Motivo_" + text;
        }
        return getstringData() + "*" + professor.getNome() + "_" + professor.getCognome() + "*Motivo_" + text;
    }

}
