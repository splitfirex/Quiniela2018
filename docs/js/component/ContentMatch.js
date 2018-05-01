class ContentMatch extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentSubtitle: "",
            showLoading: false,
            content: [],
            editables:[]
        }
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.forceReload) {
            this.props.dispatch({ type: "UNFORCE" });
            fetchLadders.bind(this)();
        }
    }

    toggleEdit(index) {
        if(this.state.editables.indexOf(index) == -1){
            this.state.editables.push(index);
        }else{
            this.state.editables.splice(this.state.editables.indexOf(index), 1);
            fetchUpdateScore.bind(this)(index, this.state.content[index].hS,this.state.content[index].vS);
        } 
        this.setState({
            editables: this.state.editables
        });
    
    }

    incrementScore(index, isHome) {
        if (isHome) {
            this.state.content[index].hS = this.state.content[index].hS == null ? 0 : this.state.content[index].hS + 1;
        } else {
            this.state.content[index].vS = this.state.content[index].vS == null ? 0 : this.state.content[index].vS + 1;
        }
        this.setState({
            content: this.state.content
        });
    }

    decrementScore(index, isHome) {
        if (isHome) {
            this.state.content[index].hS = this.state.content[index].hS == null ? null : this.state.content[index].hS - 1;
            if (this.state.content[index].hS == -1) this.state.content[index].hS = null;
        } else {
            this.state.content[index].vS = this.state.content[index].vS == null ? null : this.state.content[index].vS - 1;
            if (this.state.content[index].vS == -1) this.state.content[index].vS = null
        }
        this.setState({
            content: this.state.content
        });
    }

    componentDidMount() {
        fetchMatches.bind(this)();
    }

    dispatch(action) {
        this.setState(preState => GlobalAppActions(preState, action));
    }
    
    renderSubtitle(newSubtitle){
        if(this.state.currentSubtitle == newSubtitle){
            return null;
        }
        this.state.currentSubtitle = newSubtitle;
        return  <div>{this._translate(newSubtitle)}</div>
    }

    _translate(value){
        switch(value){
            case "GROUP_PHASE": return "Fase de grupos";
            case "ROUND_OF_16": return "Octavos de final";
            case "QUARTER_FINALS": return "Cuartos de final";
            case "SEMI_FINALS": return "Semi Finales";
            case "FINALS": return "Finales";
        }
    }

    renderMatches() {
        return this.state.content.map(function (currentValue, index, array) {
            var d = new Date(this.props.matches[index].date);
            return this.props.playername != undefined && this.props.username == this.props.playername ?
                this.state.editables.indexOf(index) != -1 ?
                [this.renderSubtitle(this.props.matches[index].typeMatch),<MatchEdit round={index + 1} key={"match" + index}
                        index={index}
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
                        toggleEdit={(index) => this.toggleEdit(index)}
                        inc={(index, home) => this.incrementScore(index, home)}
                        dec={(index, home) => this.decrementScore(index, home)} />]
                    :
                    [this.renderSubtitle(this.props.matches[index].typeMatch),<MatchUser round={index + 1} key={"match" + index}
                        index={index}
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
                        toggleEdit={(index) => this.toggleEdit(index)} />]
                :
                [this.renderSubtitle(this.props.matches[index].typeMatch),<Match round={index + 1} key={"match" + index}
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
                    visitorTeamScore={currentValue.vS == null ? "*" : currentValue.vS} />]
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
                <div className="teamMatch">
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
                <div className="teamMatch">
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
            <div className="teamMatch">
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