@Controller
public class HomeController {

    // Iniettare il servizio MongoService che contiene la logica per interagire con MongoDB
    @Autowired
    private MongoService mongoService;

    // Mappatura per la home page
    @GetMapping("/")
    public String index() {
        return "index"; // Restituisce la vista "index", una pagina con la navbar
    }

    // Gestione della richiesta per la pagina "cittadini"
    @GetMapping("/cittadini")
    public String cittadini(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "cognome", required = false) String cognome,
            @RequestParam(value = "citta", required = false) String citta,
            @RequestParam(value = "data_inizio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInizio,
            @RequestParam(value = "data_fine", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFine,
            @RequestParam(value = "order", required = false) String order,  // Parametro opzionale per l'ordinamento
            Model model) {

        // Passare i parametri di ricerca e ordinamento al servizio Mongo
        List<Document> cittadini = mongoService.ricercaCittadini(nome, cognome, citta, dataInizio, dataFine, order);
        model.addAttribute("cittadini", cittadini); // Aggiungere i risultati alla vista
        return "cittadini"; // Restituisce la vista "cittadini"
    }

    // Gestione della richiesta per la pagina "ospedali"
    @GetMapping("/ospedali")
    public String ospedali(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "codice", required = false) String codice,
            @RequestParam(value = "citta", required = false) String citta,
            Model model) {

        // Esegue la ricerca degli ospedali
        List<Document> ospedali = mongoService.ricercaOspedali(nome, codice, citta);
        model.addAttribute("ospedali", ospedali); // Aggiunge i risultati alla vista
        return "ospedali"; // Restituisce la vista "ospedali"
    }

    // Gestione della richiesta per la pagina "patologia"
    @GetMapping("/patologia")
    public String patologia(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "opt", required = false) String opt,
            @RequestParam(value = "gravita", required = false) Integer gravita,
            Model model) {

        // Esegue la ricerca delle patologie
        List<Document> patologie = mongoService.ricercaPatologie(nome, opt, gravita);
        model.addAttribute("patologie", patologie); // Aggiunge i risultati alla vista
        return "patologia"; // Restituisce la vista "patologia"
    }

    // Gestione della richiesta per la pagina "ricovero"
    @GetMapping("/ricovero")
    public String ricovero(
        @RequestParam(value = "data_inizio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInizio,
        @RequestParam(value = "data_fine", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFine,
        @RequestParam(value = "gg_inizio", required = false) Integer ggInizio,
        @RequestParam(value = "gg_fine", required = false) Integer ggFine,
        @RequestParam(value = "costo_inizio", required = false) Double costoInizio,
        @RequestParam(value = "costo_fine", required = false) Double costoFine,
        @RequestParam(value = "order", required = false) String order, // Parametro opzionale per l'ordinamento
        Model model) {

        // Esegue la ricerca dei ricoveri con i parametri forniti
        List<Document> ricoveri = mongoService.ricercaRicoveri(dataInizio, dataFine, ggInizio, ggFine, costoInizio, costoFine, order);
        model.addAttribute("ricoveri", ricoveri); // Aggiunge i risultati alla vista
        return "ricovero"; // Restituisce la vista "ricovero"
    }

    // Metodo POST per l'eliminazione di un ricovero
    @PostMapping("/eliminaRicovero")
    public String eliminaRicovero(@RequestParam("id") String id, Model model) {
        mongoService.deleteRicovero(id); // Chiamata al servizio per eliminare il ricovero
        return "redirect:/ricovero"; // Reindirizza alla pagina dei ricoveri dopo l'eliminazione
    }
    
    // Metodo POST per aggiungere un nuovo ricovero
    @PostMapping("/aggiungiRicovero")
    public String aggiungiRicovero(
            @RequestParam("newCODricovero") String codRicovero,
            @RequestParam("newCODospedale") String codOspedale,
            @RequestParam("newcosto") String costo,
            @RequestParam("newdata") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataRicovero,
            @RequestParam("newdurataRicovero") int durataRicovero,
            @RequestParam("newmotivo") String motivo,
            @RequestParam("newCSSN") String cssnPaziente,
            Model model) {

        // Crea un nuovo documento con i dati forniti
        Document nuovoRicovero = new Document()
                .append("CODricovero", codRicovero)
                .append("CODospedale", codOspedale)
                .append("COSTOricovero", costo)
                .append("DATAricovero", dataRicovero.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .append("DURATAricovero", durataRicovero)
                .append("MOTIVOricovero", motivo)
                .append("PAZIENTEricovero", cssnPaziente);

        // Aggiunge il nuovo ricovero tramite il servizio
        mongoService.aggiungiRicovero(nuovoRicovero);

        return "redirect:/ricovero"; // Reindirizza alla pagina dei ricoveri
    }
    
    // Metodo POST per modificare un ricovero esistente
    @PostMapping("/modificaRicovero")
    public String modificaRicovero(
            @RequestParam("id") String id,
            @RequestParam("CODricovero") String codRicovero,
            @RequestParam("CODospedale") String codOspedale,
            @RequestParam("COSTOricovero") String costo,
            @RequestParam("DATAricovero") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataRicovero,
            @RequestParam("DURATAricovero") int durataRicovero,
            @RequestParam("MOTIVOricovero") String motivo,
            @RequestParam("PAZIENTEricovero") String paziente,
            Model model) {

        // Crea un documento con i dati aggiornati
        Document updateData = new Document("CODricovero", codRicovero)
                .append("CODospedale", codOspedale)
                .append("COSTOricovero", costo)
                .append("DATAricovero", dataRicovero)
                .append("DURATAricovero", durataRicovero)
                .append("MOTIVOricovero", motivo)
                .append("PAZIENTEricovero", paziente);

        // Aggiorna il ricovero tramite il servizio
        mongoService.updateRicovero(id, updateData);

        return "redirect:/ricovero"; // Reindirizza alla pagina dei ricoveri
    }
}
