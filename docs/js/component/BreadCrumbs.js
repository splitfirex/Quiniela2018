function BreadCrumbs(props) {
    var content = props.breadcrumbs.map(function (currentvalue, index, array) {
        return (<div key={"bread" + index} >{currentvalue}</div>)
    });

    return (<div className="breadCrumbs">
        {content}
    </div>);
}

