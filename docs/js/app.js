class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showMenu: "",
            showModal: "",
            username: "",
            showModalName: "",
            selectedLadder: null,
            currentLadder:null,
            currentPlayer:null
        };
    }

    showMenu() {
        this.setState({
            showMenu: this.state.showMenu == "" ? "showMenu" : ""
        });
    }

    closeMenu() {
        this.setState({
            showMenu: "",
            currentPlayer: null,
            currentLadder:null
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

    selectLadder(ladder){
        this.setState({
            selectedLadder : ladder,
            showModal: ""
        })
    }

    loadPlayers(ladder){
        this.setState({
            currentLadder : ladder,
        })
    }

    showMatches(playerUsername){
        this.setState({
            currentPlayer : playerUsername
        })
    }

    goBack(){
        if(this.state.currentPlayer != null){
            this.setState({
                currentPlayer : null
            });
            return;
        }
        if(this.state.currentLadder != null){
            this.setState({
                currentLadder : null
            });
            return;
        }
    }

    render() {
        return <div>
            <SlideMenu key="SlideMenu" 
            selectedLadder={this.state.selectedLadder}
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
                    <div onClick={this.goBack.bind(this)} >
                        <div style={{ display: this.state.currentLadder != null  || this.state.currentPlayer != null ? "block" : "none" }}>
                            <i key="bars" className="fas fa-undo-alt" style={{ color: 'white' }}></i>
                        </div>
                    </div>
                </div>
                <div className="header" id="header">
                    <img src="img/woldcup.svg" width="100%" />
                </div>
                <Notification />

                <Content fnShowMatches={this.showMatches.bind(this)} loadPlayers={this.loadPlayers.bind(this)} currentLadder={this.state.currentLadder} currentPlayer={this.state.currentPlayer}/>
                <Groups ladderBoad={this.state.currentLadder} username={this.state.currentPlayer} />
                <Matches ladderBoad={this.state.currentLadder} username={this.state.currentPlayer} />
                {/*<PlayerMatches />*/}
            </div>
            <Modal key="modal"
                fnChangeLadder={this.selectLadder.bind(this)}
                className={"modal " + this.state.showModal}
                showModal={this.state.showModalName}
                fnCloseModal={this.closeModal.bind(this)}
                fnLoginSuccess={this.loginSuccess.bind(this)} />
        </div>

    }
}

function Loading(){
    return(
        <div className="loading">
         <div><i className="fas fa-cog fa-spin"></i> Loading...</div>
        </div>
    )
}


ReactDOM.render(<App />, document.getElementById("root"));
