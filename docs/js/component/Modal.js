class Modal extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return <div id="modal" className={this.props.className} >
            <div className="header">
                <div className="menu">
                    <div onClick={this.props.fnCloseModal} >
                        <div>
                            <i className="fas fa-times" style={{ color: 'white' }}></i>
                        </div>
                    </div>
                    <div></div>
                </div>
            </div>
            <div className="content">
            {/*<PlayerManager/>*/}
            <LadderBoardManager/>
            </div>
        </div>
    }
}