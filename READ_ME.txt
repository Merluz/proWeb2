***********************************************************************************************

IMPORTANTE 

Per l'installazione è necessario l'uso di JAVA-22.

L'utilizzo di altre versioni potrebbe dar luogo a diversi problemi di installazione.

***********************************************************************************************

ISTRUZIONI PER L'INSTALLAZIONE:

1. Scaricare il file "Installer Sito Sanitario".


2. Avviare l'installer o, in alternativa, eseguire manualmente il file .jar tramite il prompt dei comandi (cmd) su Windows:

    Aprire il prompt dei comandi e navigare nella cartella dove si trova il file .jar con il comando:

    cd "path_to_directory"

    Eseguire il file con il comando:

    java -jar progWeb2-0.0.1.jar



3. Si dovrebbe aprire una pagina web: 

    a. In caso positivo, è possibile navigare o utilizzare l'applicazione.

    b. In caso negativo, è necessario accedere manualmente alla porta 8080 digitando il seguente link nel browser:

        http://localhost:8080/

    c. Se non è possibile accedere alla porta 8080, procedere al controllo di evenetuali processi che stanno già utilizzando quella porta. 
    
       Per farlo, è necessario eseguire il seguente comando nel prompt dei comandi:

            netstat -aon | findstr :8080

       Se ci dovessero essere dei processi in esecuzione, prendere nota del loro PID (Process ID) e terminare il processo con il comando:

            taskkill /PID <PID> /F

       (Sostituire <PID> con il numero del processo.)


4. Se l'installer non funziona correttamente, aggiungere un'eccezione nell'antivirus per consentirne l'esecuzione.


5. Se i problemi persistono dopo aver seguito questi passaggi, provare a riavviare il PC e ripetere l'installazione.


***********************************************************************************************
VERSIONI:

Plugin di Gradle
Java: Plugin standard di Gradle per i progetti Java.
Spring Boot: Versione 3.3.3.
Spring Dependency Management: Versione 1.1.6.
Configurazione Java
Compatibilità del codice sorgente: Java versione 22.
Compatibilità del target: Java versione 22.
Toolchain Java: Usa Java 22 per la compilazione.
Repositories
Maven Central: Repository da cui recuperare le dipendenze.
Dipendenze
Spring Boot Starter per Thymeleaf: Per l'integrazione di Thymeleaf.
Spring Boot Starter Web: Per creare applicazioni web usando Spring MVC.
Spring Boot Starter Data MongoDB: Per connettersi a MongoDB e gestire il database.
Driver MongoDB Sync: Versione 5.1.2, il driver MongoDB per l'interazione con il database in modo sincrono.
Spring Boot DevTools: Solo per lo sviluppo, aiuta con il riavvio rapido e il miglioramento dell'efficienza dello sviluppo.
Spring Boot Starter Test: Per testare l'applicazione con strumenti integrati in Spring Boot.
		