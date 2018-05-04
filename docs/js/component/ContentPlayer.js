class ContentPlayer extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showLoading: false,
            content: {
                listPlayers: []
            }
        }
    }

    componentDidMount() {
        fetchPlayers.bind(this)();
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.forceReload) {
            this.props.dispatch({ type: "UNFORCE" });
            fetchPlayers.bind(this)();
        }
    }

    dispatch(action) {
        this.setState(preState => GlobalAppActions(preState, action));
    }

    changeStatus(laddername, playername, admin, active) {
        fetchPlayerStatus.bind(this)(laddername, playername, admin, active);
    }

    renderPayers() {

        return this.state.content.listPlayers.map(function (currentValue, index, array) {
            if (this.isAdmin) {

                if (currentValue.username == this.props.username) {
                    return <Player
                        ppname={currentValue.username}
                        points={currentValue.points}
                        winner={currentValue.winnerTeam}
                        dispatch={(action) => this.props.dispatch(action)}
                        {...this.props} />
                }

                return <PlayerAdmin
                    ppname={currentValue.username}
                    points={currentValue.points}
                    isAdmin={currentValue.admin}
                    isActive={currentValue.active}
                    winner={currentValue.winnerTeam}
                    dispatch={(action) => this.props.dispatch(action)}
                    changeStatus={(laddername, playername, admin, active) =>
                        this.changeStatus(laddername, playername, admin, active)
                    }
                    {...this.props} />

            } else {
                if (currentValue.active) {
                    return <Player
                        ppname={currentValue.username}
                        points={currentValue.points}
                        winner={currentValue.winnerTeam}
                        dispatch={(action) => this.props.dispatch(action)}
                        {...this.props} />
                }
                return null;
            }
        }.bind(this));
    }

    render() {
        if (!this.state.showLoading) {
            this.isAdmin = this.state.content.listPlayers.filter(user => (user.username === this.props.username && user.active && user.admin)).length != 0
            this.isActive = this.state.content.listPlayers.filter(user => (user.username === this.props.username && user.active)).length != 0
        }
        return this.state.showLoading ? <Loading /> : [
            this.isAdmin &&
            <div className="adminLadder">
                <div onClick={() => this.props.dispatch({ type: "GO_TO", dest: "LEAVE_LADDER", laddername: this.props.laddername })} > <i className="fas fa-external-link-square-alt" ></i> Abandonar</div>
                <div className="ladderColor" onClick={() => fetchUpdateColor.bind(this)()} style={{ backgroundColor: this.state.content.bgColor, color: "white" }}> <i className="fas fa-adjust" ></i> Color</div>
            </div>,
            (!this.isAdmin && this.isActive) &&
            <div className="userLadder">
                <div onClick={() => this.props.dispatch({ type: "GO_TO", dest: "LEAVE_LADDER", laddername: this.props.laddername })} > <i className="fas fa-external-link-square-alt" ></i> Abandonar la quiniela</div>
            </div>, this.renderPayers()]
    }

}

function Player(props) {
    return (
        <div className="player">
            <div className={"playerShow f" + (props.winner && props.teams[props.winner - 1].shortName)}>
                <div> {props.ppname} | {props.points} </div>
            </div>
            <div id={"id" + props.ppname} className="playerContent">
                <PlayerShortMatches {...props} />
            </div>
            <div className="playerMenu">
                <div onClick={() => props.dispatch({ type: "GO_TO", dest: "SHOW_PLAYER_MATCHES", laddername: props.laddername, playername: props.ppname })} ><div className="iconCenter"><i className="fas fa-list-ol"></i> Partidos</div></div>
                <div onClick={() => props.dispatch({ type: "GO_TO", dest: "SHOW_PLAYER_GROUPS", laddername: props.laddername, playername: props.ppname })} ><div className="iconCenter"><i className="fas fa-list-alt"></i> Grupos</div></div>
            </div>
        </div>
    )
}

function PlayerAdmin(props) {
    return (
        <div className="player">
            <div className={"playerShow f" + (props.winner && props.teams[props.winner - 1].shortName)}>
                <div> {props.ppname} | {props.points} </div>
            </div>
            <div id={"id" + props.ppname} className="playerContent">
                <PlayerShortMatches {...props} />
            </div>
            <div key={props.username + props.isAdmin + props.isActive} className="playerMenu admin">
                <div onClick={() => props.dispatch({ type: "GO_TO", dest: "SHOW_PLAYER_MATCHES", laddername: props.laddername, playername: props.ppname })} ><div className="iconCenter"><i className="fas fa-list-ol"></i> Partidos</div></div>
                <div onClick={() => props.dispatch({ type: "GO_TO", dest: "SHOW_PLAYER_GROUPS", laddername: props.laddername, playername: props.ppname })} ><div className="iconCenter"><i className="fas fa-list-alt"></i> Grupos</div></div>
                <div onClick={() => props.changeStatus(props.laddername, props.ppname, !props.isAdmin, props.isActive)} ><div className="iconCenter"><i style={{ "color": props.isAdmin ? "blue" : "black" }} className="fas fa-at"></i></div></div>
                <div onClick={() => props.changeStatus(props.laddername, props.ppname, props.isAdmin, !props.isActive)} ><div className="iconCenter"><i style={{ "color": props.isActive ? "green" : "tomato" }} className="fas fa-user"></i></div></div>
                <div onClick={() => props.dispatch({ type: "GO_TO", dest: "BAN_PLAYER", laddername: props.laddername, playername: props.ppname })}><div className="iconCenter"><i className="fas fa-ban"></i></div></div>
            </div>
        </div>
    )
}

class PlayerShortMatches extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showLoading: false,
            content: {
                prevMatches: [],
                nextMatches: []
            }
        }
    }

    componentDidMount() {
        fetchNextMatches.bind(this)();
    }

    componentWillReceiveProps(nextProps) {
      /*  if (nextProps.forceReload) {
            this.props.dispatch({ type: "UNFORCE" });
            fetchPlayers.bind(this)();
        }*/
    }

    dispatch(action) {
       this.setState(preState => GlobalAppActions(preState, action));
    }


    renderMatches() {

        return this.state.content.nextMatches.map(function (currentValue, index, array) {
            var d = new Date(this.props.matches[index].date);
            return <AuxMatch round={index + 1} key={"match" + index}
            date={zeroPad(d.getDate(), 2) + "/" + zeroPad(d.getMonth(), 2) + " " + zeroPad(d.getHours(), 2) + ":" + zeroPad(d.getMinutes(), 2)}
            homeTeamShort={this.props.teams[currentValue.hT - 1] == undefined ?
                this.props.matches[index].homeTeam : this.props.teams[currentValue.hT - 1].shortName}
            visitorTeamShort={this.props.teams[currentValue.vT - 1] == undefined ?
                this.props.matches[index].visitorTeam : this.props.teams[currentValue.vT - 1].shortName}
            flagUrlHome={this.props.teams[currentValue.hT - 1] == undefined ?
                "none" : this.props.teams[currentValue.hT - 1].flagUrl}
            flagUrlVisitor={this.props.teams[currentValue.vT - 1] == undefined ?
                "none" : this.props.teams[currentValue.vT - 1].flagUrl}
            homeTeamScore={currentValue.hS == null ? "*" : currentValue.hS}
            visitorTeamScore={currentValue.vS == null ? "*" : currentValue.vS}
            matchStatus={currentValue.status} />
        }.bind(this));
    }

    render() {
        return this.renderMatches();
    }

}

function AuxMatch(props) {
    return (
        <div>
            <div>{props.round}</div>
            <div  className={"teamMatch " + (props.matchStatus == 0 || props.matchStatus == null ? "whiteBG" : (props.matchStatus == 1  ? "greenBG" : "tomatoBG" ))} style={{ "borderBottom": "grey 1px solid" }}>
            <div className="teambox">
                    <div>{props.homeTeamShort}</div>
                    <div><div className={"flag flag-" + props.flagUrlHome}></div></div>
                    <div>{props.homeTeamScore}</div>
                </div>
                <div className="teamboxSeparator">-</div>
                <div className="teambox left">
                    <div>{props.visitorTeamScore}</div>
                    <div><div className={"flag flag-" + props.flagUrlVisitor}></div></div>
                    <div>{props.visitorTeamShort}</div>
                </div>
            </div>
        </div>
    )
}