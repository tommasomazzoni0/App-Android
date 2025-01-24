package com.example.app_registro_elettronico.gestione;

import androidx.annotation.NonNull;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Rappresenta un'assenza di uno studente con la possibilità di indicare se è stata giustificata e con quale motivazione.
 */
public class Assenza implements Serializable {

    /**
     * La data dell'assenza.
     */
    private Date data;

    /**
     * Indica se l'assenza è stata giustificata.
     */
    private boolean giustifica;

    /**
     * La motivazione della giustificazione dell'assenza.
     */
    private Docente docente;

    /**
     * Costruttore della classe Assenza.
     *
     * @param data La data dell'assenza.
     * @param giustifica Indica se l'assenza è stata giustificata.
     */
    public Assenza(Docente docente, Date data, boolean giustifica) {
        this.docente=docente;
        this.data = data;
        this.giustifica=giustifica;
    }

    /**
     * Restituisce la data dell'assenza.
     * @return La data dell'assenza.
     */
    public Date getData() {
        return data;
    }

    /**
     * Imposta la data dell'assenza.
     * @param data La nuova data dell'assenza.
     */
    public void setData(Date data) {
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
    public Docente getdocente() {
        return docente;
    }

    /**
     * Imposta la motivazione della giustificazione.
     *
     * @param docente La nuova motivazione della giustificazione.
     */
    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    /**
     * Stringa la data nel modo voluto
     * @return una stringa
     */
    public String getstringData(){
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(data);
        return calendar.get(Calendar.DAY_OF_MONTH)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.YEAR);
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(data);
        return calendar.get(Calendar.DAY_OF_MONTH)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.YEAR);

    }


    /**
     * Funzione che restituisce l'assenza in formato stringa.
     *
     * @return Stringa che rappresenta l'assenza.
     */
    public String toStringServer(){
        if(docente==null){
            if(giustifica){
                return getstringData() + " " +  "docente_nonregistrato" + " true";
            }
            return getstringData() + " " + "docente_nonregistrato" + " false";
        }
        else{
            if(giustifica){
                return getstringData() + " " + docente.getNome() + "_" + docente.getCognome() + " true";
            }
            return getstringData() + " " + docente.getNome() + "_" + docente.getCognome() + " false";
        }
    }
}
