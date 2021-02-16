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

function panelClickHandler(event) {
    const panelHeader = event.currentTarget.querySelector(".panelHeader");

    if(panelHeader){
        const platformDropdown = document.getElementById("platformDropdown");
        const descriptionTextBox = document.getElementById("reviewTextBox");
        const watchedCheckbox = document.getElementById("watchedCheckbox");

        // populate popup with media entry's values
        watchedCheckbox.checked = (panelHeader.getAttribute("watched") == 'true');
        platformDropdown.value = panelHeader.getAttribute("platform");
        descriptionTextBox.value = unescape(panelHeader.getAttribute("description"));

        // begin populating MediaEntry obj for updating
        let mediaEntry = {};
        mediaEntry.entryId = panelHeader.getAttribute("entryId");
        mediaEntry.type = panelHeader.getAttribute("mediaType");
        mediaEntry.title = unescape(panelHeader.getAttribute("title"));
        mediaEntry.imageUrl = event.currentTarget.querySelector('img').src;
        mediaEntry.username = window.sessionStorage.getItem("TvTrackerUsername");

        // send updated MediaEntry obj on save button click
        document.getElementById("saveBtn").onclick = function(){
            mediaEntry.platform = platformDropdown.value;
            mediaEntry.description = descriptionTextBox.value;
            mediaEntry.watched = watchedCheckbox.checked;

            updateMediaEntry(mediaEntry);
        }.bind(this);
    }

    showPopup();
}

function addPanelClickEvents() {
    let panels = document.getElementsByClassName("e-panel-container");

    for(let i=0; i<panels.length; i++){
        panels[i].onclick = panelClickHandler.bind(this);
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
            header: '<div title=' + escape(item.title) + ' entryId=' + item.entryId + ' description='
                + escape(item.description) + ' platform=' + item.platform + ' watched='
                + item.watched + ' mediaType=' + item.type + ' class="panelHeader">' + item.title + '</div>',
            content: '<div class="panelContent"><img src=' + imgUrl + ' class="mediaImg"></div>'
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

function startup() {
    const popupCloseBtn = document.getElementById("popupCloseBtn");
    popupCloseBtn.onclick = hidePopup

    getUsersMediaEntries();
}

