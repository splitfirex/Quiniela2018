class BreadCrumbs extends React.Component {

    render() {
        var content = this.props.listRender.map(function (currentvalue, index, array) {
            return <div>{currentvalue}</div>
        });

        return <div className="breadCrumbs">
            {content}
        </div>;
    }
}

BreadCrumbs.defaultProps = {
    listRender: [],
    fnGoBack: function () { }
}
