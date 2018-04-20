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
        }
    }


    renderContent(){
        if (this.props.currentWindow == "Inicio") {
            return <ContentLadder />
        } else if (this.props.currentWindow == "Partidos") {
            return <ContentMatch />
        } else if (this.props.currentWindow == "Jugadores") {
            return <ContentPlayer />
        } else if (this.props.currentWindow == "Grupos") {
            return <ContentGroup />
        }
    }

    render() {

        if (this.props.currentWindow == "Inicio" && this.props.username != null) {
            var newQuiniela = <div className="newLadder">
                <div> <i className="fas fa-plus"></i> Crear nueva quiniela</div>
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