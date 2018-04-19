class GlobalContent extends React.Component {

    render() {
        return <div className={"content-warp " + (this.props.renderBreadcrumbs ? "showBreadcrumbs" : "")} >
            <div className="content">
                <div className="title">Quinielas</div>

                <div className="newLadder">
                    <div> <i className="fas fa-plus"></i> Crear nueva quiniela</div>
                </div>

                <ContentLadder />

                <div className="title">Jugadores</div>

                <ContentPlayer />

                <div className="title">Partidos</div>

                <ContentMatch />

            </div>
        </div>
    }

}



GlobalContent.defaultProps = {
    renderBreadcrumbs: false,
    username: null,
    laddername: null,
    playername: null
}