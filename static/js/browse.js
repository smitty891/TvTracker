
function saveMediaEntry(mediaEntry) {
    const username = window.sessionStorage.getItem("TvTrackerUsername");
    const token = window.sessionStorage.getItem("TvTrackerToken");
    const URL = "/addMediaEntry?username=" + username + "&token=" + token

    const xhr = new XMLHttpRequest();
    xhr.open('POST', URL, true);

    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function (result) {
        if(result && result.target ) {
            if (result.target.status === 200) {
                console.log("success");
            } else if (result.target.status === 401) {
                alert("Please Sign In");
                hideSignInMessage();
            }
        }
        hideSpinner();
    }.bind(this);

    showSpinner();
    xhr.send(JSON.stringify(mediaEntry));
}

function panelClickHandler(event) {
    const panelHeader = event.currentTarget.querySelector(".panelHeader");
    const panelContent = event.currentTarget.querySelector(".panelContent");

    if(panelHeader && panelContent){
        // preview movie poster
        const movieTitle = document.getElementById("movieTitle");
        const moviePoster = document.getElementById("moviePoster");

        movieTitle.innerText = unescape(panelHeader.title);
        moviePoster.src = panelContent.querySelector("img").src;

        let mediaEntry = {};
        mediaEntry.entryId = panelHeader.getAttribute("imdbid").replace( /^\D+/g, '');
        mediaEntry.title = panelHeader.getAttribute("title");
        mediaEntry.imageUrl = event.currentTarget.querySelector('img').src;
        mediaEntry.type = this.currentMediaType
        mediaEntry.username = window.sessionStorage.getItem("TvTrackerUsername");

        document.getElementById("saveBtn").onclick = function(){
            mediaEntry.platform = document.getElementById("platformDropdown").value;
            mediaEntry.description = escape(document.getElementById("reviewTextBox").value);
            mediaEntry.watched = document.getElementById("watchedCheckbox").checked;

            saveMediaEntry(mediaEntry);

            closePopup();
        }.bind(this);
    }

    showPopup()
}

function showPopup() {
    const modal = document.getElementById("modelPopup");
    modal.style.display = "block";
}

function closePopup(){
    clearPopupInputs();

    const modal = document.getElementById("modelPopup");
    modal.style.display = "none";
}

function clearPopupInputs() {
    document.getElementById("platformDropdown").value = "null";
    document.getElementById("reviewTextBox").value = "";
    document.getElementById("watchedCheckbox").checked = false;
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

function addDashboardPanels(mediaItems) {
    buildDashboard();

    let row = 0;
    let col = 0;
    for(let i=0; i<mediaItems.length; i++){
        const item = mediaItems[i];
        const imgUrl = item.Poster === 'N/A' ? '/images/noImage.png' : item.Poster;

        const panel = {
            'row': row,
            'col': col++,
            'sizeX': 1,
            'sizeY': 1,
            header: '<div title=' + escape(item.Title) + ' year=' + item.Year + ' imdbID=' + item.imdbID + ' class="panelHeader">' + item.Title + '&nbsp;(' + item.Year + ')</div>',
            content: '<div class="panelContent pointerCursor"><img src=' + imgUrl + ' class="mediaImg"></div>'
        };

        this.dashboard.addPanel(panel);

        if(col>4){
            row++;
            col = 0;
        }
    }

    addPanelClickEvents();
}

function callSearchAPI(text, type, page) {
    const API_KEY = "4ccbc10367msh3a0aae042eaac2ep10144bjsn5bf582dc9c1c";
    const API_HOST = "movie-database-imdb-alternative.p.rapidapi.com";
    const URL = "https://" + API_HOST + "/?s=" + escape(text) + "&page=" + page + "&r=json&type=" + type;
    let totalResults = 0;
    let resultNo= document.getElementById("results");


    const xhr = new XMLHttpRequest();
    xhr.open('GET', URL, true);
    xhr.setRequestHeader("x-rapidapi-key", API_KEY);
    xhr.setRequestHeader("x-rapidapi-host", API_HOST);


    xhr.onload = function (result) {
        if(result && result.target && result.target.response){
            const response = JSON.parse(result.target.response);
            if(response.Response === "True") {
                totalResults = response.totalResults;
                addDashboardPanels.call(this, response.Search);
            } else {
                addDashboardPanels.call(this, []);
            }

            this.nextPageBtn.style.visibility = totalResults > (10*this.page) ? 'visible' : 'hidden';
            this.prevPageBtn.style.visibility = this.page > 1 ? 'visible' : 'hidden';
        }
        hideSpinner();
        enableButtons();
        resultNo.innerHTML= totalResults.toString()+" Search results for "+text

    }.bind(this);

    showSpinner();
    disableButtons();
    xhr.send(null);
}


function searchBtnClickHandler(){
    let searchTextBox = document.getElementById("searchTextBox");
    let mediaTypeDropdown = document.getElementById('mediaTypeDropdown');
    this.currentMediaType = mediaTypeDropdown.value;
    this.currentSearchText = searchTextBox.value;
    this.page = 1;
    callSearchAPI.call(this, this.currentSearchText, this.currentMediaType, this.page);
    let h1 = document.getElementById("BrowseH1");
    changePageTitle(h1, this.currentMediaType);
}

function searchOnStartUp(){
    this.currentMediaType = "Movie";
    this.currentSearchText = "Movie";
    this.page = 1;
    callSearchAPI.call(this, this.currentSearchText, this.currentMediaType, this.page);
}

function nextPageBtnClickHandler(){
    callSearchAPI.call(this, this.currentSearchText, this.currentMediaType, ++this.page);
}

function prevPageBtnClickHandler(){
    callSearchAPI.call(this, this.currentSearchText, this.currentMediaType, --this.page);
}

function bindClickHandlers(){
    const popupCloseBtn = document.getElementById("popupCloseBtn");
    popupCloseBtn.onclick = closePopup;

    this.searchBtn = document.getElementById("searchBtn");
    this.searchBtn.onclick = searchBtnClickHandler.bind(this);


    this.nextPageBtn = document.getElementById("nextPageBtn");
    this.nextPageBtn.onclick = nextPageBtnClickHandler.bind(this);

    this.prevPageBtn = document.getElementById("prevPageBtn");
    this.prevPageBtn.onclick = prevPageBtnClickHandler.bind(this);
}

function disableButtons() {
    document.getElementById("nextPageBtn").disabled = true;
    document.getElementById("prevPageBtn").disabled = true;
    document.getElementById("searchBtn").disabled = true;
}

function enableButtons() {
    document.getElementById("nextPageBtn").disabled = false;
    document.getElementById("prevPageBtn").disabled = false;
    document.getElementById("searchBtn").disabled = false;
}

async function startUp(){
    bindClickHandlers();
    searchOnStartUp();
}
function changePageTitle(title, spinner){
    let value= spinner;
    if (value=="series"){
        title.innerText= "Browse Series";
    }else {
        title.innerText= "Browse Movies";
    }

}

