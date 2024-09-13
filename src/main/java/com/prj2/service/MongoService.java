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

import javax.management.Query;

@Service
public class MongoService {

    private static final String CONNECTION_STRING = "mongodb+srv://gabrymerli:merLuzz.00@prjweb.co9so.mongodb.net/?retryWrites=true&w=majority&appName=prjweb";
    private static final String DATABASE_NAME = "prjweb";
    private static final String CITIZEN_COLLECTION_NAME = "cittadini";
    private static final String HOSPITAL_COLLECTION_NAME = "ospedali";
    private static final String PATHOLOGY_COLLECTION_NAME = "patologia";
    private static final String RECOVERY_COLLECTION_NAME = "ricovero";

    private final MongoClient mongoClient;

    public MongoService() {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(CONNECTION_STRING))
                .serverApi(serverApi)
                .build();

        this.mongoClient = MongoClients.create(settings);
    }

    public List<Document> getCittadini() {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(CITIZEN_COLLECTION_NAME);
        return collection.find().into(new ArrayList<>());
    }

    public List<Document> getOspedali() {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(HOSPITAL_COLLECTION_NAME);
        return collection.find().into(new ArrayList<>());
    }

    
    public List<Document> ricercaOspedali(String nome, String codice, String citta) {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(HOSPITAL_COLLECTION_NAME);

        Document query = new Document();

        if (nome != null && !nome.isEmpty()) {
            query.append("NOMEospedale", new Document("$regex", nome).append("$options", "i"));
        }
        if (codice != null && !codice.isEmpty()) {
            query.append("CODospedale", new Document("$regex", codice).append("$options", "i"));
        }
        if (citta != null && !citta.isEmpty()) {
            query.append("CITTAospedale", new Document("$regex", citta).append("$options", "i"));
        }
        
        

        return collection.find(query).into(new ArrayList<>());
    }



    public List<Document> getPatologie() {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(PATHOLOGY_COLLECTION_NAME);
        return collection.find().into(new ArrayList<>());
    }
    
    public List<Document> ricercaPatologie(String nome, String opt, Integer gravita) {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(PATHOLOGY_COLLECTION_NAME);

        Document query = new Document();

        if (nome != null && !nome.isEmpty()) {
            query.append("nome", new Document("$regex", nome).append("$options", "i"));
        }
        if (opt != null && !opt.isEmpty()) {
            if (opt.equals("cronica")) {
                query.append("cronica", true);
            } else if (opt.equals("mortale")) {
                query.append("mortale", true);
            } else if (opt.equals("qualsiasi")) {
                // Non aggiungere filtri specifici per 'qualsiasi'
            }
        }
        if (gravita != null) {
            query.append("gravita", new Document("$gte", gravita));
        }

        return collection.find(query).into(new ArrayList<>());
    }

    public List<Document> getRicoveri() {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(RECOVERY_COLLECTION_NAME);
        return collection.find().into(new ArrayList<>());
    }
    
    public List<Document> ricercaRicoveri(LocalDate dataInizio, LocalDate dataFine, Integer ggInizio, Integer ggFine, Double costoInizio, Double costoFine, String order) {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(RECOVERY_COLLECTION_NAME);

        Document query = new Document();

        if (dataInizio != null && dataFine != null) {
            query.append("DATAricovero", new Document("$gte", dataInizio).append("$lte", dataFine)); // Modificato
        }
        if (ggInizio != null && ggFine != null) {
            query.append("DURATAricovero", new Document("$gte", ggInizio).append("$lte", ggFine)); // Modificato
        }
        if (costoInizio != null && costoFine != null) {
            query.append("COSTOricovero", new Document("$gte", costoInizio).append("$lte", costoFine)); // Modificato
        }

        // Ordinamento in base al campo scelto
        List<Document> risultati;
        if (order != null && !order.isEmpty()) {
            risultati = collection.find(query).sort(Sorts.ascending(order)).into(new ArrayList<>());
        } else {
            risultati = collection.find(query).into(new ArrayList<>());
        }

        return risultati;
    }
    
    public void deleteRicovero(String id) {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(RECOVERY_COLLECTION_NAME);
        collection.deleteOne(new Document("_id", new ObjectId(id)));
    }
    
    public void aggiungiRicovero(Document nuovoRicovero) {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(RECOVERY_COLLECTION_NAME);
        collection.insertOne(nuovoRicovero);
    }
    
    public void updateRicovero(String id, Document updateData) {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(RECOVERY_COLLECTION_NAME);


        Document query = new Document("_id", new ObjectId(id));


        Document update = new Document();
        if (!updateData.isEmpty()) {
            update.append("$set", updateData);
        } else {
            return;
        }

        collection.updateOne(query, update);
    }




    
    public List<Document> ricercaCittadini(String nome, String cognome, String citta, LocalDate data_inizio, LocalDate data_fine, String order) {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(CITIZEN_COLLECTION_NAME);

        Document query = new Document();

        // Regex-based case-insensitive search
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
            query.append("DATANASCITAcittadino", new Document("$gte", data_inizio).append("$lte", data_fine));
        }

        // Sorting logic
        List<Document> risultati;
        if (order != null && !order.isEmpty()) {
            risultati = collection.find(query).sort(Sorts.ascending(order)).into(new ArrayList<>());
        } else {
            risultati = collection.find(query).into(new ArrayList<>());
        }

        return risultati;
    }

}
