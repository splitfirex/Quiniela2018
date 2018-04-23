class GlobalContent extends React.Component {

    constructor(props) {
        super(props);

    }


    renderTitle() {
        if (this.props.currentWindow == "Inicio") {
            return (<div className="title">Quinielas</div>)
        } else if (this.props.currentWindow == "Grupos") {
            return <div className="title">Grupos</div>
        } else if (this.props.currentWindow == "Partidos") {
            return <div className="title">Partidos</div>
        }else if (this.props.currentWindow == "Jugadores") {
            return <div className="title">Jugadores</div>
        }
        return <div className="title">{this.props.laddername}</div>
    }


    renderContent(){
        if (this.props.currentWindow == "Inicio") {
            return <ContentLadder {...this.props} />
        } else if (this.props.currentWindow == "Partidos") {
            return <ContentMatch {...this.props} />
        } else if (this.props.currentWindow == "Jugadores") {
            return <ContentPlayer {...this.props} />
        } else if (this.props.currentWindow == "Grupos") {
            return <ContentGroup {...this.props} />
        } 
    }


    render() {

        if (this.props.currentWindow == "Inicio" && this.props.username != null) {
            var newQuiniela = <div className="newLadder" onClick={this.props.fnOnClickGoTo.bind(null, "Nueva quiniela")}>
                <div> <i className="fas fa-plus" ></i> Crear nueva quiniela</div>
            </div>
        }

        return <div className={"content-warp " + (this.props.renderBreadcrumbs ? "showBreadcrumbs" : "")} >
            <div className="content">

                {this.renderTitle()}

                {newQuiniela}

                {this.renderContent()}

            </div>
        </div>
    }

}

GlobalContent.defaultProps = {
    renderBreadcrumbs: false,
    username: null,
    laddername: null,
    playername: null,
    currentWindow: "Inicio"
}