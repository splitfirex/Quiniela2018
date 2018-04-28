class SideMenu extends React.Component {

    renderIniciarSesion() {
        if (this.props.username == null) {
            return <div onClick={() => this.props.dispatch({ type: "GO_TO", dest: "SIGN_IN" })}><i className="fas fa-sign-in-alt"></i> Iniciar sesion</div>
        }

        return <div onClick={() => this.props.dispatch({ type: "SUCCESS_LOGOUT" })}><i className="fas fa-sign-out-alt"></i> Cerrar sesion</div>
    }


    render() {
        return <div className={"sideMenu-wrap " + (this.props.showMenu && "show")}>
            <div className="sideMenu-content">
                <div></div>
                <div onClick={() => this.props.dispatch({ type: "GO_TO", dest: "SHOW_HOME" })}><i className="fas fa-home"></i> Inicio</div>
                <div onClick={() => this.props.dispatch({ type: "GO_TO", dest: "SHOW_GROUPS" })}><i className="far fa-list-alt"></i> Grupos</div>
                <div onClick={() => this.props.dispatch({ type: "GO_TO", dest: "SHOW_MATCHES" })} ><i className="fas fa-futbol"></i> Partidos</div>
                {this.renderIniciarSesion()}
            </div>
        </div>
    }
}