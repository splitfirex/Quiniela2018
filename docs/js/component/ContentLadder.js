class ContentLadder extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            ladders: []
        }
    }

    processLadders(responseLadders) {
        this.setState({
            ladders: responseLadders
        });
    }


    componentDidMount() {
        getPlayerLadders(this.processLadders.bind(this));
    }

    componentWillReceiveProps(nextProps) {
        getPlayerLadders(this.processLadders.bind(this));
    }

    renderLadders() {
        var colors = getGradient(this.state.ladders.length);
        var that = this;
        return this.state.ladders.map(function (currentValue, index, array) {
            return <Ladder
                key={"Ladder" + index}
                usersCount={currentValue.listPlayers.length}
                name={currentValue.name}
                protected={currentValue.protected}
                bgColor={colors.next()}
                showPlayers={that.props.loadPlayers}
                fnOnClick={that.props.fnOnClickLadder}
            />
        })

    }


    render() {
        if (this.state.ladders.length == 0) {
            return <Loading />
        }

        return this.renderLadders()
    }

}

ContentLadder.defaultProps = {
    username: null,
    laddername: null,
    playername: null
}


function LoggedLadder(props) {

    return (
        <div className="ladder">
            <div className="ladderShow">
                <div>
                    {props.name}
                </div>
            </div>
            <div className="ladderMenu">
                <div>Jugadores:  {props.usersCount}</div>
                <div><div className="iconCenter"><i className="fas fa-users"></i></div></div>
                <div><div className="iconCenter"><i className="fas fa-sign-in-alt"></i></div></div>
                <div><div className="iconCenter"><i className="fas fa-lock"></i></div></div>
            </div>
        </div>
    )
}

function Ladder(props) {

    return (
        <div className="ladder logged"  onClick={props.fnOnClick.bind(null, props.name)}>
            <div className="ladderShow">
                <div>
                    {props.name}
                </div>
            </div>
            <div className="ladderMenu">
                <div>Jugadores: {props.usersCount}</div>
                <div><div className="iconCenter"><i className={props.protected ? "fas fa-lock" : "fas fa-users"}></i></div></div>
            </div>
        </div>
    )
}