class ContentLadder extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            ladders: []
        }
    }

    processLadders(responseLadders) {
        this.setState({
            ladders: responseLadders
        });
    }

    componentDidMount() {
        getPlayerLadders(this.processLadders.bind(this));
    }

    componentWillReceiveProps(nextProps) {
       getPlayerLadders(this.processLadders.bind(this));
    }

    renderLadders() {
        var colors = getGradient(this.state.ladders.length);
        var that = this;
        return this.state.ladders.map(function (currentValue, index, array) {
            if (that.props.username != null) {
                return <LoggedLadder
                    key={"Ladder" + index}
                    usersCount={currentValue.listPlayers.length}
                    containsUser={currentValue.listPlayers.filter(user => (user.username === that.props.username && user.active)).length != 0 }
                    containsPendingUser={currentValue.listPlayers.filter(user => (user.username === that.props.username && !user.active)).length != 0 }
                    name={currentValue.name}
                    protected={currentValue.protected}
                    bgColor={colors.next()}
                    showPlayers={that.props.loadPlayers}
                    fnOnClick={that.props.fnOnClickLadder}
                    fnOnClickGoTo={that.props.fnOnClickJoinLadder.bind(null,currentValue.name,currentValue.protected)}
                />
            } else {
                return <Ladder
                    key={"Ladder" + index}
                    usersCount={currentValue.listPlayers.length}
                    name={currentValue.name}
                    protected={currentValue.protected}
                    bgColor={colors.next()}
                    showPlayers={that.props.loadPlayers}
                    fnOnClick={that.props.fnOnClickLadder}
                    fnOnClickProtected={that.props.fnOnClickGoTo.bind(null,"Iniciar Sesion")}
                />
            }
        })

    }


    render() {
        if (this.state.ladders.length == 0) {
            return <Loading />
        }

        return this.renderLadders()
    }

}

ContentLadder.defaultProps = {
    username: null,
    laddername: null,
    playername: null
}


function LoggedLadder(props) {

    var protectedIcon = <div><div className="iconCenter"><i className="fas fa-users"></i></div></div>

    if (props.protected) {
        var protectedIcon = <div><div className="iconCenter"><i className="fas fa-lock"></i></div></div>;
    }

    if (props.containsUser && props.protected) {
        var protectedIcon = <div><div className="iconCenter"><i className="fas fa-unlock"></i></div></div>;
    }

    var joinIcon = <div onClick={props.fnOnClickGoTo} ><div className="iconCenter"><i className="fas fa-user-plus"></i></div></div>;

    if( props.containsUser ){
        var joinIcon = <div style={{border:"unset"}}></div>;
    }

    if (props.containsPendingUser) {
        var joinIcon = <div><div className="iconCenter"><i className="far fa-clock"></i></div></div>;
    }

    return (
        <div key={props.name + props.usersCount} className="ladder"  >
            <div className="ladderShow" onClick={!props.protected || props.containsUser ? props.fnOnClick.bind(null, props.name) : function(){}}>
                <div>
                    {props.name}
                </div>
            </div>
            <div className="ladderMenu">
                <div>Jugadores:  {props.usersCount}</div>
                {joinIcon}
                {protectedIcon}
            </div>
        </div>
    )
}

function Ladder(props) {

    return (
        <div  key={props.name + props.usersCount} className="ladder logged" onClick={!props.protected ? props.fnOnClick.bind(null, props.name) : props.fnOnClickProtected}>
            <div className="ladderShow" >
                <div>
                    {props.name}
                </div>
            </div>
            <div className="ladderMenu">
                <div>Jugadores: {props.usersCount}</div>
                <div><div className="iconCenter"><i className={props.protected ? "fas fa-lock" : "fas fa-users"}></i></div></div>
            </div>
        </div>
    )
}