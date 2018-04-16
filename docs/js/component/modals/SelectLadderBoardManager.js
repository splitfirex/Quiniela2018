class SelectLadderBoardManager extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            ladders : null
        }
    }

    componentDidMount() {
        postPlayerLadders(this.processLadders.bind(this));
    }

    componentWillReceiveProps(nextProps) {
        postPlayerLadders(this.processLadders.bind(this));
    }

    processLadders(responseLadders) {
        this.setState({
            "ladders": responseLadders
        });
    }

    selectLadder(ladderName){
        this.props.fnChangeLadder(ladderName);
    }

    getLadders() {
        if (this.state.ladders == null) {
            return <Loading />
        }
        var that = this;
        return this.state.ladders.map(function (currentValue, index, array) {
            return  <div onClick={that.selectLadder.bind(that, currentValue.name)}>
            <div><i className="fas fa-check-circle"></i></div>
            <div>{currentValue.name}</div>
        </div>
        });

    }


    render() {
        return <div key="joinLadderBoard" className="joinLadderBoard">
           {this.getLadders()} 
        </div>
    }
}