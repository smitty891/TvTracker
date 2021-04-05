async function verifyAuthentication(){
    return  await authenticate(window.sessionStorage.getItem("TvTrackerUsername"), null, window.sessionStorage.getItem("TvTrackerToken"));
}

/**
 * @param username
 * @param password
 * @param token
 * @returns {Promise<unknown>}
 */
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
                }

                resolve(result.target.status);
            }
        }.bind(this);

        xhr.send(null);
    });
}

