package com.prj2.model;

import java.time.LocalDate;

public class SearchParamsCittadini {
    
    // Variabili di istanza che rappresentano i parametri di ricerca per i cittadini
    private String nome;
    private String cognome;
    private String citta;
    private LocalDate dataInizio;
    private LocalDate dataFine;

    // Costruttore vuoto necessario per la serializzazione (ad es. durante l'invio e la ricezione di dati)
    public SearchParamsCittadini() {
    }

    // Costruttore completo con tutti i parametri
    public SearchParamsCittadini(String nome, String cognome, String citta, LocalDate dataInizio, LocalDate dataFine) {
        this.nome = nome;
        this.cognome = cognome;
        this.citta = citta;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    // Getters e Setters per accedere e modificare le variabili di istanza

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    // Metodo toString per una comoda rappresentazione testuale dei parametri di ricerca
    @Override
    public String toString() {
        return "SearchParamsCittadini{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", citta='" + citta + '\'' +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                '}';
    }
}
