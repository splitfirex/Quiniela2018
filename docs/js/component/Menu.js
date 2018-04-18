class Menu extends React.Component {

    render() {
        return <div className="menu">
            <div>
                <div onClick={this.props.fnToggleMenu}>
                    <i key="bars" className="fas fa-bars" style={{ color: 'grey' }}></i>
                </div>
            </div>
            <div>MyQ-2018</div>
            <div>
                <div style={{"display": this.props.renderBack ? "block" : "none" }}>
                    <i key="bars" className="fas fa-undo-alt" style={{ color: 'grey' }}></i>
                </div>
            </div>
        </div>
    }

}

Menu.defaultProps={
    renderBack : false
}