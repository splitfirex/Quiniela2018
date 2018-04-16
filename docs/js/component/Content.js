class Content extends React.Component {

    constructor(props) {
        super(props);
        this.state ={
            title : "LADDERBOARDS"
        }
    }

    componentDidMount() {
        if(this.props.currentLadder == null){
            this.setState({title: "LADDERBOARDS"})
        }
    }

    componentWillReceiveProps(nextProps) {
        if(nextProps.currentLadder == null){
            this.setState({title: "LADDERBOARDS"})
        }else{
            this.setState({title: "PLAYERS"})
        }
    }

    renderContent(){
        if(this.props.currentLadder == null){
            return <ShowLadders loadPlayers={this.props.loadPlayers} />
        }else{
            return <ShowPlayers ladderName={this.props.currentLadder} />
        }
    }

    render() {
        return <div id="content-wrap">
           <div className="section-title">{this.state.title}</div>
           {this.renderContent()}
        </div>
    }
}

Content.defaultProps={
    currentLadder : null
}