class ModalContent extends React.Component {

    constructor(props) {
        super(props);

    }

    renderContent() {
        if (this.props.currentModalWindow == "Iniciar Sesion") {
            return <ContentRegister {...this.props} />
        }
        return <div></div>

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