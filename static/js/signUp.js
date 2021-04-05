function signup() {
    let usernameTxt = document.getElementById("username");
    let emailTxt = document.getElementById("email");
    let birthDateSelect = document.getElementById("birthdate");
    let passwordTxt = document.getElementById("password");
    let confirmPassTxt = document.getElementById("confirmPassword");

    if(passwordTxt.value !== confirmPassTxt.value) {
        alert("Passwords do not match.");
        return;
    }

    let user = {
        username: usernameTxt.value,
        password: passwordTxt.value,
        email: emailTxt.value,
        birthDate: birthDateSelect.valueAsDate.toISOString(),
        token: "",
        lastLogin: null
    }

    createUser(user);
}

/**
 * @param user
 */
function createUser(user) {
    let xhr = new XMLHttpRequest();

    xhr.open("POST", "/signUp");
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onload = function (result) {
        if(result && result.target) {
            if (result.target.status === 201)
            {
                window.sessionStorage.setItem("TvTrackerToken", result.target.response);
                window.sessionStorage.setItem("TvTrackerUsername", user.username);
                showSignInMessage();
                alert("Successfully created user account and signed in.");
            }
            else if(result.target.status === 409)
            {
                alert("This username already exists.");
            }
            else {
                alert("Error occurred. User account was not created.");
            }
        }
    }.bind(this);

    xhr.send(JSON.stringify(user));
}