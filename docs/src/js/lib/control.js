var server = "http://192.168.0.156:9000";
var genericPlayername = "_NOT_A_PLAYER_";
var genericLaddername = "_NOT_A_LADDERBOARD_";

var postData = {
    method: 'POST',
    headers: new Headers({ 'Content-Type': 'application/json' }),
    mode: 'cors',
    cache: 'default'
};

var getData = {
    method: 'GET',
    headers: new Headers({ 'Content-Type': 'application/json' }),
    mode: 'cors',
    cache: 'default'
};

window.update = function (password, idmatch, homeScore, visitScore) {
    postData.body = JSON.stringify({ "password": password, idMatch: idmatch, homeScore: homeScore, visitScore: visitScore });
    fetch(server + '/user/updatemainmatch', postData)
        .then(function (response) {
            console.log("OK");
        });
}

window.resetUser = function (password, username) {
    postData.body = JSON.stringify({ "password": password, username:username });
    fetch(server + '/login/resetpassword', postData)
        .then(function (response) {
            console.log("OK");
        });
}

window.resetLadder = function (password, username) {
    postData.body = JSON.stringify({ "password": password, laddername:username });
    fetch(server + '/user/resetpassword', postData)
        .then(function (response) {
            console.log("OK");
        });
}