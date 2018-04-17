class Content extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            title: "LADDERBOARDS"
        }
    }

    componentDidMount() {
        if (this.props.currentLadder == null) {
            this.setState({ title: "LADDERBOARDS" })
        }
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.currentLadder == null) {
            this.setState({ title: "LADDERBOARDS" })
        } else if (nextProps.currentLadder != null && nextProps.currentPlayer != null ) {
            this.setState({ title: nextProps.currentLadder + " - "+ nextProps.currentPlayer })
        }else{
            this.setState({ title: nextProps.currentLadder + " - PLAYERS" })
        }
    }

    renderContent() {
        if (this.props.currentLadder == null) {
            return <ShowLadders loadPlayers={this.props.loadPlayers} />
        } else {
            return <ShowPlayers
                fnShowMatches={this.props.fnShowMatches}
                ladderName={this.props.currentLadder}
                username={this.props.currentPlayer} />
        }
    }

    render() {
        return <div id="content-wrap">
            <div className="section-title">{this.state.title}</div>
            {this.renderContent()}
        </div>
    }
}

Content.defaultProps = {
    currentLadder: null
}