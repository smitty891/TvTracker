window.sessionStorage.setItem("TvTrackerUsername", "testUser");

function updateMediaEntry(mediaEntry) {
    const username = window.sessionStorage.getItem("TvTrackerUsername");
    const token = window.sessionStorage.getItem("TvTrackerToken");
    const URL = "/editMediaEntry/" + username + "/" + token;

    const xhr = new XMLHttpRequest();
    xhr.open('PUT', URL, true);

    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function (result) {
        if(result && result.target && result.target.status === 200){
            getUsersMediaEntries();
        }
    }.bind(this);

    xhr.send(JSON.stringify(mediaEntry));
}

function deleteMediaEntry(entryId) {
    const username = window.sessionStorage.getItem("TvTrackerUsername");
    const token = window.sessionStorage.getItem("TvTrackerToken");
    const URL = "/removeMediaEntry/" + entryId + "/" + username + "/" + token;

    const xhr = new XMLHttpRequest();
    xhr.open('DELETE', URL, true);

    //xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function (result) {
        if(result && result.target && result.target.status === 200){
            getUsersMediaEntries();
        }
    }.bind(this);

    xhr.send(null);
}

function editIconClickHandler(event) {
    const infoElement = event.currentTarget;

    if(infoElement){
        const platformDropdown = document.getElementById("platformDropdown");
        const descriptionTextBox = document.getElementById("reviewTextBox");
        const watchedCheckbox = document.getElementById("watchedCheckbox");

        // populate popup with media entry's values
        watchedCheckbox.checked = (infoElement.getAttribute("watched") == 'true');
        platformDropdown.value = infoElement.getAttribute("platform");
        descriptionTextBox.value = unescape(infoElement.getAttribute("description"));

        // begin populating MediaEntry obj for updating
        let mediaEntry = {};
        mediaEntry.entryId = infoElement.getAttribute("entryId");
        mediaEntry.type = infoElement.getAttribute("mediaType");
        mediaEntry.title = unescape(infoElement.getAttribute("mediaTitle"));
        mediaEntry.imageUrl = infoElement.getAttribute("imageUrl");
        mediaEntry.username = window.sessionStorage.getItem("TvTrackerUsername");

        // send updated MediaEntry obj on save button click
        document.getElementById("saveBtn").onclick = function(){
            mediaEntry.platform = platformDropdown.value;
            mediaEntry.description = descriptionTextBox.value;
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

    const deleteEntry = confirm("Proceeding will permanently remove this media entry.");

    if (deleteEntry == true) {
        deleteMediaEntry(entryId);
    } else {
        console.log("You pressed Cancel!");
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

function addDashboardPanels(mediaEntries) {
    buildDashboard();

    let row = 0;
    let col = 0;
    for(let i=0; i<mediaEntries.length; i++){
        const item = mediaEntries[i];
        const imgUrl = item.imageUrl === 'N/A' ? '/images/noImage.png' : item.imageUrl;

        const panel = {
            'row': row,
            'col': col++,
            'sizeX': 1,
            'sizeY': 1,
            header: '<div>'
                    + '<div class="panelHeaderIcons">'
                        + '<i class="fa fa-pencil" mediaTitle=' + escape(item.title) + ' entryId=' + item.entryId + ' description='
                            + escape(item.description) + ' platform=' + item.platform + ' imageUrl=' + imgUrl +  ' watched='
                            + item.watched + ' mediaType=' + item.type + 'title="Edit" style="cursor:pointer;"></i>'
                        + '<i class="fa fa-trash" entryId=' + item.entryId + ' title="Delete" style="padding-left:5px;cursor:pointer;"></i>'
                    + '</div>'
                    + '<div class="favoritesTitle">' + item.title + '</div>'
                + '</div>',
            content: '<div class="panelContent">'
                        + '<img src=' + imgUrl + ' class="mediaImg">'
                    + '</div>'
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
    const URL = "/getMediaEntries/" + username + "/" + token;

    const xhr = new XMLHttpRequest();
    xhr.open('GET', URL, true);

    xhr.onload = function (result) {
        if(result && result.target && result.target.response){
            const response = JSON.parse(result.target.response);
            if(response) {
                addDashboardPanels.call(this, response);
            } else {
                addDashboardPanels.call(this, []);
            }

        }
    }.bind(this);

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

function startup() {
    const popupCloseBtn = document.getElementById("popupCloseBtn");
    popupCloseBtn.onclick = hidePopup

    getUsersMediaEntries();
}

