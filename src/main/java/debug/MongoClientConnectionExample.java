package debug; // Dichiarazione del pacchetto debug

import com.mongodb.ConnectionString; // Importa la classe ConnectionString per configurare la connessione
import com.mongodb.MongoClientSettings; // Importa la classe per configurare le impostazioni del client
import com.mongodb.MongoException; // Importa la classe per gestire le eccezioni di MongoDB
import com.mongodb.ServerApi; // Importa la classe per configurare l'API del server
import com.mongodb.ServerApiVersion; // Importa la versione dell'API del server
import com.mongodb.client.MongoClient; // Importa la classe per il client MongoDB
import com.mongodb.client.MongoClients; // Importa la classe per creare il client MongoDB
import com.mongodb.client.MongoCollection; // Importa la classe per le collezioni di MongoDB
import com.mongodb.client.MongoDatabase; // Importa la classe per i database MongoDB
import org.bson.Document; // Importa la classe Document per rappresentare i documenti BSON

public class MongoClientConnectionExample { // Dichiarazione della classe principale
    public static void main(String[] args) { // Metodo main, punto di ingresso dell'applicazione
        // Stringa di connessione con le credenziali per MongoDB
        String connectionString = "mongodb+srv://gabrymerli:merLuzz.00@prjweb.co9so.mongodb.net/?retryWrites=true&w=majority&appName=prjweb";

        // Configura l'API del server
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1) // Specifica la versione dell'API
                .build();

        // Configura le impostazioni del client
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString)) // Applica la stringa di connessione
                .serverApi(serverApi) // Imposta l'API del server
                .build();

        // Crea un nuovo client e si connette al server
        try (MongoClient mongoClient = MongoClients.create(settings)) { // Utilizza try-with-resources per gestire automaticamente la chiusura del client
            try {
                // Esegui un comando di ping per confermare una connessione riuscita
                MongoDatabase database = mongoClient.getDatabase("prjweb"); // Accede al database "prjweb"
                database.runCommand(new Document("ping", 1)); // Invia un comando di ping
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!"); // Stampa conferma della connessione

                // Accedi alla collezione 'cittadini'
                MongoCollection<Document> collection = database.getCollection("cittadini"); // Ottieni la collezione "cittadini"

                // Recupera e stampa tutti i documenti nella collezione
                for (Document doc : collection.find()) { // Itera su tutti i documenti nella collezione
                    System.out.println("CF: " + doc.getString("CF")); // Stampa il Codice Fiscale
                    System.out.println("Nome: " + doc.getString("nome")); // Stampa il nome
                    System.out.println("Cognome: " + doc.getString("cognome")); // Stampa il cognome
                    System.out.println("Città: " + doc.getString("citta")); // Stampa la città
                    System.out.println("Indirizzo: " + doc.getString("indirizzo")); // Stampa l'indirizzo
                    System.out.println("Luogo di Nascita: " + doc.getString("luogoNascita")); // Stampa il luogo di nascita
                    System.out.println("Data di Nascita: " + doc.getString("dataNascita")); // Stampa la data di nascita
                    System.out.println(); // Stampa una riga vuota per separare i documenti
                }
            } catch (MongoException e) { // Cattura eventuali eccezioni durante il comando di ping o la query
                // Gestisci eventuali eccezioni durante il comando di ping o la query
                System.err.println("An error occurred while trying to connect to MongoDB."); // Stampa messaggio di errore
                e.printStackTrace(); // Stampa lo stack trace dell'eccezione
            }
        } // Il client MongoDB viene chiuso automaticamente qui grazie al try-with-resources
    }
}
