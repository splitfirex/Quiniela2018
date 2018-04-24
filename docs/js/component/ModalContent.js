class ModalContent extends React.Component {

    constructor(props) {
        super(props);
        this.state ={
            showLogin: true,
            showRegister: false
        }
    }

    renderContent() {
        if (this.props.currentModalWindow == "Iniciar Sesion" && this.state.showLogin) {
            return <ContentLogin changeToRegister={this.changeToRegister.bind(this)} {...this.props} />
        }else if(this.props.currentModalWindow == "Iniciar Sesion" && this.state.showRegister){
            return <ContentRegister changeToLogin={this.changeToLogin.bind(this)} {...this.props} />
        }else if(this.props.currentModalWindow == "Nueva quiniela"){
            return <ContentNewLadder {...this.props} />
        }else if(this.props.currentModalWindow == "Unirse a quiniela"){
            return <ContentJoinLadder {...this.props} />
        }
        return <div style={{textAlign: "center", fontSize: "1em", alignSelf: "center" }}><i className="fas fa-cog fa-spin"></i> <br/> Procesando... </div>

    }

    changeToLogin(){
        this.setState({
            showRegister: false,
            showLogin: true
        })
    }

    changeToRegister(){
        this.setState({
            showRegister: true,
            showLogin: false
        })
    }

    render() {
        return <div className={"modalContent " + (this.props.renderModal ? "show" : "")}>
            <div className="backButton" onClick={this.props.fnOnModalBack.bind(null)}>
                <div className="iconCenter">
                    <i className="fas fa-times"></i>
                </div>
            </div>
            {this.renderContent()}
        </div>
    }
}

ModalContent.defaultProps = {
    renderModal: false,
    currentModalWindow: ""
}