class SideMenu extends React.Component {

    renderIniciarSesion(){
        if(this.props.username == null){
            return <div onClick={this.props.fnOnClickGoTo.bind(null,"Iniciar Sesion")}><i className="fas fa-sign-in-alt"></i> Iniciar sesion</div>
        }

       return <div onClick={this.props.fnOnClickGoTo.bind(null,"Cerrar Sesion")}><i className="fas fa-sign-out-alt"></i> Cerrar sesion</div>
    }


    render(){
        return <div className={"sideMenu-wrap "+ (this.props.renderSideMenu ? "show" : "")}>
            <div className="sideMenu-content">
                <div></div>
                <div onClick={this.props.fnOnClickGoTo.bind(null,"Inicio")}><i className="fas fa-home"></i> Inicio</div>
                <div onClick={this.props.fnOnClickGoTo.bind(null,"Grupos")}><i className="far fa-list-alt"></i>  Grupos</div>
                <div onClick={this.props.fnOnClickGoTo.bind(null,"Partidos")}><i className="fas fa-futbol"></i> Partidos</div>
                {this.renderIniciarSesion()}
            </div>
        </div>
    }
}

SideMenu.defaultProps = {
    renderSideMenu: false,
    username: null,
    fnOnClickGoTo:function(){}
}