package com.prj2.controller;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prj2.model.SearchParamsCittadini;
import com.prj2.service.MongoService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private MongoService mongoService;

    @GetMapping("/")
    public String index() {
        return "index"; // Pagina con navbar
    }

    @GetMapping("/cittadini")
    public String cittadini(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "cognome", required = false) String cognome,
            @RequestParam(value = "citta", required = false) String citta,
            @RequestParam(value = "data_inizio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInizio,
            @RequestParam(value = "data_fine", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFine,
            @RequestParam(value = "order", required = false) String order,  // Aggiungi il parametro di ordinamento
            Model model) {

        // Passa il parametro di ordinamento al servizio
        List<Document> cittadini = mongoService.ricercaCittadini(nome, cognome, citta, dataInizio, dataFine, order);
        model.addAttribute("cittadini", cittadini);
        return "cittadini";
    }


    @GetMapping("/ospedali")
    public String ospedali(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "codice", required = false) String codice,
            @RequestParam(value = "citta", required = false) String citta,
            Model model) {

        List<Document> ospedali = mongoService.ricercaOspedali(nome, codice, citta);
        model.addAttribute("ospedali", ospedali);
        return "ospedali"; 
    }

    @GetMapping("/patologia")
    public String patologia(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "opt", required = false) String opt,
            @RequestParam(value = "gravita", required = false) Integer gravita,
            Model model) {

        List<Document> patologie = mongoService.ricercaPatologie(nome, opt, gravita);
        model.addAttribute("patologie", patologie);
        return "patologia";
    }
    
    @GetMapping("/ricovero")
    public String ricovero(
        @RequestParam(value = "data_inizio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInizio,
        @RequestParam(value = "data_fine", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFine,
        @RequestParam(value = "gg_inizio", required = false) Integer ggInizio,
        @RequestParam(value = "gg_fine", required = false) Integer ggFine,
        @RequestParam(value = "costo_inizio", required = false) Double costoInizio,
        @RequestParam(value = "costo_fine", required = false) Double costoFine,
        @RequestParam(value = "order", required = false) String order, // Parametro di ordinamento aggiunto
        Model model) {

        List<Document> ricoveri = mongoService.ricercaRicoveri(dataInizio, dataFine, ggInizio, ggFine, costoInizio, costoFine, order);
        model.addAttribute("ricoveri", ricoveri);
        return "ricovero";
    }

    // Aggiungi il metodo per gestire l'eliminazione
    @PostMapping("/eliminaRicovero")
    public String eliminaRicovero(@RequestParam("id") String id, Model model) {
        mongoService.deleteRicovero(id);
        return "redirect:/ricovero"; // Redirect alla pagina dei ricoveri dopo l'eliminazione
    }


}
