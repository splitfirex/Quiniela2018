export var server = "http://localhost:9000";
export var genericPlayername = "MAIN_PLAYER";
export var genericLaddername = "MAIN_LEADERBOARD";

export var postData = {
    method: 'POST',
    headers: new Headers({ 'Content-Type': 'application/json' }),
    mode: 'cors',
    cache: 'default'
};

export var getData = {
    method: 'GET',
    headers: new Headers({ 'Content-Type': 'application/json' }),
    mode: 'cors',
    cache: 'default'
};

export default server;