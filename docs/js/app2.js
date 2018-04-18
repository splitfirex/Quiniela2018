class App extends React.Component {

    constructor(props) {
        super(props);
        this.state={
            showSideMenu: false,
            showBackButton: false,
            showLoading: false,
        }
    }

    toggleMenu() {
        this.setState({
            showSideMenu: !this.state.showSideMenu
        })
    }

    toggleLoading(){
        this.setState({
            showLoading: !this.state.showLoading
        })
    }


    render() {
        if(this.state.showLoading){
            var loading = <Loading fnToggleMenu={this.toggleMenu.bind(this)} renderSideMenu={this.state.showSideMenu} />;
        }

        return <div>
            {loading}
            <Menu fnToggleMenu={this.toggleMenu.bind(this)} renderBack={this.state.showBackButton} />
            <SideMenuModal fnToggleMenu={this.toggleMenu.bind(this)} renderSideMenu={this.state.showSideMenu} />
            <SideMenu renderSideMenu={this.state.showSideMenu} />      

            <Content />
        </div>

    }
}

function Loading(props) {
    return (
        <div className="loading">
            <i className="fas fa-spinner fa-pulse"></i>
        </div>
    )
}


ReactDOM.render(<App />, document.getElementById("root"));
