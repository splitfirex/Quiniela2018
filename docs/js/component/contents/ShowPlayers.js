class ShowPlayers extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            currentLadder: null
        }
    }

    componentDidMount() {
        if (this.props.ladderName != null && this.props.username == null) {
            postPlayerLaddersDetail(this.props.ladderName, this.processPlayers.bind(this));
        }
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.ladderName != null && nextProps.username == null) {
            postPlayerLaddersDetail(nextProps.ladderName, this.processPlayers.bind(this));
        }
    }

    processPlayers(responseLadder) {
        this.setState({
            "currentLadder": responseLadder
        });
    }

    getPlayers() {
        if (this.state.currentLadder == null) {
            return <Loading />
        }
        var that = this;
        return this.state.currentLadder.listPlayers.map(function (currentValue, index, array) {
            return <Player
                key={"player" + index}
                points={currentValue.points}
                username={currentValue.username}
                showMatches={that.props.fnShowMatches}
            />
        })
    }

    render() {
        if (this.props.ladderName != null && this.props.username == null) {
            return <div id="content">
                {this.getPlayers()}
            </div>
        } else {
            return <div></div>
        }

    }

}

ShowPlayers.defaultProps = {
    ladderName: null,
}

function Player(props) {

    return (
        <div onClick={props.showMatches.bind(null, props.username)} className="p1 g-2g">
            <div>{props.points}</div>
            <div>|</div>
            <div>{props.username}</div>
            <div><i className="fa-inverse fas fa-user" ></i></div>
            <div>
                <i className="fa-inverse fas fa-angle-double-up" style={{ color: "greenyellow" }}></i>
            </div>
        </div>
    )
}
