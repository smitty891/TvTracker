function showSignInMessage(){
    window.top.postMessage('showSignInMessage', '*')
}

function hideSignInMessage(){
    window.top.postMessage('hideSignInMessage', '*')
}

function showSpinner(){
    window.top.postMessage('showSpinner', '*')
}

function hideSpinner(){
    window.top.postMessage('hideSpinner', '*')
}