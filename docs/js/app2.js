class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showSideMenu: false,
            showBackButton: false,
            showLoading: false,
            showBreadcrumbs: true,
            breads: ["Inicio"],
            currentWindow: "Inicio"
        }
    }

    toggleMenu() {
        this.setState({
            showSideMenu: !this.state.showSideMenu
        })
    }

    toggleLoading() {
        this.setState({
            showLoading: !this.state.showLoading
        })
    }

    fnOnClickGoTo(destination) {
        console.log(destination);
        this.changeWindow(destination);
        this.toggleMenu();
    }

    changeWindow(newWindow) {
        if (newWindow == "Inicio") {
            this.setState({ breads: ["Inicio"] });
    
        } else if (newWindow == "Grupos") {
            this.setState({ breads: ["Inicio", "Grupos"] });

        } else if (newWindow == "Partidos") {
            this.setState({ breads: ["Inicio", "Partidos"] });
        }
    }

    render() {

        if (this.state.showBreadcrumbs) {
            var breadcrumbs = <BreadCrumbs listRender={this.state.breads} />;
        }

        return <div>
            <Menu fnToggleMenu={this.toggleMenu.bind(this)} renderBack={this.state.showBackButton} />
            {breadcrumbs}
            <SideMenu fnOnClickGoTo={this.fnOnClickGoTo.bind(this)} renderSideMenu={this.state.showSideMenu} />
            <GlobalContent renderBreadcrumbs={this.state.showBreadcrumbs} />
        </div>

    }
}

App.defaultProps = {
    username: null,
    laddername: null,
    playername: null,
    breads: []
}

function Loading(props) {
    return (
        <div className="loading">
            <i className="fas fa-spinner fa-pulse"></i>
        </div>
    )
}


ReactDOM.render(<App />, document.getElementById("root"));
