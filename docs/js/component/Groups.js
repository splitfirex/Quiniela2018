class Groups extends React.Component {

    constructor(props) {
        super(props);
    }

    getGroups() {
        return getPlayerGroups(this.props.username, this.props.ladderBoad).map(function (currentValue, index, array) {
            return <GroupBox idGroup={currentValue.idGroup}>
                {Object.keys(currentValue.details).map(function (key, index) {
                    var ele = currentValue.details[key];
                    return <GroupRow idTeam={ele.id} ng={ele.ng} p={ele.p} pg={ele.pg} />
                })}
            </GroupBox>
        });
    }

    render() {
        return <div id="groups-wrap">
            <div className="section-title groupsTitle">GROUPS</div>
            <div id="groups">
                {this.getGroups()}
            </div>
        </div>
    }
}

Groups.defaultProps = {
    username: "_NOT_A_PLAYER",
    ladderBoad: "_NOT_A_LADDERBOARD_",
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