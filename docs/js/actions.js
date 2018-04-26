const GlobalAppActions = (state, action) => {

    switch (action.type) {
        case "LOADING_TEAMS":
            return { currentState: "loading", showLoading: true, showWelcome: true }
            break;
        case "SUCCESS_TEAMS":
            return { currentState: "loading", showLoading: false, showWelcome: false }
            break;
    }

}

var fetchMatches = function () {
    this.dispatch({ type: "LOADING_TEAMS" });
    fetch("http://localhost:9000/team/all")
        .then(res => res.json())
        .then(function (json) {
            this.dispatch({ type: "SUCCESS_TEAMS", teams: json });
        }.bind(this))
        .catch(error => dispatch(fetchMatchesFailure(error)));
}
