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

const GlobalAppActions = (state, action) => {

    switch (action.type) {
        case "CLOSE_MODAL":
            return { showModal: false }
        case "GO_BACK":
            if (state.breadcrumbs.length == 3) {
                state.breadcrumbs.pop();
                return { subTitle: state.breadcrumbs[1], showMenu: false, contentWindow: "PLAYERS", breadcrumbs: state.breadcrumbs }
            }
            return { subTitle: "Quinielas", showMenu: false, laddername: undefined, contentWindow: "LADDERS", showBreadcrumbs: false, breadcrumbs: [] }
        case "TOGGLE_MENU":
            return { showMenu: !state.showMenu }
        case "LOADING_PREFETCHS":
            return { showWelcome: true }
        case "SUCCESS_PREFETCHS":
            state[action.prefetch] = action.content;
            if (state.matches.length == 0 || state.groups.length == 0 || state.teams.length == 0) {
                return { ...state }
            }
            return { showWelcome: false }
        case "GO_TO":
            window.scrollTo(0, 0);
            switch (action.dest) {
                case "SHOW_HOME":
                    return { subTitle: "Quinielas", playername: undefined, laddername: undefined, showMenu: false, contentWindow: "LADDERS", showBreadcrumbs: false, breadcrumbs: [] }
                case "SHOW_GROUPS":
                    return { subTitle: "Grupos", playername: undefined, laddername: undefined, showMenu: false, contentWindow: "GROUPS", showBreadcrumbs: true, breadcrumbs: ["Inicio", "Grupos"] }
                case "SHOW_MATCHES":
                    return { subTitle: "Partidos", playername: undefined, laddername: undefined, showMenu: false, contentWindow: "MATCHES", showBreadcrumbs: true, breadcrumbs: ["Inicio", "Partidos"] }
                case "SHOW_PLAYERS":
                    return { subTitle: action.laddername, laddername: action.laddername, showMenu: false, contentWindow: "PLAYERS", showBreadcrumbs: true, breadcrumbs: ["Inicio", action.laddername] }
                case "SHOW_PLAYER_MATCHES":
                    return { subTitle: action.playername, playername: action.playername, laddername: action.laddername, showMenu: false, contentWindow: "MATCHES", showBreadcrumbs: true, breadcrumbs: ["Inicio", action.laddername, action.playername] }
                case "SHOW_PLAYER_GROUPS":
                    return { subTitle: action.playername, playername: action.playername, laddername: action.laddername, showMenu: false, contentWindow: "GROUPS", showBreadcrumbs: true, breadcrumbs: ["Inicio", action.laddername, action.playername] }
                case "SIGN_IN":
                    return { showModal: true, showMenu: false, contentModalWindow: "SIGNIN" }
                case "SIGN_UP":
                    return { showModal: true, showMenu: false, contentModalWindow: "SIGNUP" }
                case "NEW_LADDER":
                    return { showModal: true, showMenu: false, contentModalWindow: "NEWLADDER" }
                case "JOIN_LADDER":
                    return { showModal: true, showMenu: false, contentModalWindow: "JOINLADDER", laddername: action.laddername, ladderProtected: action.ladderProtected }
                case "BAN_PLAYER":
                    return { showModal: true, showMenu: false, contentModalWindow: "BANPLAYER",  laddername: action.laddername, playername: action.playername }
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
            return { contentModalWindow: "", showBreadcrumbs: false, breadcrumbs: [], subTitle: "Quinielas", contentWindow: "LADDERS", showModal: false, showLoading: false, username: action.username, token: action.token }
        case "SUCCESS_LOGOUT":
            window.scrollTo(0, 0);
            return { showMenu: !state.showMenu, showBreadcrumbs: false, breadcrumbs: [], subTitle: "Quinielas", contentWindow: "LADDERS", showModal: false, showLoading: false, username: undefined, token: undefined }
        case "SUCCESS_CREATE":
            return { contentModalWindow: "", showBreadcrumbs: true, breadcrumbs: ["Inicio", action.laddername], subTitle: action.laddername, contentWindow: "PLAYERS", showModal: false, laddername: action.laddername }
        case "SUCCESS_JOIN":
            return { forceReload: true, showModal: false, subTitle: "Quinielas", playername: undefined, laddername: undefined, showMenu: false, contentWindow: "LADDERS", showBreadcrumbs: false, breadcrumbs: [] }
        case "UNFORCE":
            return { forceReload: false }
        case "CLOSE_AND_RELOAD":
            return { forceReload: true, showModal: false }
    }

}

const LoginAppActions = (state, action) => {
    switch (action.type) {
        case "LOADING_CONTENT":
            return { showLoading: true }
        case "FAIL_PROCESS":
            return { showLoading: false, error: true }
        default:
            return state
    }
}

var prefetchMatches = function () {
    this.dispatch({ type: "LOADING_PREFETCHS" });
    fetch(server + "/match/all")
        .then(res => res.json())
        .then(function (json) {
            this.dispatch({ type: "SUCCESS_PREFETCHS", prefetch: "matches", content: json });
        }.bind(this));
}

var prefetchGroups = function () {
    this.dispatch({ type: "LOADING_PREFETCHS" });
    fetch(server + "/group/all")
        .then(res => res.json())
        .then(function (json) {
            this.dispatch({ type: "SUCCESS_PREFETCHS", prefetch: "groups", content: json });
        }.bind(this));
}

var prefetchTeams = function () {
    this.dispatch({ type: "LOADING_PREFETCHS" });
    fetch(server + "/team/all")
        .then(res => res.json())
        .then(function (json) {
            this.dispatch({ type: "SUCCESS_PREFETCHS", prefetch: "teams", content: json });
        }.bind(this));
}

var fetchLadders = function () {
    this.dispatch({ type: "LOADING_CONTENT" });
    //if (this.props.token == null) {
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

var fetchMatches = function () {
    this.dispatch({ type: "LOADING_CONTENT" });
    if (this.props.token == null) {
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

var fetchGroups = function () {
    this.dispatch({ type: "LOADING_CONTENT" });
    if (this.props.token == null) {
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

var fetchPlayers = function () {
    this.dispatch({ type: "LOADING_CONTENT" });
    if (this.props.token == null) {
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

var fetchLogin = function () {
    this.dispatch({ type: "LOADING_CONTENT" });
    postData.body = JSON.stringify({ "username": this.state.username, "password": this.state.password });
    fetch(server + '/login/signin', postData)
        .then(function (response) {
            return response.json();
        }).then(function (json) {
            if (json.token != null) {
                this.props.dispatch({ type: "SUCCESS_LOGIN", username: this.state.username, token: json.token });
            } else {
                this.dispatch({ type: "FAIL_PROCESS" });
            }
        }.bind(this));
}

var fetchRegister = function () {
    if (this.state.password.length == 0) {
        this.dispatch({ type: "FAIL_PROCESS" });
        return;
    }
    this.dispatch({ type: "LOADING_CONTENT" });
    postData.body = JSON.stringify({ "username": this.state.username, "password": this.state.password });
    fetch(server + '/login/signup', postData)
        .then(function (response) {
            return response.json();
        }).then(function (json) {
            if (json.token != null) {
                this.props.dispatch({ type: "SUCCESS_LOGIN", username: this.state.username, token: json.token });
            } else {
                this.dispatch({ type: "FAIL_PROCESS" });
            }
        }.bind(this));
}

var fetchNewLadder = function () {
    if (!this.state.public && this.state.password.length == 0) {
        this.dispatch({ type: "FAIL_PROCESS" });
        return;
    }
    this.dispatch({ type: "LOADING_CONTENT" });
    postData.body = JSON.stringify({ laddername: this.state.laddername, token: this.props.token, "password": this.state.password });
    fetch(server + '/user/createladder', postData)
        .then(function (response) {
            return response.json();
        }).then(function (json) {
            if (json.name != null) {
                this.props.dispatch({ type: "SUCCESS_CREATE", laddername: json.name });
            } else {
                this.dispatch({ type: "FAIL_PROCESS" });
            }
        }.bind(this));
}

var fetchJoinLadder = function () {
    if (this.props.ladderProtected && this.state.password.length == 0) {
        this.dispatch({ type: "FAIL_PROCESS" });
        return;
    }
    this.dispatch({ type: "LOADING_CONTENT" });
    postData.body = JSON.stringify({ "token": this.props.token, "laddername": this.props.laddername, "password": this.state.password });
    fetch(server + '/user/joinladder', postData)
        .then(function (response) {
            return response.json();
        }).then(function (json) {
            if (json.name != null) {
                this.props.dispatch({ type: "SUCCESS_JOIN" });
            } else {
                this.dispatch({ type: "FAIL_PROCESS" });
            }
        }.bind(this));
}

var fetchPlayerStatus = function (laddername, playername, admin, active) {
    postData.body = JSON.stringify({ "token": this.props.token, "username": playername, "activate": active, "admin": admin, "laddername": laddername });
    fetch(server + '/user/updateplayerstatus', postData)
        .then(function (response) {
            return response.json();
        }).then(function (res) {
            this.props.dispatch({ type: "CLOSE_AND_RELOAD" });
        }.bind(this));
}

var fetchBanPlayer = function () {
    postData.body = JSON.stringify({ "token": this.props.token, "username": this.props.playername, "laddername": this.props.laddername });
    fetch(server + '/user/banplayer', postData)
        .then(function (response) {
            return response.json();
        }).then(function (res) {
            this.props.dispatch({ type: "CLOSE_AND_RELOAD" });
        }.bind(this));
}

var fetchUpdateScore = function(index,homeScore,visitScore){
  postData.body = JSON.stringify({ "token": this.props.token, laddername: this.props.laddername, "idMatch": index, "homeScore": homeScore, "visitScore": visitScore });
    fetch(server + '/user/updatematch', postData)
        .then(function (response) {
            return response.json();
        }).then(function (res) {
            this.dispatch({ type: "SUCCESS_CONTENT" , content: res  });
        }.bind(this));
}