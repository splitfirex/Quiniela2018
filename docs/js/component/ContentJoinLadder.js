class ContentJoinLadder extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showLoading: false,

            password: undefined,
            public: true,
            error: false
        }
    }

    dispatch(action) {
        this.setState(preState => GlobalAppActions(preState, action));
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value,
            error: false
        });
    }

    render() {
        return this.state.showLoading ? <div className="newLadder"><ModalLoading /></div>
            :
            <div className="newLadder">
                <div>Unirse a la quiniela</div>
                <div>{this.props.laddername}</div>
                <div className="public" >
                    {this.props.ladderProtected ? <div key="newLadderunlock" className="iconCenter red" > <i className="fas fa-lock" ></i> </div> : <div key="newLadderlock" className="iconCenter green" > <i className="fas fa-unlock" ></i></div>}
                </div>
                <div>
                    {this.props.ladderProtected && <div><input className={this.state.error ? "error" : ""} type="pasword" placeholder="Contraseña" id="password" onChange={this.handleChange.bind(this)} /></div>}
                </div>
                <div><button onClick={() => fetchJoinLadder.bind(this)()}>Enviar</button></div>
                <div> </div>
            </div>
    }

}