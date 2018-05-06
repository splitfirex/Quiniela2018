import React from 'react';
import { GlobalAppActions, fetchNextMatches, fetchPlayers, fetchPlayerStatus, fetchUpdateColor } from '../lib/actions.js'
import Loading from './ContentUtils.jsx';

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

                if (currentValue.username === this.props.username) {
                    return <Player
                        currentValue={currentValue}
                        dispatch={(action) => this.props.dispatch(action)}
                        {...this.props} />
                }

                return <PlayerAdmin
                    currentValue={currentValue}
                    dispatch={(action) => this.props.dispatch(action)}
                    changeStatus={(laddername, playername, admin, active) =>
                        this.changeStatus(laddername, playername, admin, active)
                    }
                    {...this.props} />

            } else {
                if (currentValue.active || currentValue.admin) {
                    return <Player
                        currentValue={currentValue}
                        dispatch={(action) => this.props.dispatch(action)}
                        {...this.props} />
                }
                return undefined;
            }
        }.bind(this));
    }

    render() {
        if (!this.state.showLoading) {
            this.isAdmin = this.state.content.listPlayers.filter(function (user) {
                return user.username === this.props.username && user.admin
            }.bind(this)).length !== 0
            this.isActive = this.state.content.listPlayers.filter(function (user) {
                return user.username === this.props.username && user.active
            }.bind(this)).length !== 0
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
            <div className={"playerShow f" + (props.currentValue.winnerTeam && props.teams[props.currentValue.winnerTeam].shortName)}>
                <div> {props.currentValue.username} | {props.currentValue.points} </div>
            </div>
            <div id={"id" + props.currentValue.username} className="playerContent">
                <PlayerShortMatches key={"id" + props.currentValue.username} {...props} />
            </div>
            <div className="playerMenu">
                <div onClick={() => props.dispatch({ type: "GO_TO", dest: "SHOW_PLAYER_MATCHES", laddername: props.laddername, playername: props.currentValue.username })} ><div className="iconCenter"><i className="fas fa-list-ol"></i> Partidos</div></div>
                <div onClick={() => props.dispatch({ type: "GO_TO", dest: "SHOW_PLAYER_GROUPS", laddername: props.laddername, playername: props.currentValue.username })} ><div className="iconCenter"><i className="fas fa-list-alt"></i> Grupos</div></div>
            </div>
        </div>
    )
}

function PlayerAdmin(props) {
    return (
        <div className="player">
            <div className={"playerShow f" + (props.currentValue.winnerTeam && props.teams[props.currentValue.winnerTeam - 1].shortName)}>
                <div> {props.currentValue.username} | {props.points} </div>
            </div>
            <div id={"id" + props.currentValue.username} className="playerContent">
                <PlayerShortMatches  key={"id" + props.currentValue.username} {...props} />
            </div>
            <div key={props.currentValue.username + props.currentValue.admin + props.currentValue.active} className="playerMenu admin">
                <div onClick={() => props.dispatch({ type: "GO_TO", dest: "SHOW_PLAYER_MATCHES", laddername: props.laddername, playername: props.currentValue.username })} ><div className="iconCenter"><i className="fas fa-list-ol"></i> Partidos</div></div>
                <div onClick={() => props.dispatch({ type: "GO_TO", dest: "SHOW_PLAYER_GROUPS", laddername: props.laddername, playername: props.currentValue.username })} ><div className="iconCenter"><i className="fas fa-list-alt"></i> Grupos</div></div>
                <div onClick={() => props.changeStatus(props.laddername, props.currentValue.username, !props.currentValue.admin, props.currentValue.active)} ><div className="iconCenter"><i style={{ "color": props.currentValue.admin ? "blue" : "black" }} className="fas fa-at"></i></div></div>
                <div onClick={() => props.changeStatus(props.laddername, props.currentValue.username, props.currentValue.admin, !props.currentValue.active)} ><div className="iconCenter"><i style={{ "color": props.currentValue.active ? "green" : "tomato" }} className="fas fa-user"></i></div></div>
                <div onClick={() => props.dispatch({ type: "GO_TO", dest: "BAN_PLAYER", laddername: props.laddername, playername: props.currentValue.username })}><div className="iconCenter"><i className="fas fa-ban"></i></div></div>
            </div>
        </div>
    )
}

class PlayerShortMatches extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showLoading: false,
            playername: props.currentValue.username,
            content: {
                prevMatches: [],
                nextMatches: []
            }
        }
    }

    componentDidMount() {
        fetchNextMatches.bind(this)();
    }

    dispatch(action) {
        this.setState(preState => GlobalAppActions(preState, action));
    }


    renderNextMatches() {
        return this.state.content.nextMatches.map(function (currentValue, index, array) {
            return <AuxMatch  key={this.props.username + " "+ index}
                currentMatch={currentValue}
                index={index} round={currentValue.idMatch + 1}
                oddLast={(index + 1) % 2 !== 0 && (this.state.content.nextMatches.length - 1 === index)}
                matchStatus={currentValue.status}  {...this.props} />
        }.bind(this));
    }

    renderPrevMatches() {
        return this.state.content.prevMatches.map(function (currentValue, index, array) {
            return <AuxMatch key={this.props.username + " "+ index} currentMatch={currentValue}
                index={index} round={currentValue.idMatch + 1}
                oddLast={(index + 1) % 2 !== 0 && (this.state.content.prevMatches.length - 1 === index)}
                matchStatus={currentValue.status} {...this.props} />
        }.bind(this));
    }

    render() {
        return [
            this.state.content.prevMatches.length !== 0 && <div style={{ gridColumn: "1 / span 2" }}>Partidos previos</div>,
            this.renderPrevMatches(),
            this.state.content.nextMatches.length !== 0 && <div style={{ gridColumn: "1 / span 2" }}>Partidos siguientes </div>,
            this.renderNextMatches()]
    }

}

function AuxMatch(props) {

    var hTeam = props.teams[props.currentMatch.hT] || {};
    var vTeam = props.teams[props.currentMatch.vT] || {};

    return (
        <div style={{ gridColumn: props.oddLast && "1 / span 2" }}>
            <div className={"teamMatch " + (props.matchStatus === 0 || props.matchStatus === null ? "whiteBG" : (props.matchStatus === 1 ? "greenBG" : "tomatoBG"))} style={{ "borderBottom": "grey 1px solid" }}>
                <div className="teambox">
                    <div>{hTeam === undefined ?
                        props.matches[props.index].homeTeam :
                        hTeam.shortName}</div>
                    <div><div className={"flag flag-" + hTeam.flagUrl}></div></div>
                    <div>{props.currentMatch.hS === null ? "*" : props.currentMatch.hS}</div>
                </div>
                <div className="teamboxSeparator">-</div>
                <div className="teambox left">
                    <div>{props.currentMatch.vS === null ? "*" : props.currentMatch.vS}</div>
                    <div><div className={"flag flag-" + vTeam.flagUrl}></div></div>
                    <div>{vTeam === undefined ?
                        props.matches[props.index].visitorTeam :
                        vTeam.shortName}</div>
                </div>
            </div>
        </div>
    )
}

export default ContentPlayer;