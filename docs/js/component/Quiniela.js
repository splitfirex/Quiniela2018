class Quiniela extends React.Component {
    render() {
        return <div className="quiniela" id="quiniela">
            <div> <span style={{ fontSize: '1.5em'}} className="fa-layers fa-fw">
                <i className="far fa-circle" style={{ color:'white'}}></i>
                <i className="fa-inverse fas fa-angle-double-left" data-fa-transform="shrink-6"></i>
            </span></div>
            <div>NOMBRE</div>
            <div> <span style={{ fontSize: '1.5em'}} className="fa-layers fa-fw">
                <i className="far fa-circle" style={{ color:'white'}}></i>
                <i className="fa-inverse fas fa-angle-double-right" data-fa-transform="shrink-6"></i>
            </span></div>
        </div>
    }
}