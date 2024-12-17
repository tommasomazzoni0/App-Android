package com.example.app_registro_elettronico.gestione;

public class Assenza {

    private String data;
    private boolean giustifica;

    private String giustificazione;

    public Assenza(String data, boolean giustifica, String giustificazione) {
        this.data = data;
        this.giustifica = giustifica;
        this.giustificazione = giustificazione;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean getgiustifica() {
        return giustifica;
    }

    public void setgiustifica(boolean giustifica) {
        this.giustifica = giustifica;
    }

    public String getgiustificazione() {
        return giustificazione;
    }

    public void setgiustificazione(String giustificazione) {
        this.giustificazione = giustificazione;
    }
}

