var token = "";
var server = "http://localhost:9000";
var teams = {};
var matches = {};
var groups = {};

var postData = {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    mode: 'cors',
    cache: 'default'
};

var getData = {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    mode: 'cors',
    cache: 'default'
};

loginRequest = new Request(server + '/login/signin');
registerRequest = new Request(server + '/login/signup');


function getTeams() {
    var request = new XMLHttpRequest();
    request.open('GET', server + '/team/all', false);
    request.send(null);

    if (request.status === 200) {
        teams = JSON.parse(request.responseText);
    }
}

function getGroups() {
    var request = new XMLHttpRequest();
    request.open('GET', server + '/group/all', false);
    request.send(null);

    if (request.status === 200) {
        groups = JSON.parse(request.responseText);
    }
}

function getMatches() {
    var request = new XMLHttpRequest();
    request.open('GET', server + '/match/all', false);
    request.send(null);

    if (request.status === 200) {
        matches = JSON.parse(request.responseText);
    }
}

function getPlayerMatches(username,ladder) {
    var request = new XMLHttpRequest();
    request.open('GET', server + '/user/playermatches?username='+username+'&laddername='+ladder, false);
    request.send(null);

    if (request.status === 200) {
        return JSON.parse(request.responseText);
    }
    return null;
}

function getPlayerGroups(username,ladder) {
    var request = new XMLHttpRequest();
    request.open('GET', server + '/user/playergroups?username='+username+'&laddername='+ladder, false);
    request.send(null);

    if (request.status === 200) {
        return JSON.parse(request.responseText);
    }
    return null;
}


function getPlayerLadderBoards() {
    var request = new XMLHttpRequest();
    request.open('POST', server + '/user/playergroups?username='+username+'&laddername='+ladder, false);
    request.send(null);

    if (request.status === 200) {
        return JSON.parse(request.responseText);
    }
    return null;
}

getTeams();
getGroups();
getMatches();


function tryFetch(request, inputData, reactjs, postRequest) {
    var RequestData = null;
    if (postRequest) {
        RequestData = postData;
        RequestData.body = JSON.stringify(inputData);
    } else {
        RequestData = getData;
        RequestData.body = JSON.stringify(inputData);
    }
    return fetch(request, RequestData)
        .then(function (res) {
            if (!res.ok) throw Error(res.statusText);
            return res.text();
        })
        .then(function (res) {
            if (res == "{}") throw Error("Error empty response");
            return JSON.parse(res);
        })
        .then(function (data) {
            if (data.token != null) token = data.token;
            this.getResponse(data);
        }.bind(reactjs))
        .catch(function (error) {
            this.getResponseError(error);
        }.bind(reactjs));
}