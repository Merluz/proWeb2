package com.prj2.service;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MongoService {

    // Costante per la stringa di connessione al database MongoDB
    private static final String CONNECTION_STRING = "mongodb+srv://gabrymerli:merLuzz.00@prjweb.co9so.mongodb.net/?retryWrites=true&w=majority&appName=prjweb";
    
    // Nome del database e delle collezioni utilizzate
    private static final String DATABASE_NAME = "prjweb";
    private static final String CITIZEN_COLLECTION_NAME = "cittadini";
    private static final String HOSPITAL_COLLECTION_NAME = "ospedali";
    private static final String PATHOLOGY_COLLECTION_NAME = "patologia";
    private static final String RECOVERY_COLLECTION_NAME = "ricovero";

    private final MongoClient mongoClient; // Client per la connessione a MongoDB

    // Costruttore della classe MongoService
    public MongoService() {
        // Configurazione dell'API del server MongoDB
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        // Configurazione del client MongoDB con la stringa di connessione e le impostazioni del server
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(CONNECTION_STRING))
                .serverApi(serverApi)
                .build();

        this.mongoClient = MongoClients.create(settings); // Creazione del client MongoDB
    }

    // Metodo per recuperare tutti i cittadini dal database
    public List<Document> getCittadini() {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(CITIZEN_COLLECTION_NAME);
        return collection.find().into(new ArrayList<>()); // Restituisce una lista di documenti
    }

    // Metodo per recuperare tutti gli ospedali dal database
    public List<Document> getOspedali() {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(HOSPITAL_COLLECTION_NAME);
        return collection.find().into(new ArrayList<>());
    }

    // Metodo per ricercare ospedali basati su nome, codice e città
    public List<Document> ricercaOspedali(String nome, String codice, String citta) {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(HOSPITAL_COLLECTION_NAME);

        Document query = new Document(); // Query per la ricerca

        // Aggiunta di filtri alla query per nome, codice e città (case-insensitive)
        if (nome != null && !nome.isEmpty()) {
            query.append("NOMEospedale", new Document("$regex", nome).append("$options", "i"));
        }
        if (codice != null && !codice.isEmpty()) {
            query.append("CODospedale", new Document("$regex", codice).append("$options", "i"));
        }
        if (citta != null && !citta.isEmpty()) {
            query.append("CITTAospedale", new Document("$regex", citta).append("$options", "i"));
        }

        return collection.find(query).into(new ArrayList<>()); // Restituisce i risultati della ricerca
    }

    // Metodo per recuperare tutte le patologie dal database
    public List<Document> getPatologie() {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(PATHOLOGY_COLLECTION_NAME);
        return collection.find().into(new ArrayList<>());
    }

    // Metodo per ricercare patologie basate su nome, opzioni e gravità
    public List<Document> ricercaPatologie(String nome, String opt, Integer gravita) {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(PATHOLOGY_COLLECTION_NAME);

        Document query = new Document(); // Query per la ricerca

        // Aggiunta di filtri alla query per nome e opzioni
        if (nome != null && !nome.isEmpty()) {
            query.append("nome", new Document("$regex", nome).append("$options", "i"));
        }
        if (opt != null && !opt.isEmpty()) {
            if (opt.equals("cronica")) {
                query.append("cronica", true);
            } else if (opt.equals("mortale")) {
                query.append("mortale", true);
            } // Non aggiungere filtri specifici per 'qualsiasi'
        }
        if (gravita != null) {
            query.append("gravita", new Document("$gte", gravita)); // Aggiunta di filtro per gravità
        }

        return collection.find(query).into(new ArrayList<>()); // Restituisce i risultati della ricerca
    }

    // Metodo per recuperare tutti i ricoveri dal database
    public List<Document> getRicoveri() {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(RECOVERY_COLLECTION_NAME);
        return collection.find().into(new ArrayList<>());
    }

    // Metodo per ricercare ricoveri in base a diversi parametri
    public List<Document> ricercaRicoveri(LocalDate dataInizio, LocalDate dataFine, Integer ggInizio, Integer ggFine, Double costoInizio, Double costoFine, String order) {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(RECOVERY_COLLECTION_NAME);

        Document query = new Document(); // Query per la ricerca

        // Aggiunta di filtri alla query per date, durata e costo
        if (dataInizio != null && dataFine != null) {
            query.append("DATAricovero", new Document("$gte", dataInizio).append("$lte", dataFine));
        }
        if (ggInizio != null && ggFine != null) {
            query.append("DURATAricovero", new Document("$gte", ggInizio).append("$lte", ggFine));
        }
        if (costoInizio != null && costoFine != null) {
            query.append("COSTOricovero", new Document("$gte", costoInizio).append("$lte", costoFine));
        }

        // Ordinamento dei risultati in base al campo specificato
        List<Document> risultati;
        if (order != null && !order.isEmpty()) {
            risultati = collection.find(query).sort(Sorts.ascending(order)).into(new ArrayList<>());
        } else {
            risultati = collection.find(query).into(new ArrayList<>());
        }

        return risultati; // Restituisce i risultati della ricerca
    }

    // Metodo per eliminare un ricovero utilizzando il suo ID
    public void deleteRicovero(String id) {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(RECOVERY_COLLECTION_NAME);
        collection.deleteOne(new Document("_id", new ObjectId(id))); // Esegue l'eliminazione
    }

    // Metodo per aggiungere un nuovo ricovero al database
    public void aggiungiRicovero(Document nuovoRicovero) {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(RECOVERY_COLLECTION_NAME);
        collection.insertOne(nuovoRicovero); // Esegue l'inserimento
    }

    // Metodo per aggiornare un ricovero esistente
    public void updateRicovero(String id, Document updateData) {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(RECOVERY_COLLECTION_NAME);

        Document query = new Document("_id", new ObjectId(id)); // Query per trovare il documento da aggiornare

        Document update = new Document();
        if (!updateData.isEmpty()) {
            update.append("$set", updateData); // Aggiorna i campi specificati
        } else {
            return; // Se non ci sono dati da aggiornare, esci dal metodo
        }

        collection.updateOne(query, update); // Esegue l'aggiornamento
    }

    // Metodo per ricercare cittadini in base a diversi parametri
    public List<Document> ricercaCittadini(String nome, String cognome, String citta, LocalDate data_inizio, LocalDate data_fine, String order) {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(CITIZEN_COLLECTION_NAME);

        Document query = new Document(); // Query per la ricerca

        // Aggiunta di filtri alla query per nome, cognome, città e data
        if (nome != null && !nome.isEmpty()) {
            query.append("NOMEcittadino", new Document("$regex", nome).append("$options", "i"));
        }
        if (cognome != null && !cognome.isEmpty()) {
            query.append("COGNOMEcittadino", new Document("$regex", cognome).append("$options", "i"));
        }
        if (citta != null && !citta.isEmpty()) {
            query.append("LUOGONASCITAcittadino", new Document("$regex", citta).append("$options", "i"));
        }
        if (data_inizio != null && data_fine != null) {
            query.append("DATANASCITAcittadino", new Document("$gte", data_inizio).append("$lte", data_fine)); // Filtro per data di nascita
        }

        // Ordinamento dei risultati in base al campo specificato
        List<Document> risultati;
        if (order != null && !order.isEmpty()) {
            risultati = collection.find(query).sort(Sorts.ascending(order)).into(new ArrayList<>());
        } else {
            risultati = collection.find(query).into(new ArrayList<>());
        }

        return risultati; // Restituisce i risultati della ricerca
    }
}
