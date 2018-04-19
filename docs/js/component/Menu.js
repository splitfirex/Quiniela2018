class Menu extends React.Component {

    render() {
        return <MenuLogged {...this.props} />
    }

}


function MenuLogged(props) {
    return (<div className="menu logged">
        <div onClick={props.fnToggleMenu} >
            <div className="iconCenter">
                <i key="bars" className="fas fa-bars" style={{ color: 'grey' }}></i>
            </div>
        </div>
        <div><div className="logo"></div></div>
        <div style={{ textAlign: "right" }}> Daniel <i key="bars" className="fas fa-sign-out-alt" style={{ color: 'grey' }}></i></div>
        <div>
            <div className="iconCenter">
            </div>
        </div>
    </div>)
}

function MenuNoUser(props) {
    return (<div className="menu">
        <div onClick={props.fnToggleMenu} >
            <div className="iconCenter">
                <i key="bars" className="fas fa-bars" style={{ color: 'grey' }}></i>
            </div>
        </div>
        <div><div className="logo"></div></div>
        <div style={{ textAlign: "right" }}></div>
        <div>
            <div className="iconCenter">
                <i key="bars" className="fas fa-sign-in-alt" style={{ color: 'grey' }}></i>
            </div>
        </div>
    </div>)
}


Menu.defaultProps = {
    renderBack: false
}