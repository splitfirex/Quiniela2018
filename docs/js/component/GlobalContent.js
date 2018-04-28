function GlobalContent(props) {
    return (
        <div className={"content-warp " + (props.showBreadcrumbs && "showBreadcrumbs")} >
            <div className="content">

                <div className="title">{props.subTitle}</div>

                {(props.username != null &&  props.contentWindow == "LADDERS") &&
                    <div className="newLadder" onClick={() => props.dispatch({ type: "GO_TO", dest: "NEW_LADDER" })}>
                        <div> <i className="fas fa-plus" ></i> Crear nueva quiniela</div>
                    </div>
                }

                {props.contentWindow == "LADDERS" && <ContentLadder {...props} />}
                {props.contentWindow == "GROUPS" && <ContentGroup  {...props} />}
                {props.contentWindow == "MATCHES" && <ContentMatch {...props} />}
                {props.contentWindow == "PLAYERS" && <ContentPlayer {...props} />}

            </div>
        </div>
    )
}