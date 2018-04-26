class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            model: {
                username: null,
                playername: null,
                laddername: null,
                matches: [],
                ladders: [],
                groups: []
            },
            currentState: "Init",
            showLoading: false,
            showWelcome: true,
        }
    }

    componentDidMount() {
        fetchMatches.bind(this)();
    }

    dispatch(action) {
        this.setState(preState => GlobalAppActions(preState, action));
    }

    render() {
        return <div>
            {this.state.showWelcome && <Welcome />}
            <App3 dispatch={(callback) => this.dispatch(callback)} />
            <div className="footer"></div>
        </div>
    }

}

class App3 extends React.Component {

    constructor(props) {
        super(props);

    }

    render() {
        return <div>
            <div onClick={() => this.props.dispatch({ type: "LOAD_LADDERS" })}>HOLA</div>
        </div>
    }

}



function Loading(props) {
    return (
        <div className="loading">
            <i className="fas fa-spinner fa-pulse"></i>
        </div>
    )
}

function Modal(props) {
    return (
        <div className="loading">
            <i className="fas fa-spinner fa-pulse"></i>
        </div>
    )
}

function Welcome() {
    return (
        <div className="modalWelcome">
            <i className="fas fa-spinner fa-pulse"></i>
        </div>
    )
}

function ModalProcesing(props) {
    return (
        <div className="modalLoading">
            <i className="fas fa-spinner fa-pulse"></i>
        </div>
    )
}




ReactDOM.render(<App />, document.getElementById("root"));