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
        }
        return <div></div>

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