class ContentRegister extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            username: null,
            password: null,
            passwordRepeat: null,
            error: false,
            errorPassword: false
        }
    }

    tryLogin() {
        getPlayerLogin(this.state.username, this.state.password, this.processLogin.bind(this));
    }

    processLogin(response) {
        if (Object.keys(response).length === 0 && response.constructor === Object) {
            this.setState({
                error: true
            })
        } else {
            this.props.fnLoginOK(this.state.username);
        }
    }

    handleChange = event => {
        var newErrorPassword = false;
        if(event.target.id == "passwordRepeat" && this.state.password.indexOf(event.target.value) != 0){
            newErrorPassword = true;
        }

        this.setState({
            [event.target.id]: event.target.value,
            error: false,
            errorPassword: newErrorPassword
        });
    }

    componentWillReceiveProps(nextProps) {
        this.setState({
            username: null,
            password: null,
            errorPassword: null
        })
    }

    render() {
        return <div className="login">

            <div>Iniciar Sesion</div>
            <div><input className={this.state.error ? "error" : ""} id="username" type="text" placeholder="Usuario" value={this.state.username} onChange={this.handleChange.bind(this)} /></div>
            <div><input className={this.state.errorPassword ? "error" : ""} id="password" type="password" placeholder="Contraseña" value={this.state.password} onChange={this.handleChange.bind(this)} /></div>
            <div><input className={this.state.errorPassword ? "error" : ""} id="passwordRepeat" type="password" placeholder="Repite contraseña" value={this.state.passwordRepeat} onChange={this.handleChange.bind(this)} /></div>
            <div><button onClick={this.tryLogin.bind(this)} >Enviar</button></div>
            <div> </div>
            <div>Ya tienes una cuenta? Inicia sesion!</div>

        </div>
    }
}