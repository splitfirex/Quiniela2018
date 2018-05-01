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
                        dispatch={(action) => this.props.dispatch(action)}
                        {...this.props} />
                }

                return <PlayerAdmin
                    ppname={currentValue.username}
                    points={currentValue.points}
                    isAdmin={currentValue.admin}
                    isActive={currentValue.active}
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
        }
        return this.state.showLoading ? <Loading /> : [
            this.isAdmin && <div className="adminLadder">
                <div> <i className="fas fa-external-link-square-alt" ></i> Abandonar</div>
                <div> <i className="fas fa-adjust" ></i> Color</div>
                <div> <i className="fas fa-times-circle" ></i> Expulsados</div>
            </div>, this.renderPayers()]
    }

}

function Player(props) {
    return (
        <div className="player">
            <div className="playerShow">
                <div> {props.ppname} | {props.points} </div>
            </div>
            <div className="playerContent">

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
            <div className="playerShow">
                <div> {props.ppname} | {props.points} </div>
            </div>
            <div className="playerContent">

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

function auxMatch() {
    return (
        <div>
            <div>63</div>
            <div className="teamMatch" style={{ "borderBottom": "grey 1px solid" }}>
                <div className="teambox">
                    <div>FRA</div>
                    <div><div className="flag RUS"></div></div>
                    <div>3</div>
                </div>
                <div className="teamboxSeparator">-</div>
                <div className="teambox left">
                    <div>6</div>
                    <div><div className="flag BRA"></div></div>
                    <div>BRA</div>
                </div>
            </div>
        </div>
    )
}