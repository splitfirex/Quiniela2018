class App extends React.Component {

    render() {
        return [
            <div  key="header" className="header" id="header">
                <img src="img/woldcup.svg" width="100%" />
            </div>,
            <Notification key="notification"/>,
            <Quiniela key="quiniela"/>,
            <Content key="content"/>,
            <Groups key="groups"/>,
            <Matches key="matches"/>,
            //<PlayerMatches key="PlayerMatches"/>,
            <SlideMenu key="SlideMenu"/>
        ]
    }
}


ReactDOM.render(<App />, document.getElementById("container"));
