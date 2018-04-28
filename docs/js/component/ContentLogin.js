class ContentLogin extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showLoading: false,

            username: undefined,
            password: undefined,
            error: false
        }
    }

    dispatch(action) {
        this.setState(preState => LoginAppActions(preState, action));
    }

    componentWillUnmount() {
        this.setState({ showLoading: false });
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value,
            error: false
        });
    }

    render() {
        return this.state.showLoading ? <div className="login"><ModalLoading /></div>
            :
            <div className="login">

                <div>Iniciar Sesion</div>
                <div><input className={this.state.error ? "error" : ""} id="username" type="text" placeholder="Usuario" value={this.state.username} onChange={this.handleChange} /></div>
                <div><input className={this.state.error ? "error" : ""} id="password" type="password" placeholder="Contraseña" value={this.state.password} onChange={this.handleChange} /></div>
                <div><button onClick={() => fetchLogin.bind(this)()}>Enviar</button></div>
                <div></div>
                <div>No tienes una cuenta? <a onClick={() => this.props.dispatch({ type: "GO_TO", dest: "SIGN_UP" })} href="javascript:void(0);"> registrate! </a></div>
            </div>
    }
}