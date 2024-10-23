// Wait for the DOM to be fully loaded
document.addEventListener('DOMContentLoaded', function() {
    console.log('Document is ready');

    // Change button color on click
    const button = document.querySelector('button');
    if (button) {
        button.addEventListener('click', function() {
            button.style.backgroundColor = '#28a745'; // Change button color to green on click
        });
    }
});

// Function to toggle dark mode
function toggleDarkMode() {
    const body = document.body;
    body.classList.toggle('dark-mode'); // Toggles the 'dark-mode' class on the body
}

// Add event listener for dark mode toggle button if it exists
const darkModeButton = document.querySelector('#dark-mode-toggle');
if (darkModeButton) {
    darkModeButton.addEventListener('click', toggleDarkMode);
}

// Set active class on the sidebar based on current URL
document.addEventListener('DOMContentLoaded', activeTag);

function activeTag() {
    var url = window.location.pathname; // Ottieni il percorso della URL corrente
    var navTags = document.getElementsByClassName("sidebar-link"); // Ottieni tutti i link della sidebar

    Array.from(navTags).forEach(a => {
        var aHref = a.getAttribute("href"); // Ottieni l'attributo href del link

        // Aggiungi la classe 'active' solo al link corrispondente
        if (url === aHref) { 
            a.classList.add("active");
        } else {
            a.classList.remove("active"); // Rimuovi la classe 'active' dagli altri link
        }
    });
}


// Function to open a modification section for a record
function openModify(index, codRicovero) {
    var previous = document.getElementsByClassName("openedDiv");
    if (previous.length != 0) {
        $(previous[0]).css({
            "margin-bottom": "5px",
            "border-radius": "15px",
            "border-bottom": "none"
        }).removeClass("openedDiv");
    }
    
    var id = "rec-" + index;
    var element = $("#" + id);
    var theme = "<?php echo $_SESSION['theme']; ?>"; // Get theme from PHP session
    if (theme === "blue") {
        element.css({
            "margin-bottom": "0px",
            "border-radius": "15px 15px 0px 0px", 
            "border-bottom": "1px solid rgb(180 214 255)"
        });
    } else {
        element.css({
            "margin-bottom": "0px",
            "border-radius": "15px 15px 0px 0px", 
            "border-bottom": "1px solid #8c9c55"
        });
    }

    var openMod = document.getElementsByClassName("modifing-results");
    if (openMod.length === 0) {
        createElement(index, element, codRicovero);
    } else {
        $(openMod).remove(); // Remove existing modifing results
        createElement(index, element, codRicovero);
    }
}

// Function to create a new element for record modification
function createElement(index, element, codRicovero) {
    var newElement = $("#new" + index);
    if (!newElement.length) {
        newElement = $( // Create a new form for modifying records
            '<form method="post" action="../php/modificaRicovero.php" style="display: none;">' + 
            '<div class="records-ricovero-modify modifing-results" id="new' + index + '"> ' +
            '<div></div>' +
            '<input type="text" name="newCODricovero" placeholder="New Cod.." id="newCODricovero' + index + '" value="' + codRicovero + '"> ' +
            '<input type="text" name="newCODospedale" placeholder="New Ospedale.." id="newCODospedale' + index + '"> ' +
            '<input type="text" name="newcosto" placeholder="New Costo.." id="newcosto' + index + '"> ' +
            '<input type="date" name="newdata" id="newdata' + index + '"> ' +
            '<input type="number" name="newdurataRicovero" placeholder="New Durata.." id="newdurataRicovero' + index + '"> ' +
            '<input type="text" name="newmotivo" placeholder="New Motivo.." id="newmotivo' + index + '"> ' +
            '<input type="text" name="newCSSN" placeholder="New CSSN.." id="newCSSN' + index + '"> ' +
            '<input type="hidden" name="id_ricovero" value="' + codRicovero + '">' +
            '<button class="submit-data" type="submit"></button>' +        
            '</div>' +
            '</form>'
        );

        element.addClass("openedDiv");
        element.after(newElement);
        newElement.slideDown(); // Slide down effect for the new form
    }
}

// Function to delete a record
function deleteRecord(codRicovero) {
	if (!confirm("Sei sicuro di voler eliminare questo ricovero?")) {
	    event.preventDefault(); // Annulla l'invio del modulo
	}

}

// Add event listener for adding new records
document.addEventListener(onclick, function() {
    document.getElementById("btn-adder").onclick = createAdding();
});

// Function to create a new record form
function createAdding() {
    var parent = $('#adder')
    if (!parent) {
        console.error("Element with ID 'adder' not found.");
        return;
    }
    var newadder = $("#newAdding");
    newadder = $( // Create a new form for adding records
        '<div class="adder-ricovero" id="newRecord">'+
            '<button class="deleter" onclick=deleteNewRecord() id="deleteNewRecord"> &#10005; </button>'+
            '<form method="post" action="/addRecord" class="formNewRecord">'+
            '<input type="text" name="newCODricovero" placeholder="Cod. ricovero">'+
            '<input type="text" name="newCODospedale" placeholder="Cod. ospedale">'+
            '<input type="text" name="newcosto" placeholder="Costo">'+
            '<input type="date" name="newdata" placeholder="Costo">'+
            '<input type="number" name="newdurataRicovero" placeholder="Durata prevista">'+
            '<input type="text" name="newmotivo" placeholder="Cod patologia o nuovo">'+
            '<input type="text" name="newCSSN" placeholder="CSSN paziente">'+
            '<button class="adding-btn" type="submit" id="submitNewRecord" onclick=reloadPage()> &#43; </button>'+
        '</form>'+
        '</div>');
    parent.after(newadder); // Add new record form after the parent element
    document.getElementsByClassName("adding-crud")[0].style.display = "none"; // Hide add button
}

// Reload the page
function reloadPage() {
    window.location.reload();
}

// Function to delete the new record form
function deleteNewRecord() {
    document.getElementById("newRecord").style.display = "none"; // Hide new record form
    document.getElementById("adder").style.display = "flex"; // Show the add button
}

// Save scroll position before leaving the page
window.addEventListener('beforeunload', function() {
    sessionStorage.setItem('scrollPosition', window.scrollY);
});

// Restore scroll position after loading the page
window.addEventListener('load', function() {
    var scrollPosition = sessionStorage.getItem('scrollPosition');
    if (scrollPosition) {
        window.scrollTo(0, scrollPosition); // Scroll to the saved position
        sessionStorage.removeItem('scrollPosition'); // Remove the saved position from session storage
    }
});
