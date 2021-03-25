function startUp(){
    addSpinner();
    addListeners();
}

function addListeners(){
    window.onmessage = function (e) {
        if (e.data == 'showSpinner') {
            showSpinner();
        } else if (e.data == 'hideSpinner') {
            hideSpinner();
        }
    };
}

function addSpinner(){
    ej.popups.createSpinner({
        target: document.getElementById('spinner')
    });
}

function showSpinner(){
    ej.popups.showSpinner(document.getElementById('spinner'));
}

function hideSpinner(){
    ej.popups.hideSpinner(document.getElementById('spinner'));
}