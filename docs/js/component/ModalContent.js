function ModalContent(props) {
    return (<div className={"modalContent " + (props.showModal && "show")}>
        <div className="backButton" onClick={() => props.dispatch({ type: "CLOSE_MODAL" })}>
            <div className="iconCenter">
                <i className="fas fa-times"></i>
            </div>
        </div>
        {props.contentModalWindow == "SIGNIN" && <ContentLogin {...props} />}
        {props.contentModalWindow == "SIGNUP" && <ContentRegister {...props} />}
        {props.contentModalWindow == "NEWLADDER" && <ContentNewLadder {...props} />}
        {props.contentModalWindow == "JOINLADDER" && <ContentJoinLadder {...props} />}
        {props.contentModalWindow == "BANPLAYER" && <ContentBanPlayer {...props} />}
        {props.contentModalWindow == "LEAVELADDER" && <ContentLeaveLadder {...props} />}
        
    </div>)
}


function ModalLoading() {
    return (<div style={{ textAlign: "center", fontSize: "1em", alignSelf: "center" }}><i className="fas fa-cog fa-spin"></i> <br /> Procesando... </div>)
}