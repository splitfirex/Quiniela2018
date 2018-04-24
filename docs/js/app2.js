class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showSideMenu: false,
            showLoading: false,
            showBreadcrumbs: false,
            showBack: false,
            showModal: false,
            protected: false,
            breads: [],
            currentWindow: "Inicio",
            currentModalWindow: "Nueva quiniela",
            username: null,
            playername: null,
            laddername: null
        }
    }

    goTop() {
        scrollIt(
            document.querySelector('.content-warp'),
            300,
            'easeOutQuad',
            () => console.log(`Just finished scrolling to px`)
        );
    }

    toggleMenu() {
        this.setState({
            showSideMenu: !this.state.showSideMenu
        })
    }

    fnOnClickBack() {
        this.toggleLoadingModal();
        if (this.state.breads.length == 2) {
            this.setPostState({
                currentWindow: "Inicio",
                showBreadcrumbs: false,
                breads: []
            });
        } else if (this.state.breads.length == 3) {
            this.setPostState({
                currentWindow: "Jugadores",
                breads: ["Inicio", this.state.laddername]
            });
        }
        this.goTop();
    }

    fnOnModalBack() {
        this.toggleLoadingModal();
        this.setPostState({
            showModal: false,
            currentWindow: "Inicio",
            showBreadcrumbs: false,
            currentModalWindow: null
        })
    }

    fnLoginOK(newUsername) {
        this.toggleLoadingModal();
        this.setPostState({
            username: newUsername,
            showModal: false,
            currentWindow: "Inicio",
            showBreadcrumbs: false,
            currentModalWindow: null
        })
    }

    fnRegisterOK(newUsername) {
        this.toggleLoadingModal();
        this.setPostState({
            username: newUsername,
            showModal: false,
            currentWindow: "Inicio",
            showBreadcrumbs: false,
            currentModalWindow: null
        })
    }

    fnNewLadderOK(newLaddername) {
        this.toggleLoadingModal();
        this.setPostState({
            laddername: newLaddername,
            showBreadcrumbs: true,
            showModal: false,
            currentWindow: "Jugadores",
            breads:  ["Inicio", newLaddername],
            currentModalWindow: null
        })
    }

    fnJoinLadderOK(newLaddername){
        this.toggleLoadingModal();
        this.setPostState({
            showBreadcrumbs: false,
            showModal: false,
            currentWindow: "Inicio",
            breads:  [],
            currentModalWindow: null
        })
    }

    fnOnClickGoTo(destination,laddername) {
        console.log(destination);
        console.log(laddername);
        this.changeWindow(destination);
        this.goTop();
    }

    fnOnClickJoinLadder(laddername,isProtected){
        this.setPostState({
            showModal: true,
            currentModalWindow: "Unirse a quiniela",
            laddername: laddername,
            protected: isProtected
        });
    }

    fnOnClickLadder(newLaddername) {
        this.toggleLoadingModal();
        this.setPostState({
            laddername: newLaddername,
            currentWindow: "Jugadores",
            showBreadcrumbs: true,
            breads: ["Inicio", newLaddername]
        });
        this.goTop();
    }

    fnOnMatchClick(newPlayername) {
        this.toggleLoadingModal();
        this.setPostState({
            playername: newPlayername,
            currentWindow: "Partidos",
            breads: ["Inicio", this.state.laddername, newPlayername]
        });
        this.goTop();
    }

    fnOnGroupClick(newPlayername) {
        this.toggleLoadingModal();
        this.setPostState({
            playername: newPlayername,
            currentWindow: "Grupos",
            breads: ["Inicio", this.state.laddername, newPlayername]
        });
        this.goTop();
    }

    toggleLoadingModal() {
        this.setState({
            currentModalWindow: null,
            showModal: true,
            showSideMenu: false
        })

    }

    setPostState(newState) {
        this.timer = setTimeout(
            function () {
                this.setState({
                    showModal: false,
                    currentModalWindow: null,
                    ...newState,
                })
            }.bind(this),
            250);
    }


    changeWindow(newWindow) {
        this.toggleLoadingModal();
        if (newWindow == "Inicio") {
            this.setPostState({
                breads: [],
                currentWindow: newWindow,
                showBreadcrumbs: false,
                laddername: null,
                playername: null
            });
        } else if (newWindow == "Grupos") {
            this.setPostState({
                breads: ["Inicio", "Grupos"],
                currentWindow: newWindow,
                showBreadcrumbs: true,
                laddername: null,
                playername: null,
            });
        } else if (newWindow == "Partidos") {
            this.setPostState({
                breads: ["Inicio", "Partidos"],
                currentWindow: newWindow,
                showBreadcrumbs: true,
                laddername: null,
                playername: null
            });
        } else if (newWindow == "Iniciar Sesion") {
            this.setPostState({
                showModal: true,
                currentModalWindow: newWindow
            });
        } else if (newWindow == "Cerrar Sesion") {
            this.setPostState({
                breads: [],
                currentWindow: "Inicio",
                showBreadcrumbs: false,
                currentModalWindow: null,
                username: null,
                laddername: null,
                playername: null
            });
        }else if (newWindow == "Nueva quiniela") {
            this.setPostState({
                showModal: true,
                currentModalWindow: newWindow
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
                renderBack={this.state.showBreadcrumbs}
                fnOnClickBack={this.fnOnClickBack.bind(this)} />
            {breadcrumbs}
            <SideMenu
                username={this.state.username}
                fnOnClickGoTo={this.fnOnClickGoTo.bind(this)}
                renderSideMenu={this.state.showSideMenu} />
            <GlobalContent
                laddername={this.state.laddername}
                playername={this.state.playername}
                username={this.state.username}
                fnOnClickJoinLadder={this.fnOnClickJoinLadder.bind(this)}
                fnOnClickGoTo={this.fnOnClickGoTo.bind(this)}
                fnOnClickLadder={this.fnOnClickLadder.bind(this)}
                fnOnMatchClick={this.fnOnMatchClick.bind(this)}
                fnOnGroupClick={this.fnOnGroupClick.bind(this)}
                currentWindow={this.state.currentWindow}
                renderBreadcrumbs={this.state.showBreadcrumbs} />
            <ModalContent
                ladderProtected={this.state.protected}
                laddername={this.state.laddername}
                fnNewLadderOK={this.fnNewLadderOK.bind(this)}
                fnRegisterOK={this.fnRegisterOK.bind(this)}
                fnLoginOK={this.fnLoginOK.bind(this)} 
                fnJoinLadderOK={this.fnJoinLadderOK.bind(this)}
                fnOnModalBack={this.fnOnModalBack.bind(this)}
                currentModalWindow={this.state.currentModalWindow}
                renderModal={this.state.showModal} />
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
