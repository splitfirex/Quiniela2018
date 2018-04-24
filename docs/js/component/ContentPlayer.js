class ContentPlayer extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            players: [],
            isAdmin: false
        }
    }

    processPlayers(responseLadders) {
        if (responseLadders.listPlayers == null) {
            this.setState({
                players: []
            });
        } else {
            var isAdmin = false;
            if (this.props.username != null) {
                responseLadders.listPlayers.forEach(function callback(currentValue, index, array) {
                    if (this.props.username == currentValue.username && currentValue.admin) {
                        isAdmin = true;
                    }
                }.bind(this));
            }
            this.setState({
                players: responseLadders.listPlayers,
                isAdmin: isAdmin
            });
        }
    }

    toggleAdmin(username) {
        var player = null;
        this.state.players.forEach(function callback(currentValue, index, array) {
            if (username == currentValue.username) {
                player = currentValue;
            }
        }.bind(this));

        postUpdatePlayerStatus(player.username, this.props.laddername, !player.admin, player.active, this.processPlayers.bind(this));
    }

    toggleActivate(username) {
        var player = null;
        this.state.players.forEach(function callback(currentValue, index, array) {
            if (username == currentValue.username) {
                player = currentValue;
            }
        }.bind(this));

        postUpdatePlayerStatus(player.username, this.props.laddername, player.admin, !player.active, this.processPlayers.bind(this));
    }

    componentDidMount() {
        postPlayerLaddersDetail(this.props.laddername, this.processPlayers.bind(this));
    }

    componentWillReceiveProps(nextProps) {
        postPlayerLaddersDetail(this.props.laddername, this.processPlayers.bind(this));
    }

    renderPayers() {
        var that = this;
        return this.state.players.map(function (currentValue, index, array) {
            if (that.state.isAdmin) {

                if (currentValue.username == that.props.username) {
                    return <Player
                        username={currentValue.username}
                        points={currentValue.points}
                        fnOnMatchClick={that.props.fnOnMatchClick}
                        fnOnGroupClick={that.props.fnOnGroupClick} />
                }

                return <PlayerAdmin
                    username={currentValue.username}
                    points={currentValue.points}
                    fnOnMatchClick={that.props.fnOnMatchClick}
                    fnOnGroupClick={that.props.fnOnGroupClick}
                    isAdmin={currentValue.admin}
                    isActive={currentValue.active}
                    toggleAdmin={that.toggleAdmin.bind(that, currentValue.username)}
                    toggleActivate={that.toggleActivate.bind(that, currentValue.username)} />

            } else {
                if (currentValue.active) {
                    return <Player
                        username={currentValue.username}
                        points={currentValue.points}
                        fnOnMatchClick={that.props.fnOnMatchClick}
                        fnOnGroupClick={that.props.fnOnGroupClick} />
                }
                return null;
            }
        });
    }

    render() {

        if (this.state.players.length == 0) {
            return <Loading />
        }

        return this.renderPayers();
    }

}

ContentPlayer.defaultProps = {
    username: null,
    laddername: null
}


function Player(props) {
    return (
        <div className="player">
            <div className="playerShow">
                <div> {props.username} | {props.points} </div>
            </div>
            <div className="playerContent">

            </div>
            <div className="playerMenu">
                <div onClick={props.fnOnMatchClick.bind(this, props.username)} ><div className="iconCenter"><i className="fas fa-list-ol"></i> Partidos</div></div>
                <div onClick={props.fnOnGroupClick.bind(this, props.username)} ><div className="iconCenter"><i className="fas fa-list-alt"></i> Grupos</div></div>
            </div>
        </div>
    )
}

function PlayerAdmin(props) {
    return (
        <div className="player">
            <div className="playerShow">
                <div> {props.username} | {props.points} </div>
            </div>
            <div className="playerContent">

            </div>
            <div key={props.username + props.isAdmin + props.isActive} className="playerMenu admin">
                <div onClick={props.fnOnMatchClick.bind(this, props.username)} ><div className="iconCenter"><i className="fas fa-list-ol"></i> Partidos</div></div>
                <div onClick={props.fnOnGroupClick.bind(this, props.username)} ><div className="iconCenter"><i className="fas fa-list-alt"></i> Grupos</div></div>
                <div onClick={props.toggleAdmin} ><div className="iconCenter"><i style={{ "color": props.isAdmin ? "blue" : "black" }} className="fas fa-at"></i></div></div>
                <div onClick={props.toggleActivate} ><div className="iconCenter"><i style={{ "color": props.isActive ? "green" : "tomato" }} className="fas fa-user"></i></div></div>
                <div><div className="iconCenter"><i className="fas fa-ban"></i></div></div>
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