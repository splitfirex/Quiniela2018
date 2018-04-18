class Groups extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            groups: null
        }
    }

    componentDidMount() {
        getPlayerGroups(
            this.props.username == null ? "_NOT_A_PLAYER" : this.props.username,
            this.props.ladderBoad == null ? "_NOT_A_LADDERBOARD_" : this.props.ladderBoad,
            this.processGroups.bind(this));
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.username != nextProps.username || nextProps.ladderBoad != this.props.ladderBoad) {
            getPlayerGroups(
                nextProps.username == null ? "_NOT_A_PLAYER" : nextProps.username,
                nextProps.ladderBoad == null ? "_NOT_A_LADDERBOARD_" : nextProps.ladderBoad,
                this.processGroups.bind(this));
        }
    }

    processGroups(responseGroups) {
        this.setState({
            "groups": responseGroups
        });
    }

    getGroups() {
        if (this.state.groups == null) {
            return <Loading />
        } else {
            return this.state.groups.map(function (currentValue, index, array) {
                return <GroupBox key={"groupbox" + index} idGroup={currentValue.idGroup}>
                    {Object.keys(currentValue.details).map(function (key, index) {
                        var ele = currentValue.details[key];
                        return <GroupRow key={"GroupRow" + index} idTeam={ele.id} ng={ele.ng} p={ele.p} pg={ele.pg} />
                    })}
                </GroupBox>
            });
        }
    }

    render() {
        if ((this.props.ladderBoad == null && this.props.username == null) || ( this.props.username != null)) {
            return <div id="groups-wrap">
                <div className="section-title groupsTitle">GROUPS</div>
                <div id="groups">
                    {this.getGroups()}
                </div>
            </div>
        } else {
            return <div></div>
        }
    }
}


function GroupRow(props) {
    return (
        <div className="group-box-row">
            <div>
                <div className={"flag " + teams[props.idTeam - 1].shortName}></div>
            </div>
            <div>{teams[props.idTeam - 1].name}</div>
            <div>{props.pg}</div>
            <div>{props.ng}</div>
            <div>{props.p}</div>
        </div>
    );
}

function GroupBox(props) {
    return (
        <div className="group-box">
            <div className="group-box-content">
                <div className="group-box-title"> GROUP {groups[props.idGroup - 1].name} </div>
                <div className="group-box-row">
                    <div style={{ gridColumn: "span 2" }}>Team</div>
                    <div>GF </div>
                    <div>GC </div>
                    <div>P </div>
                </div>

                {props.children}
            </div>
        </div>
    );
}