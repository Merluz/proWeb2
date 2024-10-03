***********************************************************************************************

IMPORTANTE 

Per l'installazione è necessario l'uso di JAVA-22 

L'utilizzo di altre versioni potrebbe dar luogo a diversi problemi di installazione

***********************************************************************************************

ISTRUZIONI PER L'INSTALLAZIONE:

1. Scaricare il file "Installer Sito Sanitario".


2. Avviare l'installer o, in alternativa, eseguire manualmente il file .jar tramite il prompt dei comandi (cmd) su Windows:

Aprire il prompt dei comandi e navigare nella cartella dove si trova il file .jar con il comando:

cd "path_to_directory"

Eseguire il file con il comando:

java -jar progWeb2-0.0.1.jar



3. Si dovrebbe aprire una pagina web: a. In caso positivo, è possibile navigare o utilizzare l'applicazione.

b. In caso negativo, è necessario accedere manualmente alla porta 8080 digitando il seguente link nel browser:

http://localhost:8080/

c. Se non riesci ancora ad accedere alla porta 8080, controlla se ci sono processi che stanno già utilizzando quella porta. Per farlo, esegui questo comando nel prompt dei comandi:

netstat -aon | findstr :8080

Se ci sono processi in esecuzione, prendi nota del loro PID (Process ID) e termina il processo con il comando:

taskkill /PID <PID> /F

(Sostituisci <PID> con il numero del processo.)


4. Se l'installer non funziona correttamente, aggiungere un'eccezione nell'antivirus per consentirne l'esecuzione.


5. Se i problemi persistono dopo aver seguito questi passaggi, prova a riavviare il PC e ripetere l'installazione.


***********************************************************************************************


		