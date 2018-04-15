class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showMenu: "",
            showModal: "",
            username: "",
            showModalName: ""
        };
    }

    showMenu() {
        this.setState({
            showMenu: this.state.showMenu == "" ? "showMenu" : ""
        });
    }

    closeMenu() {
        this.setState({
            showMenu: ""
        });
    }

    showModal(element) {
        var modalname = element.currentTarget.getAttribute("data-modal");
        this.setState({
            showModal: this.state.showModal == "" ? "showModal" : "",
            showModalName: modalname
        });
    }

    closeModal() {
        this.setState({
            showModal: ""
        });
    }

    loginSuccess(user) {
        this.setState({
            username: user,
            showModal: ""
        })
    }

    logoutSuccess(){
        token = "";
        this.setState({
            username: "",
            showModal: ""
        })
    }

    render() {
        return <div>
            <SlideMenu key="SlideMenu" 
            username={this.state.username}
            fnCloseMenu={this.closeMenu.bind(this)} 
            fnShowModal={this.showModal.bind(this)}
            fnSignOut={this.logoutSuccess.bind(this)} />
            <div key="container" id="container" className={"container " + this.state.showMenu}>
                <div className="menu">
                    <div onClick={this.showMenu.bind(this)} >
                        <div style={{ display: this.state.showMenu != "" ? "block" : "none" }}>
                            <i key="times" className="fas fa-times" style={{ color: 'white' }}></i>
                        </div>
                        <div style={{ display: this.state.showMenu == "" ? "block" : "none" }}>
                            <i key="bars" className="fas fa-bars" style={{ color: 'white' }}></i>
                        </div>
                    </div>
                    <div></div>
                </div>
                <div className="header" id="header">
                    <img src="img/woldcup.svg" width="100%" />
                </div>
                <Notification />

                <Content />
                <Groups />
                <Matches />
                {/*<PlayerMatches />*/}
            </div>
            <Modal key="modal"
                className={"modal " + this.state.showModal}
                showModal={this.state.showModalName}
                fnCloseModal={this.closeModal.bind(this)}
                fnLoginSuccess={this.loginSuccess.bind(this)} />
        </div>

    }
}


ReactDOM.render(<App />, document.getElementById("root"));
