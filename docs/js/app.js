class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showMenu: "",
            showModal: ""
        };
    }

    showMenu() {
        this.setState({
            showMenu: this.state.showMenu == "" ? "showMenu" : ""
        });
    }

    closeMenu(){
        this.setState({
            showMenu: ""
        });
    }

    showModal() {
        this.setState({
            showModal: this.state.showModal == "" ? "showModal" : ""
        });
    }

    closeModal(){
        this.setState({
            showModal: ""
        });
    }

    render() {
        return [
            <SlideMenu key="SlideMenu" fnCloseMenu={this.closeMenu.bind(this)} fnShowModal={this.showModal.bind(this)}/>,
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
                <Quiniela />
                <Content />
                <Groups />
                <Matches />
                <PlayerMatches />
            </div>
        ]

    }
}


ReactDOM.render(<App />, document.getElementById("root"));
