import React from 'react';
import { GlobalAppActions, fetchLadders, fetchUpdateScore, fetchMatches  } from '../lib/actions.js'
import Loading from './ContentUtils.jsx';
import zeroPad from '../lib/utils.js';

export class ContentMatch extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showLoading: false,
            content: [],
            editables: []
        }
        this.currentSubtitle = "";
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.forceReload) {
            this.props.dispatch({ type: "UNFORCE" });
            fetchLadders.bind(this)();
        }
    }

    toggleEdit(index) {
        if (this.state.editables.indexOf(index) === -1) {
            this.state.editables.push(index);
        } else {
            this.state.editables.splice(this.state.editables.indexOf(index), 1);
            fetchUpdateScore.bind(this)(index, this.state.content[index].hS, this.state.content[index].vS);
        }
        this.setState({
            editables: this.state.editables
        });

    }

    incrementScore(index, isHome) {
        var content = this.state.content;
        if (isHome) {
            content[index].hS = this.state.content[index].hS === undefined ? 0 : this.state.content[index].hS + 1;
        } else {
            content[index].vS = this.state.content[index].vS === undefined ? 0 : this.state.content[index].vS + 1;
        }
        this.setState({
            content: content
        });
    }

    decrementScore(index, isHome) {
        var content = this.state.content;
        if (isHome) {
            content[index].hS = this.state.content[index].hS === undefined ? undefined : this.state.content[index].hS - 1;
            if (this.state.content[index].hS === -1) content[index].hS = undefined;
        } else {
            content[index].vS = this.state.content[index].vS === undefined ? undefined : this.state.content[index].vS - 1;
            if (this.state.content[index].vS === -1) content[index].vS = undefined
        }
        this.setState({
            content: content
        });
    }

    componentDidMount() {
        fetchMatches.bind(this)();
    }

    dispatch(action) {
        this.setState(preState => GlobalAppActions(preState, action));
    }

    renderSubtitle(newSubtitle) {
        if (this.currentSubtitle === newSubtitle) {
            return undefined;
        }
        this.currentSubtitle = newSubtitle;
        return <div>{this._translate(newSubtitle)}</div>
    }

    _translate(value) {
        switch (value) {
            case "GROUP_PHASE": return "Fase de grupos";
            case "ROUND_OF_16": return "Octavos de final";
            case "QUARTER_FINALS": return "Cuartos de final";
            case "SEMI_FINALS": return "Semi Finales";
            case "FINALS": return "Finales";
            default : return "";
        }
    }

    renderMatches() {
        return this.state.content.map(function (currentValue, index, array) {
            var d = new Date(this.props.matches[index].date);
            return this.props.playername !== undefined && 
            this.props.username === this.props.playername && 
            this.props.matches[index].editable && !this.props.matches[index].finish  ?
                this.state.editables.indexOf(index) !== -1 ?
                    [this.renderSubtitle(this.props.matches[index].typeMatch), <MatchEdit round={index + 1} key={"match" + index}
                        index={index}
                        date={zeroPad(d.getDate(), 2) + "/" + zeroPad(d.getMonth(), 2) + " " + zeroPad(d.getHours(), 2) + ":" + zeroPad(d.getMinutes(), 2)}
                        homeTeamShort={this.props.teams[currentValue.hT] === undefined ?
                            this.props.matches[index].homeTeam : this.props.teams[currentValue.hT].shortName}
                        visitorTeamShort={this.props.teams[currentValue.vT] === undefined ?
                            this.props.matches[index].visitorTeam : this.props.teams[currentValue.vT].shortName}
                        flagUrlHome={this.props.teams[currentValue.hT] === undefined ?
                            "none" : this.props.teams[currentValue.hT].flagUrl}
                        flagUrlVisitor={this.props.teams[currentValue.vT - 1] === undefined ?
                            "none" : this.props.teams[currentValue.vT].flagUrl}
                        homeTeamScore={currentValue.hS == undefined ? "*" : currentValue.hS}
                        visitorTeamScore={currentValue.vS == undefined ? "*" : currentValue.vS}
                        toggleEdit={(index) => this.toggleEdit(index)}
                        inc={(index, home) => this.incrementScore(index, home)}
                        dec={(index, home) => this.decrementScore(index, home)}
                        matchStatus={currentValue.status}  />]
                    :
                    [this.renderSubtitle(this.props.matches[index].typeMatch), <MatchUser round={index + 1} key={"match" + index}
                        index={index}
                        date={zeroPad(d.getDate(), 2) + "/" + zeroPad(d.getMonth(), 2) + " " + zeroPad(d.getHours(), 2) + ":" + zeroPad(d.getMinutes(), 2)}
                        homeTeamShort={this.props.teams[currentValue.hT ] === undefined ?
                            this.props.matches[index].homeTeam : this.props.teams[currentValue.hT].shortName}
                        visitorTeamShort={this.props.teams[currentValue.vT] === undefined ?
                            this.props.matches[index].visitorTeam : this.props.teams[currentValue.vT].shortName}
                        flagUrlHome={this.props.teams[currentValue.hT ] === undefined ?
                            "none" : this.props.teams[currentValue.hT ].flagUrl}
                        flagUrlVisitor={this.props.teams[currentValue.vT ] === undefined ?
                            "none" : this.props.teams[currentValue.vT ].flagUrl}
                        homeTeamScore={currentValue.hS == undefined ? "*" : currentValue.hS}
                        visitorTeamScore={currentValue.vS == undefined ? "*" : currentValue.vS}
                        toggleEdit={(index) => this.toggleEdit(index)}
                        matchStatus={currentValue.status}  />]
                :
                [this.renderSubtitle(this.props.matches[index].typeMatch), <Match round={index + 1} key={"match" + index}
                    date={zeroPad(d.getDate(), 2) + "/" + zeroPad(d.getMonth(), 2) + " " + zeroPad(d.getHours(), 2) + ":" + zeroPad(d.getMinutes(), 2)}
                    homeTeamShort={this.props.teams[currentValue.hT] === undefined ?
                        this.props.matches[index].homeTeam : this.props.teams[currentValue.hT ].shortName}
                    visitorTeamShort={this.props.teams[currentValue.vT] === undefined ?
                        this.props.matches[index].visitorTeam : this.props.teams[currentValue.vT ].shortName}
                    flagUrlHome={this.props.teams[currentValue.hT] === undefined ?
                        "none" : this.props.teams[currentValue.hT].flagUrl}
                    flagUrlVisitor={this.props.teams[currentValue.vT] === undefined ?
                        "none" : this.props.teams[currentValue.vT].flagUrl}
                    homeTeamScore={currentValue.hS == undefined ? "*" : currentValue.hS}
                    visitorTeamScore={currentValue.vS == undefined ? "*" : currentValue.vS}
                    matchStatus={currentValue.status} />]
        }.bind(this))
    }


    render() {
        return this.state.showLoading ? <Loading /> : this.renderMatches();
    }
}


function Match(props) {
    return (
        <div className="match">
            <div>{props.round}</div>
            <div className="matchInfo">
                <div><div>{props.date}</div></div>
                <div className={"teamMatch " + (props.matchStatus === 0 || props.matchStatus == undefined ? "whiteBG" : (props.matchStatus === 1  ? "greenBG" : "tomatoBG" ))}>
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
        </div>
    );
}

function MatchUser(props) {
    return (
        <div className="match user">
            <div>{props.round}</div>
            <div className="matchInfo">
                <div><div>{props.date}</div></div>
                <div className={"teamMatch " + (props.matchStatus === 0 || props.matchStatus == undefined ? "whiteBG" : (props.matchStatus === 1  ? "greenBG" : "tomatoBG" ))}>
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
            <div onClick={() => props.toggleEdit(props.index)}><div className="iconCenter"><i className="fas fa-edit"></i></div></div>
        </div>
    )
}

function MatchEdit(props) {
    return (<div className="match user edit">
        <div>{props.round}</div>
        <div className="matchInfo">
            <div><div>{props.date}</div></div>
            <div className={"teamMatch " + (props.matchStatus === 0 || props.matchStatus == undefined ? "whiteBG" : (props.matchStatus === 1  ? "greenBG" : "tomatoBG" ))}>
                <div className="teambox">
                    <div>{props.homeTeamShort}</div>
                    <div><div className={"flag flag-" + props.flagUrlHome}></div></div>
                </div>
                <div className="teamboxSeparator">-</div>
                <div className="teambox">
                    <div></div>
                    <div><div className={"flag flag-" + props.flagUrlVisitor}></div></div>
                    <div>{props.visitorTeamShort}</div>
                </div>
            </div>
            <div className="editBox">
                <div onClick={() => props.dec(props.index, true)} > <i className="fas fa-chevron-circle-left"></i> </div>
                <div>{props.homeTeamScore}</div>
                <div onClick={() => props.inc(props.index, true)}> <i className="fas fa-chevron-circle-right"></i> </div>
                <div></div>
                <div onClick={() => props.dec(props.index, false)}> <i className="fas fa-chevron-circle-left"></i> </div>
                <div> {props.visitorTeamScore}</div>
                <div onClick={() => props.inc(props.index, false)}> <i className="fas fa-chevron-circle-right"></i> </div>
            </div>
        </div>
        <div onClick={() => props.toggleEdit(props.index)} ><div className="iconCenter"><i className="fas fa-save"></i></div></div>
    </div>)
}

export default ContentMatch;