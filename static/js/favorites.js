function updateMediaEntry(mediaEntry) {
    const username = window.sessionStorage.getItem("TvTrackerUsername");
    const token = window.sessionStorage.getItem("TvTrackerToken");
    const URL = "/editMediaEntry?username=" + username + "&token=" + token

    const xhr = new XMLHttpRequest();
    xhr.open('PUT', URL, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function (result) {
        if(result && result.target ) {
            if (result.target.status === 200) {
                getUsersMediaEntries();
            } else if (result.target.status === 401) {
                alert("Please Sign In");
                hideSignInMessage();
            }

            if (result.target.status !== 200) {
                hideSpinner();
            }
        }
    }.bind(this);

    showSpinner();
    xhr.send(JSON.stringify(mediaEntry));
}

function deleteMediaEntry(mediaEntry) {
    const username = window.sessionStorage.getItem("TvTrackerUsername");
    const token = window.sessionStorage.getItem("TvTrackerToken");
    const URL = "/removeMediaEntry?username=" + username + "&token=" + token

    const xhr = new XMLHttpRequest();
    xhr.open('DELETE', URL, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function (result) {
        if(result && result.target ) {
            if (result.target.status === 200) {
                getUsersMediaEntries();
            } else if (result.target.status === 401) {
                alert("Please Sign In");
                hideSignInMessage();
            }

            if (result.target.status !== 200) {
                hideSpinner();
            }
        }
    }.bind(this);

    showSpinner();
    xhr.send(JSON.stringify(mediaEntry));
}

function editIconClickHandler(event) {
    const infoElement = event.currentTarget;

    if(infoElement){
        // get mediaEntry
        const entryId = infoElement.getAttribute("entryId");
        let mediaEntry = this.mediaEntries.find(entry => entry.id == entryId);

        if(!mediaEntry){
            alert("Error");
            return;
        }

        // preview movie poster
        const movieTitle = document.getElementById("movieTitle");
        const moviePoster = document.getElementById("moviePoster");
        movieTitle.innerText = unescape(mediaEntry.title);
        moviePoster.src = mediaEntry.imageUrl;

        // populate popup with media entry's values
        const platformDropdown = document.getElementById("platformDropdown");
        const descriptionTextBox = document.getElementById("reviewTextBox");
        const watchedCheckbox = document.getElementById("watchedCheckbox");

        watchedCheckbox.checked = mediaEntry.watched;
        platformDropdown.value = mediaEntry.platform
        descriptionTextBox.value = unescape(mediaEntry.description);

        // send updated MediaEntry obj on save button click
        document.getElementById("saveBtn").onclick = function(){
            mediaEntry.platform = platformDropdown.value;
            mediaEntry.description = escape(descriptionTextBox.value);
            mediaEntry.watched = watchedCheckbox.checked;

            updateMediaEntry(mediaEntry);

            hidePopup();
            clearPopupInputs();
        }.bind(this);
    }

    showPopup();
}

function deleteIconClickHandler(event) {
    const entryId = event.currentTarget.getAttribute("entryId");
    let mediaEntry = this.mediaEntries.find(entry => entry.id == entryId);

    if(!mediaEntry){
        alert("Error");
        return;
    }

    const deleteEntry = confirm("Proceeding will permanently remove this media entry.");

    if (deleteEntry == true) {
        deleteMediaEntry(mediaEntry);
    }
}

function addPanelClickEvents() {
    const editNodes = document.getElementsByClassName("fa-pencil");
    for(let i=0; i<editNodes.length; i++){
        const editNode = editNodes[i];
        editNode.onclick = editIconClickHandler.bind(this);
    }

    const deleteNodes = document.getElementsByClassName("fa-trash");
    for(let i=0; i<deleteNodes.length; i++){
        const deleteNode = deleteNodes[i];
        deleteNode.onclick = deleteIconClickHandler.bind(this);
    }
}

function buildDashboard() {
    if(this.dashboard)
        this.dashboard.destroy();

    this.dashboard = new ej.layouts.DashboardLayout({
        cellSpacing:[5,5],
        columns: 5,
        cellAspectRatio: 50 / 30,
        allowDragging: false,
        panels: []
    });

    this.dashboard.appendTo("#dashDiv");
}

function buildPanelHeader(item) {
    const watchedCheckVisibility = (item.watched  ? 'visible' : 'hidden');

    let header = '<div>'
        + '<div class="watchedCheckIcon" style="visibility: ' + watchedCheckVisibility + '">'
        + '<i class="fa fa-check"></i>'
        + '</div>'
        + '<div class="rightSideIcons">'
        + '<i class="fa fa-pencil" entryId=' + item.id + ' title="Edit" style="cursor:pointer;"></i>'
        + '<i class="fa fa-trash" entryId=' + item.id + ' title="Delete" style="padding-left:5px;cursor:pointer;"></i>'
        + '</div>'
        + '<div class="favoritesTitle">' + unescape(item.title) + '</div>'
        + '</div>';

    return header
}

function addDashboardPanels() {
    buildDashboard();

    let row = 0;
    let col = 0;
    for(let i=0; i<this.mediaEntries.length; i++){
        const item = this.mediaEntries[i];
        const imgUrl = item.imageUrl === 'N/A' ? '/images/noImage.png' : item.imageUrl;

        const panel = {
            'row': row,
            'col': col++,
            'sizeX': 1,
            'sizeY': 1,
            header: buildPanelHeader(item),
            content: '<div class="panelContent">'
                        + '<img src=' + imgUrl + ' class="mediaImg">'
                    + '</div>'
                    + '<div class="panelDescription">' + unescape(item.description) + '</div>'
        };

        this.dashboard.addPanel(panel);

        if(col>4){
            row++;
            col = 0;
        }
    }

    addPanelClickEvents();
}

function getUsersMediaEntries() {
    const username = window.sessionStorage.getItem("TvTrackerUsername");
    const token = window.sessionStorage.getItem("TvTrackerToken");
    const URL = "/getMediaEntries?username=" + username + "&token=" + token;

    const xhr = new XMLHttpRequest();
    xhr.open('GET', URL, true);

    xhr.onload = function (result) {
        if(result && result.target && result.target.status === 200 && result.target.response){
            const response = JSON.parse(result.target.response);
            this.mediaEntries = response ?? [];
            addDashboardPanels.call(this);
        }
        hideSpinner();
    }.bind(this);

    showSpinner();
    xhr.send(null);
}

function showPopup() {
    const modal = document.getElementById("modelPopup");
    modal.style.display = "block";
}

function hidePopup() {
    const modal = document.getElementById("modelPopup");
    modal.style.display = "none";
}

function clearPopupInputs() {
    document.getElementById("platformDropdown").value = "";
    document.getElementById("reviewTextBox").value = "";
    document.getElementById("watchedCheckbox").checked = false;
}

async function startUp() {
    const popupCloseBtn = document.getElementById("popupCloseBtn");
    popupCloseBtn.onclick = hidePopup

    // load favorites when user signs in
    window.onmessage = function(e){
        if (e.data == 'signedIn') {
            getUsersMediaEntries();
        }
    };

    // load favorites if user is already signed in
    let statusCode = await verifyAuthentication();
    if(statusCode === 200) {
        getUsersMediaEntries();
    } else if (statusCode === 401) {
        hideSignInMessage();
        alert("Please Sign In");
    }
}
