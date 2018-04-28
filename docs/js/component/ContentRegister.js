class ContentRegister extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            username: undefined,
            password: undefined,
            passwordRepeat: undefined,
            error: false,
            errorPassword: false
        }
    }

    dispatch(action) {
        this.setState(preState => LoginAppActions(preState, action));
    }

    handleChange = event => {
        var newErrorPassword = false;
        if (event.target.id == "passwordRepeat" && this.state.password.indexOf(event.target.value) != 0) {
            newErrorPassword = true;
        }

        this.setState({
            [event.target.id]: event.target.value,
            error: false,
            errorPassword: newErrorPassword
        });
    }

    render() {
        return <div className="register">

            <div>Registrarse</div>
            <div><input className={this.state.error ? "error" : ""} id="username" type="text" placeholder="Usuario" value={this.state.username} onChange={this.handleChange.bind(this)} /></div>
            <div><input className={this.state.errorPassword ? "error" : ""} id="password" type="password" placeholder="Contraseña" value={this.state.password} onChange={this.handleChange.bind(this)} /></div>
            <div><input className={this.state.errorPassword ? "error" : ""} id="passwordRepeat" type="password" placeholder="Repite contraseña" value={this.state.passwordRepeat} onChange={this.handleChange.bind(this)} /></div>
            <div><button onClick={() => fetchRegister.bind(this)()} >Enviar</button></div>
            <div> </div>
            <div>Ya tienes una cuenta? <a href="javascript:void(0);" onClick={() => this.props.dispatch({ type: "GO_TO", dest: "SIGN_IN" })}>Inicia sesion!</a></div>

        </div>
    }
}