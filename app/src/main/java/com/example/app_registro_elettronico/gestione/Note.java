package com.example.app_registro_elettronico.gestione;

/**
 * La classe Note rappresenta una nota registrata nel sistema.
 * Ogni nota contiene una data, il nome del professore e il testo della nota.
 */
public class Note {

    private String date;
    private String professor;
    private String text;

    /**
     * Costruttore della classe Note.
     *
     * @param date      La data della nota.
     * @param professor Il nome del professore associato alla nota.
     * @param text      Il testo della nota.
     */
    public Note(String date, String professor, String text) {
        this.date = date;
        this.professor = professor;
        this.text = text;
    }

    /**
     * Ottiene la data della nota.
     * @return La data della nota.
     */
    public String getDate() {
        return date;
    }

    /**
     * Imposta una nuova data per la nota.
     * @param date La nuova data della nota.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Ottiene il nome del professore associato alla nota.
     * @return Il nome del professore.
     */
    public String getProfessor() {
        return professor;
    }

    /**
     * Imposta il nome del professore associato alla nota.
     * @param professor Il nuovo nome del professore.
     */
    public void setProfessor(String professor) {
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
}
