class ContentLadder extends React.Component {

    render() {
        return [<Ladder/>,<LoggedLadder/>]
    }

}

ContentLadder.defaultProps = {
    username : null,
    laddername : null,
    playername: null
}


function LoggedLadder(props) {

    return (
        <div className="ladder">
            <div className="ladderShow">
                <div>
                    LadderName
            </div>
            </div>
            <div className="ladderMenu">
                <div>Jugadores: 10</div>
                <div><div className="iconCenter"><i className="fas fa-users"></i></div></div>
                <div><div className="iconCenter"><i className="fas fa-sign-in-alt"></i></div></div>
                <div><div className="iconCenter"><i className="fas fa-lock"></i></div></div>
            </div>
        </div>
    )
}

function Ladder(props) {

    return (
        <div className="ladder logged">
            <div className="ladderShow">
                <div>
                    LadderName
            </div>
            </div>
            <div className="ladderMenu">
                <div>Jugadores: 10</div>
                <div><div className="iconCenter"><i className="fas fa-lock"></i></div></div>
            </div>
        </div>
    )
}