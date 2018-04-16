class ShowLadders extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            ladders: null
        }

    }

    componentDidMount() {
        getPlayerLadders(this.processLadders.bind(this));
    }

    componentWillReceiveProps(nextProps) {
        getPlayerLadders(this.processLadders.bind(this));
    }

    processLadders(responseLadders) {
        this.setState({
            "ladders": responseLadders
        });
    }

    getLadders() {
        if (this.state.ladders == null) {
            return <Loading />
        }
        getRandomColor();

        return this.state.ladders.map(function (currentValue, index, array) {
            return <Ladder
                key={"Ladder" + index}
                usersCount={currentValue.listPlayers.length}
                name={currentValue.name}
                protected={currentValue.protected}
                bgColor={"#FFFFFF"}
            />
        })

    }

    render() {
        return <div id="content">
            {this.getLadders()}
        </div>
    }

}

function Ladder(props) {

    var renderIcon = <i className="fa-inverse fas fa-users"></i>
    if (props.protected) {
        renderIcon = <i className="fa-inverse fas fa-lock"></i>
    }

    return (
        <div className="ladderboard" style={{ backgroundColor: props.bgColor }}>
            <div>{props.usersCount}</div>
            <div>|</div>
            <div>{props.name}</div>
            <div> </div>
            <div>{renderIcon}
            </div>
        </div>
    )
}