import React from 'react';
import { GlobalAppActions, fetchLadders, fetchUpdateScore, fetchMatches, fetchMatchesGroups } from '../lib/actions.js'
import Loading from './ContentUtils.jsx';
import { zeroPad, colorScore, calculateTeam } from '../lib/utils.js';

export class ContentMatch extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showLoading: false,
            content: [],
            groups: [],
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

    toggleEdit(id) {

        if (this.state.editables.indexOf(id) === -1) {
            this.state.editables.push(id);
        } else {

            this.state.content.map(function (currentValue, index, array) {
                var homeTeam = calculateTeam(currentValue, this.state.groups, array, this.props.teams, "home");
                var awayTeam = calculateTeam(currentValue, this.state.groups, array, this.props.teams, "away");
                if (currentValue.type != "group") {
                    currentValue.home_team = typeof homeTeam === 'object' ? homeTeam.id : null;
                    currentValue.away_team = typeof awayTeam === 'object' ? awayTeam.id : null;
                }
            }.bind(this));


            var index = this.state.content.map(function (e) { return e.id; }).indexOf(id);
            this.state.editables.splice(this.state.editables.indexOf(id), 1);
            this.updateReferencies(index);

        }
        this.setState({
            editables: this.state.editables
        });

    }

    updateReferencies(index) {

        fetchUpdateScore.bind(this)([this.state.content[index]]);

        if (this.state.content[index].references != null) {
            for (var i = 0; i < this.state.content[index].references.length; i++) {
                var id = this.state.content[index].references[i];
                var index = this.state.content.map(function (e) { return e.id; }).indexOf(id);
                this.updateReferencies(index);
            }
        }
    }

    incrementScore(id, score) {
        var content = this.state.content;
        var index = content.map(function (e) { return e.id; }).indexOf(id);

        content[index][score] = this.state.content[index][score] == undefined ? 0 : this.state.content[index][score] + 1;

        if ((score == "home_penalty" || score == "away_penalty") && content[index]["home_penalty"] === content[index]["away_penalty"]) {
            content[index][score] = this.state.content[index][score] == undefined ? 0 : this.state.content[index][score] + 1;
        }

        this.setState({
            content: content
        });
    }

    decrementScore(id, score) {
        var content = this.state.content;
        var index = content.map(function (e) { return e.id; }).indexOf(id);

        content[index][score] = this.state.content[index][score] == undefined ? undefined : this.state.content[index][score] - 1;
        if (this.state.content[index][score] === -1) content[index][score] = undefined;

        if ((score == "home_penalty" || score == "away_penalty") && content[index]["home_penalty"] != undefined && content[index]["home_penalty"] === content[index]["away_penalty"]) {
            content[index][score] = this.state.content[index][score] == undefined ? undefined : this.state.content[index][score] - 1;
            if (this.state.content[index][score] === -1) content[index][score] = undefined;
        }

        this.setState({
            content: content
        });
    }

    componentDidMount() {
        fetchMatchesGroups.bind(this)();
        fetchMatches.bind(this)();
    }

    dispatch(action) {
        this.setState(preState => GlobalAppActions(preState, action));
    }

    renderSubtitle(newSubtitle) {
        if (newSubtitle.indexOf("Group") != -1) newSubtitle = "Group";
        if (this.currentSubtitle === newSubtitle) {
            return undefined;
        }
        this.currentSubtitle = newSubtitle;
        return <div>{this._translate(this.currentSubtitle)}</div>
    }

    _translate(value) {
        if (value.indexOf("Group") != -1) return "Fase de grupos";
        if (value.indexOf("Round of 16") != -1) return "Octavos de final";
        if (value.indexOf("Quarter-finals") != -1) return "Cuartos de final";
        if (value.indexOf("Semi-finals") != -1) return "Semi Final";
        if (value.indexOf("Third place play-off") != -1) return "Tercer lugar";
        if (value.indexOf("Final") != -1) return "Final";
    }

    renderMatches() {
        if (this.state.groups.length == 0) return null;
        return this.state.content.map(function (currentValue, index, array) {
            var d = new Date(currentValue.date);
            var homeTeam = calculateTeam(currentValue, this.state.groups, array, this.props.teams, "home");
            var awayTeam = calculateTeam(currentValue, this.state.groups, array, this.props.teams, "away");
            if (currentValue.type != "group") {
                currentValue.home_team = typeof homeTeam === 'object' ? homeTeam.id : null;
                currentValue.away_team = typeof awayTeam === 'object' ? awayTeam.id : null;
            }
            return this.props.playername !== undefined &&
                this.props.username === this.props.playername &&
                !currentValue.finished && currentValue.editable ?
                this.state.editables.indexOf(currentValue.id) !== -1 ?
                    [this.renderSubtitle(currentValue.groupname), <MatchEdit round={index + 1} key={"match" + currentValue.id}
                        id={currentValue.id}
                        date={zeroPad(d.getDate(), 2) + "/" + zeroPad(d.getMonth(), 2) + " " + zeroPad(d.getHours(), 2) + ":" + zeroPad(d.getMinutes(), 2)}
                        homeTeam={homeTeam}
                        awayTeam={awayTeam}
                        match={currentValue}
                        toggleEdit={(index) => this.toggleEdit(index)}
                        inc={(index, home) => this.incrementScore(index, home)}
                        dec={(index, home) => this.decrementScore(index, home)}
                        matchStatus={currentValue.status} />]
                    :
                    [this.renderSubtitle(currentValue.groupname), <MatchUser round={index + 1} key={"match" + currentValue.id}
                        id={currentValue.id}
                        date={zeroPad(d.getDate(), 2) + "/" + zeroPad(d.getMonth(), 2) + " " + zeroPad(d.getHours(), 2) + ":" + zeroPad(d.getMinutes(), 2)}
                        homeTeam={homeTeam}
                        awayTeam={awayTeam}
                        match={currentValue}
                        toggleEdit={(index) => this.toggleEdit(index)}
                        matchStatus={currentValue.status} />]
                :
                [this.renderSubtitle(currentValue.groupname), <Match round={index + 1} key={"match" + currentValue.id}
                    date={zeroPad(d.getDate(), 2) + "/" + zeroPad(d.getMonth(), 2) + " " + zeroPad(d.getHours(), 2) + ":" + zeroPad(d.getMinutes(), 2)}
                    homeTeam={homeTeam}
                    awayTeam={awayTeam}
                    match={currentValue}
                    matchStatus={currentValue.status} />]
        }.bind(this))
    }


    render() {
        return this.state.showLoading ? <Loading /> : this.renderMatches();
    }
}



function Match(props) {
    var homeP = "";
    var awayP = "";
    if (props.match.home_result != undefined && props.match.home_result == props.match.away_result && props.match.type != "group") {
        homeP = "(" + (props.match.home_penalty == undefined ? "*" : props.match.home_penalty) + ")";
        awayP = "(" + (props.match.away_penalty == undefined ? "*" : props.match.away_penalty) + ")";
    }


    return (
        <div className="match">
            <div>{props.round}</div>
            <div className="matchInfo">
                <div><div>{props.date}</div></div>
                <div className={"teamMatch " + colorScore(props.matchStatus)}>
                    <div className="teambox">
                        <div>{typeof props.homeTeam === 'object' ? props.homeTeam.shortCode : props.homeTeam}</div>
                        <div><div className={"flag flag-" + (typeof props.homeTeam === 'object' ? props.homeTeam.flagUrl : "none")}></div></div>
                        <div>{(props.match.home_result == undefined ? "*" : props.match.home_result) + homeP}</div>
                    </div>
                    <div className="teamboxSeparator">-</div>
                    <div className="teambox left">
                        <div>{awayP + (props.match.away_result == undefined ? "*" : props.match.away_result)}</div>
                        <div><div className={"flag flag-" + (typeof props.awayTeam === 'object' ? props.awayTeam.flagUrl : "none")}></div></div>
                        <div>{typeof props.awayTeam === 'object' ? props.awayTeam.shortCode : props.awayTeam}</div>
                    </div>
                </div>
            </div>
        </div>
    );
}

function MatchUser(props) {
    var homeP = "";
    var awayP = "";
    if (props.match.home_result != undefined && props.match.home_result == props.match.away_result && props.match.type != "group") {
        homeP = "(" + (props.match.home_penalty == undefined ? "*" : props.match.home_penalty) + ")";
        awayP = "(" + (props.match.away_penalty == undefined ? "*" : props.match.away_penalty) + ")";
    }


    return (
        <div className="match user">
            <div>{props.round}</div>
            <div className="matchInfo">
                <div><div>{props.date}</div></div>
                <div className={"teamMatch " + colorScore(props.matchStatus)}>
                    <div className="teambox">
                        <div>{typeof props.homeTeam === 'object' ? props.homeTeam.shortCode : props.homeTeam}</div>
                        <div><div className={"flag flag-" + (typeof props.homeTeam === 'object' ? props.homeTeam.flagUrl : "none")}></div></div>
                        <div>{(props.match.home_result == undefined ? "*" : props.match.home_result) + homeP}</div>
                    </div>
                    <div className="teamboxSeparator">-</div>
                    <div className="teambox left">
                        <div>{awayP + (props.match.away_result == undefined ? "*" : props.match.away_result)}</div>
                        <div><div className={"flag flag-" + (typeof props.awayTeam === 'object' ? props.awayTeam.flagUrl : "none")}></div></div>
                        <div>{typeof props.awayTeam === 'object' ? props.awayTeam.shortCode : props.awayTeam}</div>
                    </div>
                </div>
            </div>
            <div onClick={() => props.toggleEdit(props.id)}><div className="iconCenter"><i className="fas fa-edit"></i></div></div>
        </div>
    )
}

function MatchEdit(props) {
    var homeP = "";
    var awayP = "";
    if (props.match.home_result != undefined && props.match.home_result == props.match.away_result && props.match.type != "group") {
        homeP = "(" + (props.match.home_penalty == undefined ? "*" : props.match.home_penalty) + ")";
        awayP = "(" + (props.match.away_penalty == undefined ? "*" : props.match.away_penalty) + ")";
    }

    return (<div className="match user edit">
        <div>{props.round}</div>
        <div className="matchInfo">
            <div><div>{props.date}</div></div>
            <div className={"teamMatch " + colorScore(props.matchStatus)}>
                <div className="teambox">
                    <div>{typeof props.homeTeam === 'object' ? props.homeTeam.shortCode : props.homeTeam}</div>
                    <div><div className={"flag flag-" + (typeof props.homeTeam === 'object' ? props.homeTeam.flagUrl : "none")}></div></div>
                </div>
                <div className="teamboxSeparator">-</div>
                <div className="teambox">
                    <div></div>
                    <div><div className={"flag flag-" + (typeof props.awayTeam === 'object' ? props.awayTeam.flagUrl : "none")}></div></div>
                    <div>{typeof props.awayTeam === 'object' ? props.awayTeam.shortCode : props.awayTeam}</div>
                </div>
            </div>
            <div className="editBox">
                <div onClick={() => props.dec(props.id, "home_result")} > <i className="fas fa-chevron-circle-left"></i> </div>
                <div>{props.match.home_result == undefined ? "*" : props.match.home_result}</div>
                <div onClick={() => props.inc(props.id, "home_result")}> <i className="fas fa-chevron-circle-right"></i> </div>
                <div></div>
                <div onClick={() => props.dec(props.id, "away_result")}> <i className="fas fa-chevron-circle-left"></i> </div>
                <div>{props.match.away_result == undefined ? "*" : props.match.away_result}</div>
                <div onClick={() => props.inc(props.id, "away_result")}> <i className="fas fa-chevron-circle-right"></i> </div>
            </div>
            {homeP != "" && <div className="editBox">
                <div onClick={() => props.dec(props.id, "home_penalty")} > <i className="fas fa-chevron-circle-left"></i> </div>
                <div>{props.match.home_penalty == undefined ? "*" : props.match.home_penalty}</div>
                <div onClick={() => props.inc(props.id, "home_penalty")}> <i className="fas fa-chevron-circle-right"></i> </div>
                <div></div>
                <div onClick={() => props.dec(props.id, "away_penalty")}> <i className="fas fa-chevron-circle-left"></i> </div>
                <div>{props.match.away_penalty == undefined ? "*" : props.match.away_penalty}</div>
                <div onClick={() => props.inc(props.id, "away_penalty")}> <i className="fas fa-chevron-circle-right"></i> </div>
            </div>}
        </div>
        <div onClick={() => props.toggleEdit(props.id)} ><div className="iconCenter"><i className="fas fa-save"></i></div></div>
    </div>)
}

export default ContentMatch;