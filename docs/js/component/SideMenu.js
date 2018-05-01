class SideMenu extends React.Component {

    renderIniciarSesion() {
        if (this.props.username == null) {
            return <div ><i className="fas fa-sign-in-alt"></i> Iniciar sesion</div>
        }

        return
    }


    render() {
        return <div className={"sideMenu-wrap " + (this.props.showMenu && "show")}>
            <div className="sideMenu-content">
                <div> <img src="img/logo.png" style={{ height: "40px", marginTop: "40px" }} /> </div>

                {this.props.username == null ?
                    <div key="notlogged" onClick={() => this.props.dispatch({ type: "GO_TO", dest: "SIGN_IN" })} > <i className="fas fa-sign-in-alt"></i> Iniciar sesion</div> :
                    <div key="logged" className="userLogged">  {this.props.username}</div>}

                <div onClick={() => this.props.dispatch({ type: "GO_TO", dest: "SHOW_HOME" })}><i className="fas fa-home"></i> Inicio</div>
                <div onClick={() => this.props.dispatch({ type: "GO_TO", dest: "SHOW_GROUPS" })}><i className="far fa-list-alt"></i> Grupos</div>
                <div onClick={() => this.props.dispatch({ type: "GO_TO", dest: "SHOW_MATCHES" })} ><i className="fas fa-futbol"></i> Partidos</div>
                
                {this.props.username != null &&
                    <div onClick={() => this.props.dispatch({ type: "GO_TO", dest: "NEW_LADDER" })} ><i className="fas fa-plus"></i> Nueva Quiniela</div>}
                {this.props.username != null &&
                    <div onClick={() => this.props.dispatch({ type: "SUCCESS_LOGOUT" })}><i className="fas fa-sign-out-alt"></i> Cerrar sesion</div>}

            </div>
        </div>
    }
}