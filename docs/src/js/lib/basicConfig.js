export var server = "http://localhost:9000";
export var genericPlayername = "_NOT_A_PLAYER_";
export var genericLaddername = "_NOT_A_LADDERBOARD_";

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