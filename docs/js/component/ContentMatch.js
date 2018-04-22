class ContentMatch extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            matches: []
        }
    }



    componentDidMount() {
        getPlayerMatches(
            this.props.playername == null ? genericPlayername : this.props.playername,
            this.props.laddername == null ? genericLaddername : this.props.laddername,
            this.processMatches.bind(this));
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.playername != nextProps.playername || nextProps.laddername != this.props.laddername) {
            getPlayerMatches(
                nextProps.playername == null ? genericPlayername : nextProps.playername,
                nextProps.laddername == null ? genericLaddername : nextProps.laddername,
                this.processMatches.bind(this));
        }
    }

    processMatches(reponseMatches) {
        this.setState({
            matches: reponseMatches
        });
    }

    renderMatches() {
        return this.state.matches.map(function (currentValue, index, array) {
            var d = new Date(matches[index].date);
            return <Match round={index + 1} key={"match" + index}
                date={zeroPad(d.getDate(), 2) + "/" + zeroPad(d.getMonth(), 2) + " " + zeroPad(d.getHours(), 2) + ":" + zeroPad(d.getMinutes(), 2)}
                homeTeamShort={teams[currentValue.hT - 1] == undefined ?
                    matches[index].homeTeam : teams[currentValue.hT - 1].shortName}
                visitorTeamShort={teams[currentValue.vT - 1] == undefined ?
                    matches[index].visitorTeam : teams[currentValue.vT - 1].shortName}
                flagUrlHome={teams[currentValue.hT - 1] == undefined ?
                    "none": teams[currentValue.hT - 1].flagUrl}
                flagUrlVisitor={teams[currentValue.vT - 1] == undefined ?
                    "none" : teams[currentValue.vT - 1].flagUrl}

                homeTeamScore={currentValue.hS == null ? "*" : currentValue.hS}
                visitorTeamScore={currentValue.vS == null ? "*" : currentValue.vS} />
        })
    }

    renderMatchesUser() {

    }

    render() {

        if (this.state.matches.length == 0) {
            return <Loading />
        } else if (this.props.playername == this.props.username) {

        }

        return this.renderMatches();
    }
}

ContentMatch.defaultProps = {
    username: null,
    laddername: null,
    playername: null
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
            <div>63</div>
            <div className="matchInfo">
                <div><div>12/12 12:59</div></div>
                <div className="teamMatch">
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
            <div><div className="iconCenter"><i className="fas fa-edit"></i></div></div>
        </div>
    )
}

function MatchEdit(props) {
    return (<div className="match user edit">
        <div>63</div>
        <div className="matchInfo">
            <div><div>12/12 12:59</div></div>
            <div className="teamMatch">
                <div className="teambox right">
                    <div>FRA</div>
                    <div><div className="flag RUS"></div></div>
                </div>
                <div className="teamboxSeparator">-</div>
                <div className="teambox left">
                    <div><div className="flag BRA"></div></div>
                    <div>BRA</div>
                </div>
            </div>
            <div className="editBox">
                <div> <i className="fas fa-chevron-circle-left"></i> </div>
                <div> 5</div>
                <div> <i className="fas fa-chevron-circle-right"></i> </div>
                <div></div>
                <div> <i className="fas fa-chevron-circle-left"></i> </div>
                <div> 5</div>
                <div> <i className="fas fa-chevron-circle-right"></i> </div>
            </div>
        </div>
        <div><div className="iconCenter"><i className="fas fa-edit"></i></div></div>
    </div>)
}