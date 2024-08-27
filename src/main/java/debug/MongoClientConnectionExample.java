package debug;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoClientConnectionExample {
    public static void main(String[] args) {
        // Sostituisci <db_password> con la tua password effettiva
        String connectionString = "mongodb+srv://gabrymerli:merLuzz.00@prjweb.co9so.mongodb.net/?retryWrites=true&w=majority&appName=prjweb";

        // Configura l'API del server
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        // Configura le impostazioni del client
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        // Crea un nuovo client e connettiti al server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Esegui un comando di ping per confermare una connessione riuscita
                MongoDatabase database = mongoClient.getDatabase("prjweb");
                database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");

                // Accedi alla collezione 'cittadini'
                MongoCollection<Document> collection = database.getCollection("cittadini");

                // Recupera e stampa tutti i documenti nella collezione
                for (Document doc : collection.find()) {
                    System.out.println("CF: " + doc.getString("CF"));
                    System.out.println("Nome: " + doc.getString("nome"));
                    System.out.println("Cognome: " + doc.getString("cognome"));
                    System.out.println("Città: " + doc.getString("citta"));
                    System.out.println("Indirizzo: " + doc.getString("indirizzo"));
                    System.out.println("Luogo di Nascita: " + doc.getString("luogoNascita"));
                    System.out.println("Data di Nascita: " + doc.getString("dataNascita"));
                    System.out.println(); // Stampa una riga vuota per separare i documenti
                }
            } catch (MongoException e) {
                // Gestisci eventuali eccezioni durante il comando di ping o la query
                System.err.println("An error occurred while trying to connect to MongoDB.");
                e.printStackTrace();
            }
        }
    }
}

