class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = { showMenu: "" };
    }

    showMenu() {
        this.setState({showMenu:"showMenu"});
    }

    render() {
        return [
            <SlideMenu key="SlideMenu" />,
            <div id="container" className={"container " + this.state.showMenu}>
                <div className="menu" onClick={this.showMenu.bind(this)} >
                    <div><i className="fas fa-bars" style={{ color: 'white' }}></i></div>
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
