var token = "";
var server = "https://77.231.246.1:9080/QuiniRestService";
//var server = "http://localhost:9000";
var teams = getTeams();
var matches = getMatches();
var groups = getGroups();
var genericPlayername = "_NOT_A_PLAYER_"
var genericLaddername = "_NOT_A_LADDERBOARD_"

var myheaders = new Headers({ 'Content-Type': 'application/json' });

var postData = {
    method: 'POST',
    headers: myheaders,
    mode: 'cors',
    cache: 'default'
};

var getData = {
    method: 'GET',
    headers: myheaders,
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
        return JSON.parse(request.responseText);
    }
    return null;
}

function getGroups() {
    var request = new XMLHttpRequest();
    request.open('GET', server + '/group/all', false);
    request.send(null);

    if (request.status === 200) {
        return JSON.parse(request.responseText);
    }
    return null;
}

function getMatches() {
    var request = new XMLHttpRequest();
    request.open('GET', server + '/match/all', false);
    request.send(null);

    if (request.status === 200) {
        return JSON.parse(request.responseText);
    }
    return null;
}

function getPlayerMatches(username, ladder, callback) {
    if (token == "") {
        fetch(server + '/user/playermatches?username=' + username + '&laddername=' + ladder, getData)
            .then(function (response) {
                return response.json();
            }).then(function (res) { callback(res) });
    } else {
        postData.body = JSON.stringify({ "token": token, "username": username, "laddername": ladder });
        fetch(server + '/user/playermatches', postData)
            .then(function (response) {
                return response.json();
            }).then(function (res) {
                callback(res)
            });
    }

}

function getPlayerGroups(username, ladder, callback) {
    if (token == "") {
        fetch(server + '/user/playergroups?username=' + username + '&laddername=' + ladder, getData)
            .then(function (response) {
                return response.json();
            }).then(function (res) { callback(res) });
    } else {
        postData.body = JSON.stringify({ "token": token, "username": username, "laddername": ladder });
        fetch(server + '/user/playergroups', postData)
            .then(function (response) {
                return response.json();
            }).then(function (res) {
                callback(res)
            });
    }

}

function postUpdatePlayerStatus(username, laddername, admin, active, callback) {
    postData.body = JSON.stringify({ "token": token, "username": username, "activate": active, "admin": admin, "laddername": laddername });
    fetch(server + '/user/updateplayerstatus', postData)
        .then(function (response) {
            return response.json();
        }).then(function (res) {
            callback(res)
        });
}

function getPlayerLadders(callback) {
    fetch(server + '/user/ladders', getData)
        .then(function (response) {
            return response.json();
        }).then(function (res) {
            callback(res)
        });
}

function postPlayerLadders(callback) {
    postData.body = JSON.stringify({ "token": token });
    fetch(server + '/user/ladders', postData)
        .then(function (response) {
            return response.json();
        }).then(function (res) {
            callback(res)
        });
}

function postCreateLadder(laddername, password, callback) {
    postData.body = JSON.stringify({ "token": token, "nameladder": laddername, "password": password });
    fetch(server + '/user/createladder', postData)
        .then(function (response) {
            return response.json();
        }).then(function (res) {
            callback(res)
        });
}

function postJoinLadder(laddername, password, callback) {
    postData.body = JSON.stringify({ "token": token, "nameladder": laddername, "password": password });
    fetch(server + '/user/joinladder', postData)
        .then(function (response) {
            return response.json();
        }).then(function (res) {
            callback(res)
        });
}

function postPlayerLaddersDetail(laddername, callback) {
    if (token != "") {
        postData.body = JSON.stringify({ "token": token, "ladderName": laddername });
        fetch(server + '/user/ladders/detail', postData)
            .then(function (response) {
                return response.json();
            }).then(function (res) {
                callback(res)
            });
    } else {
        fetch(server + '/user/ladders/detail?laddername=' + laddername, getData)
            .then(function (response) {
                return response.json();
            }).then(function (res) {
                callback(res)
            });
    }
}


function getPlayerLogin(username, password, callback) {
    postData.body = JSON.stringify({ "username": username, "password": password });
    fetch(server + '/login/signin', postData)
        .then(function (response) {
            return response.json();
        }).then(function (res) {
            if (res.token != null) token = res.token;
            callback(res)
        });
}

function getPlayerRegister(username, password, callback) {
    postData.body = JSON.stringify({ "username": username, "password": password });
    fetch(server + '/login/signup', postData)
        .then(function (response) {
            return response.json();
        }).then(function (res) {
            if (res.token != null) token = res.token;
            callback(res)
        });
}


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