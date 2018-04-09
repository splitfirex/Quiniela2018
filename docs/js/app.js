class App extends React.Component {

    render() {

        return [
            <SlideMenu key="SlideMenu" />,
            <div id="container" class="container">
                <div className="header" id="header">
                    <img src="img/woldcup.svg" width="100%" />
                </div>,
                <Notification />
                <Quiniela />
                <Content />
                <Groups />
                <Matches />
                <PlayerMatches />

            </div>
        ]

    }
}


ReactDOM.render(<App />, document.getElementById("root"));
