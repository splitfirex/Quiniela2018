class ContentLogin extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            username: undefined,
            password: undefined,
            error: false
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
        this.setState({
            [event.target.id]: event.target.value,
            error: false
        });
    }

    componentWillReceiveProps(nextProps) {
        this.setState({
            username: undefined,
            password: undefined
        })
    }

    render() {
        return <div className="login">

            <div>Iniciar Sesion</div>
            <div><input className={this.state.error ? "error" : ""} id="username" type="text" placeholder="Usuario" value={this.state.username} onChange={this.handleChange} /></div>
            <div><input className={this.state.error ? "error" : ""} id="password" type="password" placeholder="Contraseña" value={this.state.password} onChange={this.handleChange} /></div>
            <div><button onClick={this.tryLogin.bind(this)} >Enviar</button></div>
            <div> <form action="/local" method="POST"><div className="g-recaptcha" data-sitekey="6Lctz1QUAAAAAEmuwcYrf67Nr3OOj1Lx_l2jfllP"></div> </form></div>
            <div>No tienes una cuenta? <a onClick={this.props.changeToRegister.bind(this)} href="javascript:void(0);"> registrate! </a></div>

        </div>
    }
}