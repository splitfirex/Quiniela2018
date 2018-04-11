class LadderBoardManager extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showQuestion: "",
        };
    }

    onClickLadder() {
        this.setState({ showQuestion: "showQuestion" });
    }

    onClickOption() {
        this.setState({ showQuestion: "" });
    }

    render() {
        return <div>
            <div key="joinLadderBoard" className="joinLadderBoard">
                <div onClick={this.onClickLadder.bind(this)}>
                    <div><i className="fas fa-lock"></i></div>
                    <div>Splitfire</div>
                </div>
                <div>
                    <div><i className="fas fa-unlock"></i></div>
                    <div>Splitfire</div>
                </div>
            </div>
        <Question className={"question-wrap " + this.state.showQuestion} fnOnClick={this.onClickOption.bind(this)} />
        </div>
    }
}