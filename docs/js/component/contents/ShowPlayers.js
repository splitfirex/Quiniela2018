class ShowPlayers extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            currentLadder: null
        }
    }

    componentDidMount() {
        if (this.props.ladderName != null) {
            postPlayerLaddersDetail(this.props.ladderName,this.processPlayers.bind(this));
        }
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.ladderName != null) {
            postPlayerLaddersDetail(nextProps.ladderName, this.processPlayers.bind(this));
        }
    }

    processPlayers(responseLadder) {
        this.setState({
            "currentLadder": responseLadder
        });
    }

    getPlayers(){
        if (this.state.currentLadder == null) {
            return <Loading />
        }

        return this.state.currentLadder.listPlayers.map(function (currentValue, index, array) {
            return <Player
                key={"player" + index}
                points={currentValue.points}
                username={currentValue.username}
            />
        })
    }

    render() {
        return <div id="content">
            {this.getPlayers()}

        </div>
    }

}

ShowPlayers.defaultProps = {
    ladderName: null,
}

function Player(props) {

    return (
        <div className="p1 g-2g">
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
