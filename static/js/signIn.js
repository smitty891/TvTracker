async function signIn(){
    const usernameTxt = document.getElementById("usernameTxt");
    const passwordTxt = document.getElementById("passwordTxt");

    const statusCode = await authenticate(usernameTxt.value, passwordTxt.value);

    if(statusCode === 200) {
        const event = new Event("signedIn", {"bubbles":true, "cancelable":false});
        document.dispatchEvent(event);
    }
}

async function verifyAuthentication(){
    return  await authenticate(window.sessionStorage.getItem("TvTrackerUsername"), null, window.sessionStorage.getItem("TvTrackerToken"));
}

function authenticate(username, password, token){
    return new Promise(function (resolve, reject) {
        let xhr = new XMLHttpRequest();

        let params = "username="+username;
        if(password) params += "&password="+password;
        if(token) params += "&token="+token;

        xhr.open("GET", "/authenticate?" + params);

        xhr.onload = function (result) {
            if (result && result.target) {
                if (result.target.status === 200) {
                    window.sessionStorage.setItem("TvTrackerToken", result.target.response);
                    window.sessionStorage.setItem("TvTrackerUsername", username);

                    showSignInMessage();
                }

                resolve(result.target.status);
            }
        }.bind(this);

        xhr.send(null);
    });
}

function signOut(){
    hideSignInMessage()
    window.sessionStorage.setItem("TvTrackerToken", undefined);
    window.sessionStorage.setItem("TvTrackerUsername", undefined);
}

function showSignInMessage(){
    let message = "Welcome Back, " + window.sessionStorage.getItem("TvTrackerUsername") + "!";

    let signUpLink = document.getElementById("signUpLink");
    let usernameTxt = document.getElementById("usernameTxt");
    let passwordTxt = document.getElementById("passwordTxt");
    let signInBtn = document.getElementById("signInBtn");
    let signInMessage = document.getElementById("signInMessage");
    let signOutBtn = document.getElementById("signOutBtn");

    signUpLink.style.setProperty('display', 'none');
    usernameTxt.style.setProperty('display', 'none');
    passwordTxt.style.setProperty('display', 'none');
    signInBtn.style.setProperty('display', 'none');

    signInMessage.innerHTML = message;
    signInMessage.style.setProperty('display', '');
    signOutBtn.style.setProperty('display', '');
}

function hideSignInMessage(){
    let signUpLink = document.getElementById("signUpLink");
    let usernameTxt = document.getElementById("usernameTxt");
    let passwordTxt = document.getElementById("passwordTxt");
    let signInBtn = document.getElementById("signInBtn");
    let signInMessage = document.getElementById("signInMessage");
    let signOutBtn = document.getElementById("signOutBtn");

    signUpLink.style.setProperty('display', '');
    usernameTxt.style.setProperty('display', '');
    passwordTxt.style.setProperty('display', '');
    signInBtn.style.setProperty('display', '');
    signInMessage.style.setProperty('display', 'none');
    signOutBtn.style.setProperty('display', 'none');
}