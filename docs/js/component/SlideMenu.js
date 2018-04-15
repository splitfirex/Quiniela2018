class SlideMenu extends React.Component {

    constructor(props) {
        super(props);
    }

    moveTo(element) {
        document.location = element.currentTarget.getAttribute("data-destination");
        this.props.fnCloseMenu();
    }

    showScreen() {
        if (this.props.username == "") {
            return <NotLoggedUser fnShowModal={this.props.fnShowModal} moveTo={this.moveTo.bind(this)} />
        } else {
            return <LoggedUser fnSignOut={this.props.fnSignOut} fnShowModal={this.props.fnShowModal} moveTo={this.moveTo.bind(this)} />
        }
    }

    showSignIn() {
        if (this.props.username == "") {
            return <div data-modal="LoginManager" className="signin" onClick={(e) => this.props.fnShowModal(e)}>
                <svg className="svg-inline--fa fa-sign-out-alt fa-w-16" aria-hidden="true" data-prefix="fas" dataIcon="sign-out-alt" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" data-fa-i2svg=""><path fill="currentColor" d="M497 273L329 441c-15 15-41 4.5-41-17v-96H152c-13.3 0-24-10.7-24-24v-96c0-13.3 10.7-24 24-24h136V88c0-21.4 25.9-32 41-17l168 168c9.3 9.4 9.3 24.6 0 34zM192 436v-40c0-6.6-5.4-12-12-12H96c-17.7 0-32-14.3-32-32V160c0-17.7 14.3-32 32-32h84c6.6 0 12-5.4 12-12V76c0-6.6-5.4-12-12-12H96c-53 0-96 43-96 96v192c0 53 43 96 96 96h84c6.6 0 12-5.4 12-12z"></path></svg>
                <span>Sign in</span>
            </div>
        }
        return <div>{this.props.username}</div>
    }

    showSelectLadderBoard() {
        if (this.props.username == "") {
            return <div></div>
        }
        return <div data-modal="SelectLadderBoardManager" onClick={(e) => this.props.fnShowModal(e)}>
            Select ladderboard
        </div>
    }

    render() {
        return <div id="slideMenu-wrap" className="slideMenu-wrap">
            <div id="slideMenu" className="slideMenu">
                <div className="slideMenuHeader">
                    {this.showSignIn()}
                    {this.showSelectLadderBoard()}
                </div>
                {this.showScreen()}
            </div>
        </div>
    }
}

function LoggedUser(props) {
    return (
        <div className="slideMenuContent">
            <div data-destination="#container" onClick={(e) => props.moveTo(e)}>
                <div><i className="fas fa-home"></i></div>
                <div>Home</div>

            </div>
            <div data-destination="#groups-wrap" onClick={(e) => props.moveTo(e)}>
                <div><i className="fas fa-list"></i></div>
                <div>Groups</div>
            </div>
            <div data-destination="#matches-wrap" onClick={(e) => props.moveTo(e)}>
                <div><i className="fas fa-futbol"></i></div>
                <div>Matches</div>
            </div>
            <div className="slideMenuSeparator">
            </div>
            <div data-modal="LoginManager" onClick={(e) => props.fnShowModal(e)} >
                <div><i className="fas fa-plus"></i></div>
                <div>New Ladderboard</div>
            </div>
            <div data-modal="LoginManager" onClick={(e) => props.fnShowModal(e)}>
                <div><i className="fas fa-sort-amount-up"></i></div>
                <div>Join Ladderboard</div>
            </div>
            <div data-modal="LoginManager" onClick={(e) => props.fnShowModal(e)}>
                <div><i className="fas fa-futbol"></i></div>
                <div>My Matches</div>
            </div>
            <div className="slideMenuSeparator">
            </div>
            <div> </div>
            <div onClick={(e) => props.fnSignOut(e)}>
                <div><i className="fas fa-sign-out-alt"></i></div>
                <div>Sign out</div>
            </div>
        </div>
    );

}

function NotLoggedUser(props) {
    return (
        <div className="slideMenuContent">
            <div data-destination="#container" onClick={(e) => props.moveTo(e)}>
                <div><i className="fas fa-home"></i></div>
                <div>Home</div>

            </div>
            <div data-destination="#groups-wrap" onClick={(e) => props.moveTo(e)}>
                <div><i className="fas fa-list"></i></div>
                <div>Groups</div>
            </div>
            <div data-destination="#matches-wrap" onClick={(e) => props.moveTo(e)}>
                <div><i className="fas fa-futbol"></i></div>
                <div>Matches</div>
            </div>
        </div>
    )
}

function AdminUser(props) {
    return (
        <div className="slideMenuContent">
            <div data-destination="#container" onClick={(e) => props.moveTo(e)}>
                <div><i className="fas fa-home"></i></div>
                <div>Home</div>

            </div>
            <div data-destination="#groups-wrap" onClick={(e) => props.moveTo(e)}>
                <div><i className="fas fa-list"></i></div>
                <div>Groups</div>
            </div>
            <div data-destination="#matches-wrap" onClick={(e) => props.moveTo(e)}>
                <div><i className="fas fa-futbol"></i></div>
                <div>Matches</div>
            </div>
            <div className="slideMenuSeparator">
            </div>
            <div data-modal="LoginManager" onClick={(e) => props.fnShowModal(e)}>
                <div><i className="fas fa-plus"></i></div>
                <div>New Ladderboard</div>
            </div>
            <div data-modal="LoginManager" onClick={(e) => props.fnShowModal(e)}>
                <div><i className="fas fa-sort-amount-up"></i></div>
                <div>Join Ladderboard</div>
            </div>
            <div onClick={props.fnShowModal}>
                <div><i className="fas fa-futbol"></i></div>
                <div>My Matches</div>
            </div>
            <div className="slideMenuSeparator">
            </div>
            <div onClick={props.fnShowModal}>
                <div><i className="fas fa-users"></i></div>
                <div>Manage players</div>
                <div>2</div>
            </div>
            <div> </div>
            <div> </div>
            <div onClick={props.fnShowModal}>
                <div><i className="fas fa-sign-out-alt"></i></div>
                <div>Sign out</div>
            </div>
        </div>
    );
}