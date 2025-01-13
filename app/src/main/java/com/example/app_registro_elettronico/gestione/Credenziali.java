package com.example.app_registro_elettronico.gestione;

import java.io.Serializable;

public class Credenziali implements Serializable {
    private String user;
    private String password;

    /**
     * Funzione che crea le credenziali per gli utenti con: nome utente e password.
     *
     * @param user Nome utente.
     * @param password Password.
     */
    public Credenziali(String user, String password) {
        this.user = user;
        this.password = password;
    }

    /**
     * Restituisce nome utente.
     *
     * @return Nome utente.
     */
    public String getUser() {
        return user;
    }

    /**
     * Restituisce la password.
     *
     * @return Password.
     */
    public String getPassword() {
        return password;
    }

    public boolean equals(Credenziali credenziali){
        return (this.user.equals(credenziali.getUser()) && this.password.equals(credenziali.getPassword()));
    }
}
