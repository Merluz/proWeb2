// script.js

document.addEventListener('DOMContentLoaded', function() {
    console.log('Document is ready');

    // Example: Change button color on click
    const button = document.querySelector('button');
    if (button) {
        button.addEventListener('click', function() {
            button.style.backgroundColor = '#28a745'; // Change color on click
        });
    }
});

// Example function to toggle dark mode
function toggleDarkMode() {
    const body = document.body;
    body.classList.toggle('dark-mode');
}

// Add event listener for dark mode toggle button if it exists
const darkModeButton = document.querySelector('#dark-mode-toggle');
if (darkModeButton) {
    darkModeButton.addEventListener('click', toggleDarkMode);
}

document.addEventListener('load', activeTag());
    function activeTag(){
    var url = window.location.href;
    var navTags = document.getElementsByClassName("sidebar-link");
    
    Array.from(navTags).forEach(a => {
        var aHref = a.getAttribute("href");
        aHref=aHref.substring(2);
		if(aHref=="/"){
			a.classList.add("active");
		}
       	else{
            a.classList.add("active");
        }
    });
}
function openModify(index, codRicovero) {
 	var previous = document.getElementsByClassName("openedDiv");
    if(previous.length != 0){
        $(previous[0]).css({
            "margin-bottom" : "5px",
            "border-radius" : "15px",
            "border-bottom" : "none"
        }).removeClass("openedDiv");
    }
    var id = "rec-" + index;
    var element = $("#" + id);
    var theme = "<?php echo $_SESSION['theme']; ?>";
    if (theme == "blue") {
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
    if(openMod.length == 0){
        createElement(index, element, codRicovero);
    } else {
        $(openMod).remove();
        createElement(index, element, codRicovero);
    }
}


function createElement(index, element, codRicovero) {
    var newElement = $("#new" + index);
    if(!newElement.length) {
        newElement = $(
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
        newElement.slideDown(); 
    }
}

function deleteRecord(codRicovero) {
    if (confirm("Sei sicuro di voler eliminare questo record?")) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "../php/deleteRecord.php", true); 
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                window.location.reload();
            }
        };
        xhr.send("CODricovero=" + encodeURIComponent(codRicovero));
    }
}

document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("btn-adder").onclick = createAdding;
});

function createAdding() {
    var parent = $('#adder')
    if (!parent) {
        console.error("Element with ID 'adder' not found.");
        return;
    }
    var newadder = $("#newAdding");
    newadder = $(
        '<div class="adder-ricovero" id="newRecord">'+
            '<button class="deleter" onclick=deleteNewRecord() id="deleteNewRecord"> &#10005; </button>'+
            '<form method="post" action="../php/addRecord.php" class="formNewRecord">'+
            '<input type="text" name="newCODricovero" placeholder="Cod. ricovero">'+
            '<input type="text" name="newCODospedale" placeholder="Cod. ospedale">'+
            '<input type="text" name="newcosto" placeholder="Costo">'+
            '<input type="date" name="newdata" placeholder="Costo">'+
            '<input type="number" name="newdurataRicovero" placeholder="Durata prevista">'+
            '<input type="text" name="newmotivo" placeholder="Cod patologia o nuovo">'+
            '<input type="text" name="newCSSN" placeholder="CSSN paziente">'+
            '<button class="adding-btn" type="submit" id="submitNewRecord" onclick=reloadPage()> &#43; </button>'+
        '<form>'+
        '</div>');
    parent.after(newadder);
    document.getElementsByClassName("adding-crud")[0].style.display="none";
}
function reloadPage(){
    window.location.reload();
}

function deleteNewRecord(){
    document.getElementById("newRecord").style.display="none";
    document.getElementById("adder").style.display="flex";
}


window.addEventListener('beforeunload', function() {
    sessionStorage.setItem('scrollPosition', window.scrollY);
});


window.addEventListener('load', function() {
    var scrollPosition = sessionStorage.getItem('scrollPosition');
    if (scrollPosition) {
        window.scrollTo(0, scrollPosition);
        sessionStorage.removeItem('scrollPosition');
    }
});

