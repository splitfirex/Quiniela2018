class BreadCrumbs extends React.Component {

    render() {
        var content = this.props.listRender.map(function (currentvalue, index, array) {
            return <div>{currentvalue}</div>
        });

        var backButton = <div onClick={this.fnGoBack} className="backButton ">
            <div className="iconCenter">
                <i className="fas fa-arrow-left"></i></div></div>;

        return <div className="breadCrumbs">
            {content}
            {this.props.listRender.length > 1 ? backButton : null}
        </div>
    }
}

BreadCrumbs.defaultProps = {
    listRender: [],
    fnGoBack: function () { }
}
