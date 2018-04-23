class ContentJoinLadder extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            laddername: props.laddername,
            password: null,
            public: true,
            error: false,
            errorPassword: false
        }
    }

    tryRegisterNewLadder() {
        if (!this.state.public && (this.state.password == null || this.state.password.trim() == "")) {
            this.setState({
                errorPassword: true
            });
        } else if (this.state.laddername == null || this.state.laddername.trim() == "") {
            this.setState({
                error: true
            });
        }
         else {
            postCreateLadder(this.state.laddername, this.state.password, this.processNewLadder.bind(this));
        }


    }

    processNewLadder(response) {
        if (Object.keys(response).length === 0 && response.constructor === Object) {
            this.setState({
                error: true
            });
        } else {
            this.props.fnNewLadderOK(response.name);
        }
    }

    togglePublic() {
        this.setState({
            public: !this.state.public,
            password: null
        });
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value,
            error: false,
            errorPassword: false
        });
    }

    renderLock() {
        if (this.state.public) {
            return <div key="newLadderlock" className="iconCenter green" onClick={this.togglePublic.bind(this)}> <i className="fas fa-unlock" ></i> </div>
        }
        return <div key="newLadderUnlock" className="iconCenter red" onClick={this.togglePublic.bind(this)}> <i className="fas fa-lock" ></i></div>
    }

    renderPassword() {
        if (this.state.public) {
            return <div> </div>
        }
        return <div><input className={this.state.errorPassword ? "error" : ""}  type="pasword" placeholder="Contraseña" id="password" onChange={this.handleChange.bind(this)} /></div>
    }

    render() {
        return <div className="newLadder">

            <div>Unirse a la quiniela</div>
            <div><input className={this.state.error ? "error" : ""} id="laddername" readOnly="readOnly" type="text" placeholder="Nombre quiniela" value={this.state.laddername} onChange={this.handleChange.bind(this)} /></div>
            <div className="public" >
                {this.renderLock()}
            </div>
            <div>
                {this.renderPassword()}
            </div>
            <div><button onClick={this.tryRegisterNewLadder.bind(this)} >Enviar</button></div>
            <div> </div>
        </div>
    }

}