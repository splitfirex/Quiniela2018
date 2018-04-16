class Modal extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            title: "",
            modalName: "",
        }
    }

    componentWillReceiveProps(nextProps) {
        this.setState({
            title: nextProps.showModal,
            modalName: nextProps.showModal
        })
    }

    selectModalToShow() {
        if (this.state.modalName == "PlayerManager") {
            return <PlayerManager />
        } else if (this.state.modalName == "LadderBoardManager") {
            return <LadderBoardManager />
        } else if (this.state.modalName == "SelectLadderBoardManager") {
            return <SelectLadderBoardManager fnChangeLadder={this.props.fnChangeLadder} />
        } else if (this.state.modalName == "LoginManager") {
            return <LoginManager fnLoginSuccess={this.props.fnLoginSuccess} />
        }
        return <div></div>;
    }

    render() {
        return <div id="modal" className={this.props.className} >
            <div className="header">
                <div className="menu">
                    <div onClick={this.props.fnCloseModal} >
                        <div>
                            <i className="fas fa-times" style={{ color: 'white' }}></i>
                        </div>
                    </div>
                    <div>{this.state.title}</div>
                </div>
            </div>
            <div className="content">
                {this.selectModalToShow()}
            </div>
        </div>
    }
}

Modal.defaultProps = {
    showModal: ""
}