class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showModal: false,
            showWelcome: true,
            showMenu: false,
            showBreadcrumbs: false,

            contentWindow: "LADDERS",
            matches: [],
            groups: [],
            teams: [],
            token: null,
            subTitle: "Quinielas",
            dispatch: (action) => this.dispatch(action)
        }
    }

    componentDidMount() {
        prefetchMatches.bind(this)();
        prefetchGroups.bind(this)();
        prefetchTeams.bind(this)();
    }

    dispatch(action) {
        this.setState(preState => GlobalAppActions(preState, action));
    }

    render() {
        return <div>
            {this.state.showWelcome && <Welcome />}
            {this.state.showBreadcrumbs && <BreadCrumbs breadcrumbs={this.state.breadcrumbs} />}
            <Menu  {...this.state} />
            <SideMenu {...this.state} />
            <ModalContent  {...this.state} />
            <GlobalContent {...this.state} />


            <div className="footer"></div>
        </div>
    }

}

function Welcome() {
    return (<div className="modalWelcome">

    </div>)
}

function Loading(props) {
    return (
        <div className="loading">
            <i className="fas fa-spinner fa-pulse"></i>
        </div>
    )
}


ReactDOM.render(<App />, document.getElementById("root"));