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

export const GlobalAppActions = (state, action) => {

    switch (action.type) {
        case "CLOSE_MODAL":
            return { showModal: false }
        case "GO_BACK":
            window.scrollTo(0, 0);
            if (state.playername !== undefined) {
                return { subTitle: "Jugadores", showMenu: false, contentWindow: "PLAYERS", breadcrumbs: [state.laddername], playername: undefined }
            }
            return { subTitle: "Quinielas", showMenu: false, laddername: undefined, contentWindow: "LADDERS", showBreadcrumbs: false, breadcrumbs: [] }
        case "TOGGLE_MENU":
            return { showMenu: !state.showMenu }
        case "LOADING_PREFETCHS":
            return { showWelcome: true }
        case "SUCCESS_PREFETCHS":
            state[action.prefetch] = action.content;
            if (state.matches.length === 0 || state.groups.length === 0 || state.teams.length === 0) {
                return { ...state }
            }
            return { showWelcome: false }
        case "GO_TO":

            switch (action.dest) {
                case "SHOW_HOME":
                    window.scrollTo(0, 0);
                    return { subTitle: "Quinielas", playername: undefined, laddername: undefined, showMenu: false, contentWindow: "LADDERS", showBreadcrumbs: false }
                case "SHOW_GROUPS":
                    window.scrollTo(0, 0);
                    return { subTitle: "Grupos", playername: undefined, laddername: undefined, showMenu: false, contentWindow: "GROUPS", showBreadcrumbs: false }
                case "SHOW_MATCHES":
                    window.scrollTo(0, 0);
                    return { subTitle: "Partidos", playername: undefined, laddername: undefined, showMenu: false, contentWindow: "MATCHES", showBreadcrumbs: false }
                case "SHOW_PLAYERS":
                    window.scrollTo(0, 0);
                    return { subTitle: "Jugadores", laddername: action.laddername, showMenu: false, contentWindow: "PLAYERS", showBreadcrumbs: true, breadcrumbs: [action.laddername] }
                case "SHOW_PLAYER_MATCHES":
                    window.scrollTo(0, 0);
                    return { subTitle: "Partidos", playername: action.playername, laddername: action.laddername, showMenu: false, contentWindow: "MATCHES", showBreadcrumbs: true, breadcrumbs: [action.playername] }
                case "SHOW_PLAYER_GROUPS":
                    window.scrollTo(0, 0);
                    return { subTitle: "Grupos", playername: action.playername, laddername: action.laddername, showMenu: false, contentWindow: "GROUPS", showBreadcrumbs: true, breadcrumbs: [action.playername] }
                case "SIGN_IN":
                    return { showModal: true, showMenu: false, contentModalWindow: "SIGNIN" }
                case "SIGN_UP":
                    return { showModal: true, showMenu: false, contentModalWindow: "SIGNUP" }
                case "NEW_LADDER":
                    return { showModal: true, showMenu: false, contentModalWindow: "NEWLADDER" }
                case "JOIN_LADDER":
                    return { showModal: true, showMenu: false, contentModalWindow: "JOINLADDER", laddername: action.laddername, ladderProtected: action.ladderProtected }
                case "BAN_PLAYER":
                    return { showModal: true, showMenu: false, contentModalWindow: "BANPLAYER", laddername: action.laddername, playername: action.playername }
                case "LEAVE_LADDER":
                    return { showModal: true, showMenu: false, contentModalWindow: "LEAVELADDER", laddername: action.laddername }
                default:
                    return state
            }
        case "LOADING_CONTENT":
            return { forceReload: false, showLoading: true }
        case "TOGGLE_PUBLIC":
            return { public: !state.public }
        case "SUCCESS_CONTENT":
            return { showLoading: false, content: action.content }
        case "SUCCESS_LOGIN":
            window.scrollTo(0, 0);
            return { contentModalWindow: "", showBreadcrumbs: false, subTitle: "Quinielas", contentWindow: "LADDERS", showModal: false, showLoading: false, username: action.username, token: action.token }
        case "SUCCESS_LOGOUT":
            window.scrollTo(0, 0);
            return { showMenu: !state.showMenu, showBreadcrumbs: false, subTitle: "Quinielas", contentWindow: "LADDERS", showModal: false, showLoading: false, username: undefined, token: undefined }

        case "SUCCESS_CREATE":
            return { forceReload: true, contentModalWindow: "", showBreadcrumbs: true, breadcrumbs: [action.laddername], subTitle: action.laddername, contentWindow: "PLAYERS", showModal: false, laddername: action.laddername }
        case "SUCCESS_LEAVE":
        case "SUCCESS_JOIN":
            return { forceReload: true, showModal: false, subTitle: "Quinielas", playername: undefined, laddername: undefined, showMenu: false, contentWindow: "LADDERS", showBreadcrumbs: false }
        case "UNFORCE":
            return { forceReload: false }
        case "CLOSE_AND_RELOAD":
            return { forceReload: true, showModal: false }
        case "FAIL_PROCESS_PASSWORD":
            return { showLoading: false, errorPassword: true }
        default:
            return state;


    }

}

export const LoginAppActions = (state, action) => {
    switch (action.type) {
        case "LOADING_CONTENT":
            return { showLoading: true }
        case "FAIL_PROCESS":
            return { showLoading: false, error: true }
        default:
            return state
    }
}

export var prefetchMatches = function () {
    this.dispatch({ type: "LOADING_PREFETCHS" });
    fetch(server + "/match/all")
        .then(res => res.json())
        .then(function (json) {
            this.dispatch({ type: "SUCCESS_PREFETCHS", prefetch: "matches", content: json });
        }.bind(this));
}

export var prefetchGroups = function () {
    this.dispatch({ type: "LOADING_PREFETCHS" });
    fetch(server + "/group/all")
        .then(res => res.json())
        .then(function (json) {
            this.dispatch({ type: "SUCCESS_PREFETCHS", prefetch: "groups", content: json });
        }.bind(this));
}

export var prefetchTeams = function () {
    this.dispatch({ type: "LOADING_PREFETCHS" });
    fetch(server + "/team/all")
        .then(res => res.json())
        .then(function (json) {
            this.dispatch({ type: "SUCCESS_PREFETCHS", prefetch: "teams", content: json });
        }.bind(this));
}

export var fetchLadders = function () {
    this.dispatch({ type: "LOADING_CONTENT" });
    //if (this.props.token == undefined) {
    fetch(server + "/user/ladders", getData)
        .then(res => res.json())
        .then(function (json) {
            this.dispatch({ type: "SUCCESS_CONTENT", content: json });
        }.bind(this));
    /*} else {
        postData.body = JSON.stringify({ "token": this.props.token });
        fetch(server + "/user/ladders", postData)
            .then(res => res.json())
            .then(function (json) {
                this.dispatch({ type: "SUCCESS_CONTENT", content: json });
            }.bind(this));
    }*/
}

export var fetchMatches = function () {
    this.dispatch({ type: "LOADING_CONTENT" });
    if (this.props.token === undefined) {
        fetch(server + '/user/playermatches?username=' + (this.props.playername || genericPlayername) + '&laddername=' + (this.props.laddername || genericLaddername), getData)
            .then(res => res.json())
            .then(function (json) {
                this.dispatch({ type: "SUCCESS_CONTENT", content: json });
            }.bind(this));
    } else {
        postData.body = JSON.stringify({ "token": this.props.token, "username": (this.props.playername || genericPlayername), "laddername": (this.props.laddername || genericLaddername) });
        fetch(server + "/user/playermatches", postData)
            .then(res => res.json())
            .then(function (json) {
                this.dispatch({ type: "SUCCESS_CONTENT", content: json });
            }.bind(this));
    }
}

export var fetchGroups = function () {
    this.dispatch({ type: "LOADING_CONTENT" });
    if (this.props.token === undefined) {
        fetch(server + '/user/playergroups?username=' + (this.props.playername || genericPlayername) + '&laddername=' + (this.props.laddername || genericLaddername), getData)
            .then(res => res.json())
            .then(function (json) {
                this.dispatch({ type: "SUCCESS_CONTENT", content: json });
            }.bind(this));
    } else {
        postData.body = JSON.stringify({ "token": this.props.token, "username": (this.props.playername || genericPlayername), "laddername": (this.props.laddername || genericLaddername) });
        fetch(server + "/user/playergroups", postData)
            .then(res => res.json())
            .then(function (json) {
                this.dispatch({ type: "SUCCESS_CONTENT", content: json });
            }.bind(this));
    }
}

export var fetchPlayers = function () {
    this.dispatch({ type: "LOADING_CONTENT" });
    if (this.props.token === undefined) {
        fetch(server + '/user/ladders/detail?laddername=' + (this.props.laddername || genericLaddername), getData)
            .then(res => res.json())
            .then(function (json) {
                this.dispatch({ type: "SUCCESS_CONTENT", content: json });
            }.bind(this));

    } else {
        postData.body = JSON.stringify({ "token": this.props.token, "laddername": (this.props.laddername || genericLaddername) });
        fetch(server + '/user/ladders/detail', postData)
            .then(res => res.json())
            .then(function (json) {
                this.dispatch({ type: "SUCCESS_CONTENT", content: json });
            }.bind(this));
    }
}

export var fetchLogin = function () {
    this.dispatch({ type: "LOADING_CONTENT" });
    postData.body = JSON.stringify({ "username": this.state.username, "password": this.state.password });
    fetch(server + '/login/signin', postData)
        .then(function (response) {
            return response.json();
        }).then(function (json) {
            if (json.token !== undefined) {
                this.props.dispatch({ type: "SUCCESS_LOGIN", username: this.state.username, token: json.token });
            } else {
                this.dispatch({ type: "FAIL_PROCESS" });
            }
        }.bind(this));
}

export var fetchRegister = function () {
    if (this.state.password.length === 0) {
        this.dispatch({ type: "FAIL_PROCESS" });
        return;
    }
    this.dispatch({ type: "LOADING_CONTENT" });
    postData.body = JSON.stringify({ "username": this.state.username, "password": this.state.password });
    fetch(server + '/login/signup', postData)
        .then(function (response) {
            return response.json();
        }).then(function (json) {
            if (json.token !== undefined) {
                this.props.dispatch({ type: "SUCCESS_LOGIN", username: this.state.username, token: json.token });
            } else {
                this.dispatch({ type: "FAIL_PROCESS" });
            }
        }.bind(this));
}

export var fetchNewLadder = function () {
    if (!this.state.public && (this.state.password == undefined || this.state.password.length === 0)) {
        this.dispatch({ type: "FAIL_PROCESS_PASSWORD" });
        return;
    }
    this.dispatch({ type: "LOADING_CONTENT" });
    postData.body = JSON.stringify({ laddername: this.state.laddername, token: this.props.token, "password": this.state.password });
    fetch(server + '/user/createladder', postData)
        .then(function (response) {
            return response.json();
        }).then(function (json) {
            if (json.name !== undefined) {
                this.props.dispatch({ type: "SUCCESS_CREATE", laddername: json.name });
            } else {
                this.dispatch({ type: "FAIL_PROCESS" });
            }
        }.bind(this));
}

export var fetchJoinLadder = function () {
    if (this.props.ladderProtected && this.state.password.length === 0) {
        this.dispatch({ type: "FAIL_PROCESS_PASSWORD" });
        return;
    }
    this.dispatch({ type: "LOADING_CONTENT" });
    postData.body = JSON.stringify({ "token": this.props.token, "laddername": this.props.laddername, "password": this.state.password });
    fetch(server + '/user/joinladder', postData)
        .then(function (response) {
            return response.json();
        }).then(function (json) {
            if (json.name !== undefined) {
                this.props.dispatch({ type: "SUCCESS_JOIN" });
            } else {
                this.dispatch({ type: "FAIL_PROCESS_PASSWORD" });
            }
        }.bind(this));
}

export var fetchPlayerStatus = function (laddername, playername, admin, active) {
    postData.body = JSON.stringify({ "token": this.props.token, "username": playername, "activate": active, "admin": admin, "laddername": laddername });
    fetch(server + '/user/updateplayerstatus', postData)
        .then(function (response) {
            return response.json();
        }).then(function (res) {
            this.props.dispatch({ type: "CLOSE_AND_RELOAD" });
        }.bind(this));
}

export var fetchBanPlayer = function () {
    postData.body = JSON.stringify({ "token": this.props.token, "username": this.props.playername, "laddername": this.props.laddername });
    fetch(server + '/user/banplayer', postData)
        .then(function (response) {
            return response.json();
        }).then(function (res) {
            this.props.dispatch({ type: "CLOSE_AND_RELOAD" });
        }.bind(this));
}

export var fetchUpdateScore = function (index, homeScore, visitScore) {
    postData.body = JSON.stringify({ "token": this.props.token, laddername: this.props.laddername, "idMatch": index, "homeScore": homeScore, "visitScore": visitScore });
    fetch(server + '/user/updatematch', postData)
        .then(function (response) {
            return response.json();
        }).then(function (res) {
            this.dispatch({ type: "SUCCESS_CONTENT", content: res });
        }.bind(this));
}

export var fetchUpdateColor = function () {
    postData.body = JSON.stringify({ "token": this.props.token, laddername: this.props.laddername });
    fetch(server + '/user/updatecolor', postData)
        .then(function (response) {
            return response.json();
        }).then(function (res) {
            this.dispatch({ type: "SUCCESS_CONTENT", content: res });
        }.bind(this));
}

export var fetchLeaveLadder = function () {
    this.dispatch({ type: "LOADING_CONTENT" });
    postData.body = JSON.stringify({ "token": this.props.token, "laddername": this.props.laddername });
    fetch(server + '/user/leaveladder', postData)
        .then(function (response) {
            return response.json();
        }).then(function (res) {
            this.props.dispatch({ type: "SUCCESS_LEAVE" });
        }.bind(this));
}

export var fetchNextMatches = function () {
    this.dispatch({ type: "LOADING_CONTENT" });
    if (this.props.token === undefined) {
        fetch(server + '/user/nextmatches?username=' + (this.state.playername || genericPlayername) + '&laddername=' + (this.props.laddername || genericLaddername), getData)
            .then(res => res.json())
            .then(function (json) {
                this.dispatch({ type: "SUCCESS_CONTENT", content: json });
            }.bind(this));
    } else {
        postData.body = JSON.stringify({ "token": this.props.token, "username": (this.state.playername || genericPlayername), "laddername": (this.props.laddername || genericLaddername) });
        fetch(server + "/user/nextmatches", postData)
            .then(res => res.json())
            .then(function (json) {
                this.dispatch({ type: "SUCCESS_CONTENT", content: json });
            }.bind(this));
    }
}



export default GlobalAppActions;