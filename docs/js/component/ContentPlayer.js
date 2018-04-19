class ContentPlayer extends React.Component {

    render() {
        return [<Player />, <PlayerAdmin />]
    }

}

ContentPlayer.defaultProps = {
    username : null,
    laddername : null
}


function Player(props) {
    return (
        <div className="player">
            <div className="playerShow">
                <div> Nombre jugador</div>
            </div>
            <div className="playerContent">
                <div>63</div>
                <div className="teamMatch" style={{ "borderBottom": "grey 1px solid" }}>
                    <div className="teambox">
                        <div>FRA</div>
                        <div><div className="flag RUS"></div></div>
                        <div>3</div>
                    </div>
                    <div className="teamboxSeparator">-</div>
                    <div className="teambox left">
                        <div>6</div>
                        <div><div className="flag BRA"></div></div>
                        <div>BRA</div>
                    </div>
                </div>
            </div>
            <div className="playerMenu">
                <div><div className="iconCenter"><i className="fas fa-list-ol"></i> Partidos</div></div>
                <div><div className="iconCenter"><i className="fas fa-list-alt"></i> Grupos</div></div>
            </div>
        </div>
    )
}

function PlayerAdmin(props) {
    return (
        <div className="player">
            <div className="playerShow">
                <div> Nombre jugador</div>
            </div>
            <div className="playerContent">
                <div>63</div>
                <div className="teamMatch" style={{ "borderBottom": "grey 1px solid" }}>
                    <div className="teambox">
                        <div>FRA</div>
                        <div><div className="flag RUS"></div></div>
                        <div>3</div>
                    </div>
                    <div className="teamboxSeparator">-</div>
                    <div className="teambox left">
                        <div>6</div>
                        <div><div className="flag BRA"></div></div>
                        <div>BRA</div>
                    </div>
                </div>
                <div>63</div>
                <div className="teamMatch" style={{ "borderBottom": "grey 1px solid" }}>
                    <div className="teambox">
                        <div>FRA</div>
                        <div><div className="flag RUS"></div></div>
                        <div>3</div>
                    </div>
                    <div className="teamboxSeparator">-</div>
                    <div className="teambox left">
                        <div>6</div>
                        <div><div className="flag BRA"></div></div>
                        <div>BRA</div>
                    </div>
                </div>
            </div>
            <div className="playerMenu admin">
                <div><div className="iconCenter"><i className="fas fa-list-ol"></i> Partidos</div></div>
                <div><div className="iconCenter"><i className="fas fa-list-alt"></i> Grupos</div></div>
                <div><div className="iconCenter"><i className="fas fa-at"></i></div></div>
                <div><div className="iconCenter"><i className="fas fa-check"></i></div></div>
                <div><div className="iconCenter"><i className="fas fa-sign-in-alt"></i></div></div>
            </div>
        </div>
    )
}