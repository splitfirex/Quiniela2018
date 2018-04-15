class LoginManager extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            errorMessage: "",
            credentials: { "username": "", "password": "", "passwordr": "" }
        };

        this.baseState = this.state;
        
        this.state.signInState = true;
    }

    handleUsernameChange(e) {
        this.state.credentials["username"] = e.target.value;
        this.setState({
            credentials: this.state.credentials,
            errorMessage: ""
        });
    }

    handlePasswordChange(e) {
        this.state.credentials["password"] = e.target.value;
        this.setState({
            credentials: this.state.credentials,
            errorMessage: ""
        });
    }

    handlePasswordRChange(e) {
        this.state.credentials["passwordr"] = e.target.value;
        this.setState({
            credentials: this.state.credentials,
            errorMessage: ""
        });
    }

    showScreen() {
        if (this.state.signInState) {
            return <Login credentials={this.state.credentials}
                onChanges={{
                    "username": this.handleUsernameChange.bind(this),
                    "password": this.handlePasswordChange.bind(this)
                }}
                errorMessage={this.state.errorMessage}
                sendData={this.sendRequest.bind(this)}
                changeScreen={this.changeScreen.bind(this)} />
        }
        return <Register credentials={this.state.credentials}
            onChanges={{
                "username": this.handleUsernameChange.bind(this),
                "password": this.handlePasswordChange.bind(this),
                "passwordr": this.handlePasswordRChange.bind(this)
            }}
            errorMessage={this.state.errorMessage}
            sendData={this.sendRequest.bind(this)}
            changeScreen={this.changeScreen.bind(this)} />
    }

    changeScreen() {
        this.setState({
            signInState: !this.state.signInState
        });
    }

    getResponseError(response) {
        this.setState({ 
            credentials: { "username": "", "password": "", "passwordr": "" },
            errorMessage: "Error in credentials" });
    }

    getResponse(response) {
        if (response.token != null) token = response.token;
        this.props.fnLoginSuccess(this.state.credentials.username);
        this.state.credentials = { "username": "", "password": "", "passwordr": "" };
    }

    sendRequest() {
        if (this.state.signInState) {
            tryFetch(loginRequest, this.state.credentials, this, true);
        } else {
            if (this.state.credentials.password == this.state.credentials.passwordr) {
                tryFetch(registerRequest, this.state.credentials, this, true);
            } else {
                this.setState({ errorMessage: "Password d" });
            }
        }
        this.setState(this.baseState);
    }

    render() {
        return this.showScreen();
    }

}


function Login(props) {
    return (
        <div className="signin">
            <div>Sign in</div>
            <div><input onChange={props.onChanges.username} value={props.credentials.username} type="text" placeholder="Username" /></div>
            <div><input onChange={props.onChanges.password} value={props.credentials.password} type="password" placeholder="Password" /></div>
            <div><button onClick={props.sendData}>Sign in </button></div>
            <div>{props.errorMessage}</div>
            <div>Don't have an account? <a onClick={props.changeScreen}>Click here to register!</a></div>
        </div>
    );
}

function Register(props) {
    return (
        <div className="register">
            <div>Register</div>
            <div><input onChange={props.onChanges.username} value={props.credentials.username} type="text" placeholder="Username" /></div>
            <div><input onChange={props.onChanges.password} value={props.credentials.password} type="password" placeholder="Password" /></div>
            <div><input onChange={props.onChanges.passwordr} value={props.credentials.passwordr} type="password" placeholder="Repeat password" /></div>
            <div><button onClick={props.sendData}>Register</button></div>
            <div>{props.errorMessage}</div>
            <div>Have an account? <a onClick={props.changeScreen}>Click here to sign in!</a></div>
        </div>
    );
}