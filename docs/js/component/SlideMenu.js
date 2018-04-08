class SlideMenu extends React.Component {

    render() {
        return <div id="slideMenu" className="menu">
            <div className="menuHeader">
                <div id="menuBtnOpen" className="menuIcon">
                    <i className="fas fa-bars fa-lg" style={{ color: 'white' }}></i>
                </div>
                <div style={{ textAlign: 'center' }}>HOLAAA danielito</div>
            </div>
            <div className="menuContent">
                <div className="menuContentItems">
                    <div className="menuItem">
                        <a href="#" className="itemClick">
                            <i className="fas fa-home fa-sm"></i>
                            <span>Home</span>
                        </a>
                    </div>
                    <div className="menuItem">
                        <a href="#content-wrap" className="itemClick">
                            <i className="fas fa-users fa-sm"></i>
                            <span>Players</span>
                        </a>
                    </div>
                    <div className="menuItem">
                        <a href="#groups-wrap" className="itemClick">
                            <i className="fas fa-compress fa-sm"></i>
                            <span>Groups</span>
                        </a>
                    </div>
                    <div className="menuItem">
                        <a href="#matches-wrap" className="itemClick">
                            <i className="fas fa-futbol fa-sm"></i>
                            <span>Matches</span>
                        </a>
                    </div>
                    <div className="menuItem">
                        <a href="javascript:void(0); displaySignIn();">
                            <i className="fas fa-sign-in-alt fa-sm"></i>
                            <span>Sign in</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    }
}