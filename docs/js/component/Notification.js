class Notification extends React.Component {
    render() {
        return <div className="notification" id="notification">
            <div></div>
            <div className="last-result">
                <div className="team-box">
                    <div className="teamname">BRA</div>
                    <div className="flag BRA"></div>
                    <div className="score">5</div>
                </div>
                <div>
                    -
                </div>
                <div className="team-box">
                    <div className="score">5</div>
                    <div className="flag KOR"></div>
                    <div className="teamname">KOR</div>
                </div>
            </div>
            <div></div>
        </div>
    }
}