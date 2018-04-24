class ContentJoinLadder extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            laddername: props.laddername,
            password: undefined,
            public: true,
            errorPassword: false
        }
    }

    tryJoinLadder() {
        if (this.props.ladderProtected && this.state.password != undefined && this.state.password.trim() != "") {
            postJoinLadder(this.state.laddername,this.state.password,this.processJoinLadder.bind(this));
        }else if(!this.props.ladderProtected){
            postJoinLadder(this.state.laddername,null,this.processJoinLadder.bind(this));
        }
        this.setState({
            errorPassword: true
        });
    }

    processJoinLadder(response) {
        if (Object.keys(response).length === 0 && response.constructor === Object) {
            this.setState({
                errorPassword: true
            });
        } else {
            this.props.fnJoinLadderOK(response.name);
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
            errorPassword: false
        });
    }

    renderLock() {
        if (this.props.ladderProtected) {
            return <div key="newLadderunlock" className="iconCenter red" > <i className="fas fa-lock" ></i> </div>
        }
        return <div key="newLadderlock" className="iconCenter gree" > <i className="fas fa-unlock" ></i></div>
    }

    renderPassword() {
        if (!this.props.ladderProtected) {
            return <div> </div>
        }
        return <div><input className={this.state.errorPassword ? "error" : ""} type="pasword" placeholder="Contraseña" id="password" onChange={this.handleChange.bind(this)} /></div>
    }

    render() {
        return <div className="newLadder">

            <div>Unirse a la quiniela</div>
            <div>{this.state.laddername}</div>
            <div className="public" >
                {this.renderLock()}
            </div>
            <div>
                {this.renderPassword()}
            </div>
            <div><button onClick={this.tryJoinLadder.bind(this)} >Enviar</button></div>
            <div> </div>
        </div>
    }

}