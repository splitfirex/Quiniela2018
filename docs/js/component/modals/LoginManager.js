class LoginManager extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showSignin: true,
        };

        this.loginData = {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            mode: 'cors',
            cache: 'default'
        };
        this.loginRequest = new Request(server+'/login/signin');
        this.registerRequest = new Request(server+'/login/signup');
    }

    showScreen() {
        if (this.state.showSignin) {
            return <Login sendData={this.sendLogin.bind(this)}
                changeScreen={this.changeScreen.bind(this)} />
        }
        return <Register sendData={this.sendLogin.bind(this)}
            changeScreen={this.changeScreen.bind(this)} />
    }

    changeScreen() {
        this.setState({
            showSignin: !this.state.showSignin
        });
    }

    sendLogin() {
        this.loginData.body = JSON.stringify( {"username":"Daniel", "password":"Daniel"} ) ;
        fetch(this.loginRequest, this.loginData)
        .then(function(res){ return res.json(); })
        .then(function(data){ alert( JSON.stringify( data ) ) }.bind(this));
    }

    render() {
        return this.showScreen();
    }

}

function Login(props) {
    return (
        <div className="signin">
            <div>Sign in</div>
            <div><input type="text" placeholder="Username" /></div>
            <div><input type="password" placeholder="Password" /></div>
            <div><button onClick={props.sendData}>Sign in </button></div>
            <div>Don't have an account? <a onClick={props.changeScreen}>Click here to register!</a></div>
        </div>
    );
}

function Register(props) {
    return (
        <div className="register">
            <div>Register</div>
            <div><input type="text" placeholder="Username" /></div>
            <div><input type="password" placeholder="Password" /></div>
            <div><input type="password" placeholder="Repeat password" /></div>
            <div><button onClick={props.sendData}>Register</button></div>
            <div>Have an account? <a onClick={props.changeScreen}>Click here to sign in!</a></div>
        </div>
    );
}