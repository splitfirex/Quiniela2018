class Matches extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            "matches": null
        }
    }

    componentDidMount() {
        getPlayerMatches(
            this.props.username == null ? "_NOT_A_PLAYER" : this.props.username,
            this.props.ladderBoad == null ? "_NOT_A_LADDERBOARD_" : this.props.ladderBoad,
            this.processMatches.bind(this));
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.username != nextProps.username || nextProps.ladderBoad != this.props.ladderBoad) {
            getPlayerMatches(
                this.props.username == null ? "_NOT_A_PLAYER" : this.props.username,
                this.props.ladderBoad == null ? "_NOT_A_LADDERBOARD_" : this.props.ladderBoad,
                this.processMatches.bind(this));
        }
    }

    processMatches(reponseMatches) {
        this.setState({
            "matches": reponseMatches
        });
    }

    getMatches() {

        if (this.state.matches == null) {
            return <Loading />
        } else {
            return this.state.matches.map(function (currentValue, index, array) {
                var d = new Date(matches[index].date);
                return <Match key={"match" + index} date={zeroPad(d.getDate(), 2) + "/" + zeroPad(d.getMonth(), 2)}
                    hour={zeroPad(d.getHours(), 2) + ":" + zeroPad(d.getMinutes(), 2)}
                    homeTeamShort={teams[currentValue.hT - 1] == undefined ?
                        matches[index].homeTeam : teams[currentValue.hT - 1].shortName}

                    visitorTeamShort={teams[currentValue.vT - 1] == undefined ?
                        matches[index].visitorTeam : teams[currentValue.vT - 1].shortName}

                    homeTeamScore={currentValue.hS == null ? "*" : currentValue.hs}
                    visitorTeamScore={currentValue.vS == null ? "*" : currentValue.vs} />
            })
        }

    }

    render() {
        if ((this.props.username == null && this.props.ladderBoad == null) || (this.props.username != null)) {
            return <div id="matches-wrap">
                <div className="section-title matchesTitle">MATCHES</div>
                <div id="matches">
                    {this.getMatches()}
                </div>
            </div>
        } else {
            return <div></div>
        }

    }
}

function Match(props) {
    return (
        <div className="match-box">
            <div className="fecha">{props.date}</div>
            <div className="hora">{props.hour}</div>
            <div className="team-box-sm">
                <div>{props.homeTeamShort}</div>
                <div>
                    <div className={"flag " + (props.homeTeamShort.indexOf("_") != -1 ? "UNKOW" : props.homeTeamShort)}></div>
                </div>
                <div>{props.homeTeamScore}</div>
            </div>
            <div>-</div>
            <div className="team-box-sm">
                <div>{props.visitorTeamScore}</div>
                <div>
                    <div className={"flag " + (props.visitorTeamShort.indexOf("_") != -1 ? "UNKOW" : props.visitorTeamShort)}></div>
                </div>
                <div>{props.visitorTeamShort}</div>
            </div>
        </div>
    )
}


Matches.defaultProps = {
    username: "_NOT_A_PLAYER",
    ladderBoad: "_NOT_A_LADDERBOARD_",
}