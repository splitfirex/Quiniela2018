class SelectLadderBoardManager extends React.Component {

    constructor(props) {
        super(props);
    }

    onClickLadder(){
        
    }

    render() {
        return <div key="joinLadderBoard" className="joinLadderBoard">
            <div onClick={this.onClickLadder.bind(this)}>
                <div><i className="fas fa-lock"></i></div>
                <div>Splitfire</div>
            </div>
            <div>
                <div><i className="fas fa-unlock"></i></div>
                <div>Splitfire</div>
            </div>
        </div>
    }
}