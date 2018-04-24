class ContentGroup extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            groups: []
        }
    }

    componentDidMount() {
        getPlayerGroups(
            this.props.playername == null ? genericPlayername : this.props.playername,
            this.props.laddername == null ? genericLaddername : this.props.laddername,
            this.processGroups.bind(this));
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.playername != nextProps.playername || nextProps.playername != this.props.playername) {
            getPlayerGroups(
                nextProps.username == null ? genericPlayername : nextProps.username,
                nextProps.laddername == null ? genericLaddername : nextProps.laddername,
                this.processGroups.bind(this));
        }
    }

    processGroups(newGroups) {
        this.setState({
            groups: newGroups
        })
    }

    renderGroups() {
        return this.state.groups.map(function (currentValue, index, array) {
            return <Group key={"groupbox" + index} groupid={currentValue.idGroup}
            details={currentValue.details}>
            </Group>
        });
    }

    render() {

        if (this.state.groups.length == 0) {
            return <Loading />
        }

        return this.renderGroups();
    }
}


ContentGroup.defaultProps = {
    username: null,
    laddername: null,
    playername: null
}


function Group(props) {



    return (
        <div className="group">
            <div className="title">
                <div> Grupo {groups[props.groupid-1].name}</div>
            </div>
            <div className="row">
                <div>
                </div>
                <div>Equipo</div>
                <div>GF</div>
                <div>GC</div>
                <div>Dif</div>
                <div>P</div>
            </div>
            {Object.keys(props.details).map(function (key, index) {
                var ele = props.details[key];
                return <GroupRow key={"GroupRow" + index} idTeam={ele.id} ng={ele.ng} p={ele.p} pg={ele.pg} />
            })}
        </div>
    )
}


function GroupRow(props) {
    return (
        <div className="row" >
            <div>
                <div className={"flag flag-" + teams[props.idTeam - 1].flagUrl}></div>
            </div>
            <div>{teams[props.idTeam - 1].name}</div>
            <div>{props.pg}</div>
            <div>{props.ng}</div>
            <div>{props.pg - props.ng}</div>
            <div>{props.p}</div>
        </div>
    )
}