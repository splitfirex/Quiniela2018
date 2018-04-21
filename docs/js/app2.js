class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showSideMenu: false,
            showBackButton: false,
            showLoading: false,
            showBreadcrumbs: false,
            breads: [],
            currentWindow: "Inicio",
            username: null,
            playername: null,
            laddername: null
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

    fnOnClickBack() {
        if(this.state.breads.length == 2){
            this.setState({
                currentWindow: "Inicio",
                showBreadcrumbs: false,
                breads: []
            })
        }else if(this.state.breads.length == 3){
            this.setState({
                currentWindow: "Jugadores",
                breads: ["Inicio", this.state.laddername]
            })
        }
    }

    fnOnClickGoTo(destination) {
        console.log(destination);
        this.changeWindow(destination);
        this.toggleMenu();
    }

    fnOnClickLadder(newLaddername) {
        console.log(newLaddername);
        this.setState({
            laddername: newLaddername,
            currentWindow: "Jugadores",
            showBreadcrumbs: true,
            breads: ["Inicio", newLaddername]
        })
    }

    fnOnMatchClick(newPlayername) {
        this.setState({
            playername: newPlayername,
            currentWindow: "Partidos",
            breads: ["Inicio", this.state.laddername, newPlayername]
        })
    }

    fnOnGroupClick(newPlayername) {
        this.setState({
            playername: newPlayername,
            currentWindow: "Grupos",
            breads: ["Inicio", this.state.laddername, newPlayername]
        })
    }


    changeWindow(newWindow) {
        if (newWindow == "Inicio") {
            this.setState({
                breads: ["Inicio"],
                currentWindow: newWindow,
                laddername: null,
                playername: null
            });

        } else if (newWindow == "Grupos") {
            this.setState({
                breads: ["Inicio", "Grupos"],
                currentWindow: newWindow,
                laddername: null,
                playername: null
            });

        } else if (newWindow == "Partidos") {
            this.setState({
                breads: ["Inicio", "Partidos"],
                currentWindow: newWindow,
                laddername: null,
                playername: null
            });
        }
    }

    render() {

        if (this.state.showLoading) {
            var loading = <Loading />;
        }

        if (this.state.showBreadcrumbs) {
            var breadcrumbs = <BreadCrumbs listRender={this.state.breads} />;
        }

        return <div>
            {loading}
            <Menu
                fnToggleMenu={this.toggleMenu.bind(this)}
                renderBack={this.state.breads.length > 1}
                fnOnClickBack={this.fnOnClickBack.bind(this)} />
            {breadcrumbs}
            <SideMenu fnOnClickGoTo={this.fnOnClickGoTo.bind(this)} renderSideMenu={this.state.showSideMenu} />
            <GlobalContent
                laddername={this.state.laddername}
                playername={this.state.playername}
                username={this.state.username}
                fnOnClickLadder={this.fnOnClickLadder.bind(this)}
                fnOnMatchClick={this.fnOnMatchClick.bind(this)}
                fnOnGroupClick={this.fnOnGroupClick.bind(this)}
                currentWindow={this.state.currentWindow}
                renderBreadcrumbs={this.state.showBreadcrumbs} />
            <div className="footer"></div>
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
