package com.example.app_registro_elettronico.gestione;

public class Voti {

    private String data;
    private float voto;
    private String descrizione;

    public Voti(String data, float voto, String descrizione) {
        this.data = data;
        this.voto = voto;
        this.descrizione = descrizione;
    }

    public String getdata() {
        return data;
    }

    public void setdata(String data) {
        this.data = data;
    }

    public float getvoto() {
        return voto;
    }

    public void setvoto(float voto) {
        this.voto = voto;
    }

    public String getdescrizione() {
        return descrizione;
    }

    public void setdescrizione(String text) {
        this.descrizione = text;
    }
}

