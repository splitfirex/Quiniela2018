class SideMenu extends React.Component {

    render(){
        return <div className={"sideMenu-wrap "+ (this.props.renderSideMenu ? "show" : "")}>
            <div className="sideMenu-content">
                <div></div>
                <div onClick={this.props.fnOnClickGoTo.bind(null,"Inicio")}>Inicio</div>
                <div onClick={this.props.fnOnClickGoTo.bind(null,"Grupos")}>Grupos</div>
                <div onClick={this.props.fnOnClickGoTo.bind(null,"Partidos")}>Partidos</div>
                <div onClick={this.props.fnOnClickGoTo.bind(null,"Partidos")}>Iniciar sesion</div>
            </div>
        </div>
    }
}

SideMenu.defaultProps = {
    renderSideMenu: false,
    fnOnClickGoTo:function(){}
}