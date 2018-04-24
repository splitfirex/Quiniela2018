class Menu extends React.Component {

    render() {
        return <MenuLogged {...this.props} />
    }

}


function MenuLogged(props) {
    return (<div className="menu logged">
        <div onClick={props.fnToggleMenu} >
            <div className="iconCenter">
                <i key="bars" className="fas fa-bars"></i>
            </div>
        </div>
        <div><div className="logo"></div></div>
        <div></div>
        <div onClick={props.fnOnClickBack.bind(null)} style={{"visibility" : props.renderBack ? "visible": "hidden"}}>
            <div className="iconCenter">
                <i key="bars" className="fas fa-arrow-left"></i>
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