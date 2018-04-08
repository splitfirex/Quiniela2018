class PlayerMatches extends React.Component {
    render(){
        return     <div id="playerMatches-wrap">
        <div className="section-title matchesTitle">PLAYER MATCHES</div>
        <div id="playerMatches">
          <div className="playerMatchesItem">
            <div>05/18 15:00</div>
            <div className="playerMatchesItem-result">
              <div></div>
              <div className="team-box-wcontrol">
                <div style={{gridColumn: 'span 2'}}></div>
                <div>
                  <span style={{fontSize:'2em'}} className="fa-layers fa-fw">
                    <i className="far fa-circle" style={{color:'white'}}></i>
                    <i className="fa-inverse fas fa-angle-up" data-fa-transform="shrink-6"></i>
                  </span>
                </div>
                <div className="control-team">KOR</div>
                <div className="flag smBel"></div>
                <div className="control-points">5</div>
                <div  style={{gridColumn: 'span 2'}}></div>
                <div>
                  <span style={{fontSize:'2em'}} className="fa-layers fa-fw">
                    <i className="far fa-circle" style={{color:'white'}}></i>
                    <i className="fa-inverse fas fa-angle-down" data-fa-transform="shrink-6"></i>
                  </span>
                </div>
              </div>
              <div className="team-separator">
                <i className="fas fa-minus fa-lg" style={{color:'white'}}></i>
              </div>
              <div className="team-box-wcontrol">
                <div>
                  <span style={{fontSize:'2em'}} className="fa-layers fa-fw">
                    <i className="far fa-circle" style={{color:'white'}}></i>
                    <i className="fa-inverse fas fa-angle-up" data-fa-transform="shrink-6"></i>
                  </span>
                </div>
                <div  style={{gridColumn: 'span 2'}}></div>
                <div className="control-points">5</div>
                <div className="flag smBel"></div>
                <div className="control-team">KOR</div>
                <div>
                  <span style={{fontSize:'2em'}} className="fa-layers fa-fw">
                    <i className="far fa-circle" style={{color:'white'}}></i>
                    <i className="fa-inverse fas fa-angle-down" data-fa-transform="shrink-6"></i>
                  </span>
                </div>
                <div  style={{gridColumn: 'span 2'}}></div>
              </div>
              <div></div>
            </div>
  
          </div>
    
        </div>
      </div>
    }
}