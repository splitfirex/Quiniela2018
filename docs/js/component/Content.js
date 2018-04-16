class Content extends React.Component {

    constructor(props) {
        super(props);

        this.state= {
            showLadders: true,
            title: "LADDERBOARDS"
        }

    }

    renderContent(){
        if(this.state.showLadders){
            return <ShowLadders />
        }else{
            return <ShowPlayers />
        }
    }

    render() {
        return <div id="content-wrap">
           <div className="section-title">{this.state.title}</div>
           {this.renderContent()}
        </div>
    }
}