package com.example.app_registro_elettronico.gestione;

public class Note {

    private String date;
    private String professor;
    private String text;

    public Note(String date, String professor, String text) {
        this.date = date;
        this.professor = professor;
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

