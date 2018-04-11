class Question extends React.Component {

    constructor(props) {
        super(props);
    }

    onClickCancel(){
        this.props.fnOnClick();
    }

    onClickAccept(){
        this.props.fnOnClick();
    }


    render() {
        return <div className={this.props.className}  id="question">
            <div className="question">
                <div className="title">Titulo</div>
                <div className="content">lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem </div>
                <div className="buttons">
                <button onClick={this.onClickAccept.bind(this)} className="accept">Aceptar</button> 
                <button onClick={this.onClickCancel.bind(this)} className="cancel">Cancelar</button></div>

            </div>
        </div>
    }
}