import React from 'react';
import { GlobalAppActions, fetchGroups } from '../lib/actions.js'
import Loading from './ContentUtils.jsx';


export class ContentGroup extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showLoading: false,
            content: []
        }
    }

    dispatch(action) {
        this.setState(preState => GlobalAppActions(preState, action));
    }

    componentDidMount() {
        fetchGroups.bind(this)();
    }

    renderGroups() {
        return this.state.content.map(function (currentValue, index, array) {
            return <Group key={"groupbox" + index} groupid={currentValue.idGroup}
                details={currentValue.details}  {...this.props} />
        }.bind(this));
    }

    render() {
        return this.state.showLoading ? <Loading /> : this.renderGroups();
    }
}


function Group(props) {
    return (
        <div className="group">
            <div className="title">
                <div> Grupo {props.groups[props.groupid - 1].name}</div>
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
                return <GroupRow key={"GroupRow" + index} idTeam={ele.id} ng={ele.ng} p={ele.p} pg={ele.pg} teams={props.teams} />
            })}
        </div>
    )
}


function GroupRow(props) {
    return (
        <div className="row" >
            <div>
                <div className={"flag flag-" + props.teams[props.idTeam].flagUrl}></div>
            </div>
            <div>{props.teams[props.idTeam].name}</div>
            <div>{props.pg}</div>
            <div>{props.ng}</div>
            <div>{props.pg - props.ng}</div>
            <div>{props.p}</div>
        </div>
    )
}

export default ContentGroup;