class SlideMenu extends React.Component {

    constructor(props) {
        super(props);
    }

    moveTo(destination) {
        this.props.fnCloseMenu(destination);
    }

    render() {
        return <div id="slideMenu-wrap" className="slideMenu-wrap">
            <div id="slideMenu" className="slideMenu">
                <div className="slideMenuHeader">
                    <div>
                        Splitfire
                </div>
                    <div>
                        Select ladderboard
                </div>
                </div>
                <div className="slideMenuContent">
                    <div onClick={this.moveTo.bind(this, "#container")}>
                        <div><i className="fas fa-home"></i></div>
                        <div>Home</div>

                    </div>
                    <div onClick={this.moveTo.bind(this, "#groups-wrap")}>
                        <div><i className="fas fa-list"></i></div>
                        <div>Groups</div>
                    </div>
                    <div onClick={this.moveTo.bind(this, "#matches-wrap")}>
                        <div><i className="fas fa-futbol"></i></div>
                        <div>Matches</div>
                    </div>
                    <div className="slideMenuSeparator">
                    </div>
                    <div onClick={this.props.fnShowModal}>
                        <div><i className="fas fa-plus"></i></div>
                        <div>New Ladderboard</div>
                    </div>
                    <div onClick={this.props.fnShowModal}>
                        <div><i className="fas fa-sort-amount-up"></i></div>
                        <div>Join Ladderboard</div>
                    </div>
                    <div onClick={this.props.fnShowModal}>
                        <div><i className="fas fa-futbol"></i></div>
                        <div>My Matches</div>
                    </div>
                    <div className="slideMenuSeparator">
                    </div>
                    <div onClick={this.props.fnShowModal}>
                        <div><i className="fas fa-users"></i></div>
                        <div>Manage players</div>
                        <div>2</div>
                    </div>
                    <div> </div>
                    <div> </div>
                    <div> </div>
                    <div> </div>
                    <div> </div>
                    <div> </div>
                    <div> </div>
                    <div> </div>
                    <div onClick={this.props.fnShowModal}>
                        <div><i className="fas fa-sign-out-alt"></i></div>
                        <div>Sign out</div>
                    </div>
                </div>
            </div>
        </div>
    }
}