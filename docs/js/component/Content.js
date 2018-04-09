class Content extends React.Component {
    render() {
        return <div id="content-wrap">
            <div className="section-title playerTitle">PLAYERS</div>
            <div id="content">
                <div className="p1 g-1g">
                    <div>5</div>
                    <div>|</div>
                    <div>SPLITFIRE</div>
                    <div> <i className="fa-inverse fas fa-at"></i></div>
                    <div>
                        <i className="fa-inverse fas fa-angle-double-down" style={{ color: "tomato" }}></i>
                    </div>
                </div>
                <div className="p1 g-2g">
                    <div>5</div>
                    <div>|</div>
                    <div>SPLITFIRE</div>
                    <div><i className="fa-inverse fas fa-user" ></i></div>
                    <div>
                        <i className="fa-inverse fas fa-angle-double-up" style={{ color: "greenyellow" }}></i>
                    </div>
                </div>
            </div>
        </div>
    }
}