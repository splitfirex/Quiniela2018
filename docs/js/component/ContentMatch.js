class ContentMatch extends React.Component {

    render() {
        return [ <MatchEdit />,<Match />, <MatchUser />]
    }
}

ContentMatch.defaultProps = {
    username : null,
    laddername : null
}

function Match(props) {
    return (
        <div className="match">
            <div>63</div>
            <div className="matchInfo">
                <div><div>12/12 12:59</div></div>
                <div className="teamMatch">
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
        </div>
    );
}

function MatchUser(props) {
    return (
        <div className="match user">
            <div>63</div>
            <div className="matchInfo">
                <div><div>12/12 12:59</div></div>
                <div className="teamMatch">
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
            <div><div className="iconCenter"><i className="fas fa-edit"></i></div></div>
        </div>
    )
}

function MatchEdit(props) {
    return (<div className="match user edit">
        <div>63</div>
        <div className="matchInfo">
            <div><div>12/12 12:59</div></div>
            <div className="teamMatch">
                <div className="teambox right">
                    <div>FRA</div>
                    <div><div className="flag RUS"></div></div>
                </div>
                <div className="teamboxSeparator">-</div>
                <div className="teambox left">
                    <div><div className="flag BRA"></div></div>
                    <div>BRA</div>
                </div>
            </div>
            <div className="editBox">
                <div> <i className="fas fa-chevron-circle-left"></i> </div>
                <div> 5</div>
                <div> <i className="fas fa-chevron-circle-right"></i> </div>
                <div></div>
                <div> <i className="fas fa-chevron-circle-left"></i> </div>
                <div> 5</div>
                <div> <i className="fas fa-chevron-circle-right"></i> </div>
            </div>
        </div>
        <div><div className="iconCenter"><i className="fas fa-edit"></i></div></div>
    </div>)
}