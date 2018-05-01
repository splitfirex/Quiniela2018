function GlobalContent(props) {
    return (
        <div className={"content-warp " + (props.showBreadcrumbs && "showBreadcrumbs")} >
            <div className="content">

                {props.contentWindow == "LADDERS" && <ContentLadder {...props} />}
                {props.contentWindow == "GROUPS" && <ContentGroup  {...props} />}
                {props.contentWindow == "MATCHES" && <ContentMatch {...props} />}
                {props.contentWindow == "PLAYERS" && <ContentPlayer {...props} />}

            </div>
        </div>
    )
}