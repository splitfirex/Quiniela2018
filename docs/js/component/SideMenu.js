class SideMenu extends React.Component {


    render(){

        return <div className={"sideMenu-wrap "+ (this.props.renderSideMenu ? "show" : "")}>
            <div className="sideMenu-content">HOLA</div>
        
        </div>
    }
}

function SideMenuModal(props){
        return (<div onClick={props.fnToggleMenu} className={"sideMenu-modal "+ (props.renderSideMenu ? "show" : "")}/>)
}

SlideMenu.defaultProps = {
    renderSideMenu: false
}