class ContentGroup extends React.Component {

    render() {
        return [<Group />, <Group />]
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
                <div> Grupo A</div>
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
            <GroupRow/>
            <GroupRow/>
            <GroupRow/>
            <GroupRow/>
        </div>
    )
}


function GroupRow(props) {
    return (
        <div className="row" >
            <div>
                <div className="flag BRA"></div>
            </div>
            <div>Brazil</div>
            <div>2</div>
            <div>3</div>
            <div>-5</div>
            <div>4</div>
        </div>
    )
}