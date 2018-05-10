export var zeroPad = (num, places) => {
    var zero = places - num.toString().length + 1;
    return Array(+(zero > 0 && zero)).join("0") + num;
}


export var colorScore = (score) => {
    if (score == null) return "whiteBG";
    switch (score) {
        case 0:
            return "tomatoBG";
        case 1:
            return "yellowBG";
        case 3:
            return "greenBG";
    }
}

export var calculateTeam = (match, group, matches, teams, location) => {
    var loc = location == "home" ? "home_team" : "away_team";
    var loc2 = location == "home" ? "home_team_ph" : "away_team_ph";

    switch (match.type) {
        case "group":
            return getTeamObject(teams, match[loc]);
        case "qualified":
            if (match[loc] != null) getTeamObject(teams, match[loc]);
            var g = group.filter(function (el) {
                return el.groupName == match[loc2].split("_")[0];
            })[0];
            if (g.details[parseInt(match[loc2].split("_")[1]) - 1].p == 0) return match[loc2];
            return getTeamObject(teams,g.details[parseInt(match[loc2].split("_")[1])-1].id);
        case "winner":
            if (match[loc] != null) getTeamObject(teams, match[loc]);

            var m = matches.filter((e) => e.id == match[loc2].replace(/\D+/g, ''))[0];
            if (m.home_result == null || m.away_result == null) return match[loc2];
            if (m.home_result == m.away_result) {
                return getTeamObject(teams,(m.home_penalty > m.away_penalty ? m.home_team : m.away_team));
            }
            return getTeamObject(teams,(m.home_result > m.away_result ? m.home_team : m.away_team));
        case "loser":
            if (match[loc] != null) getTeamObject(teams, match[loc]);

            var m = matches.filter((e) => e.id == match[loc2].replace(/\D+/g, ''))[0];
            if (m.home_result == null || m.away_result == null) return match[loc2];
            if (m.home_result == m.away_result) {
                return getTeamObject(teams,(m.home_penalty < m.away_penalty ? m.home_team : m.away_team));
            }
            return getTeamObject(teams,(m.home_result < m.away_result ? m.home_team : m.away_team));
    }
}

export var getTeamObject = (teams, id) =>{
    return teams.filter(t=>t.id == id)[0];
}

export var recalculateGroups = (group, matches) => {

    group.forEach((i) => {
        i.details.forEach((i2) => {
            i2.p = 0; i2.ng = 0; i2.pg = 0;
        });
    })

    for (var i = 0; i < matches.length; i++) {
        if (matches[i].type != "group" || matches[i].home_team == null || matches[i].away_team == null) continue;
        var homeDetail = group.filter((item) => item.groupName == matches[i].groupname.replace("Group ", ""))[0].details.filter(f => f.id == matches[i].home_team)[0];
        var awayDetail = group.filter((item) => item.groupName == matches[i].groupname.replace("Group ", ""))[0].details.filter(f => f.id == matches[i].away_team)[0];

        homeDetail.p = matches[i].home_result > matches[i].away_result ? homeDetail.p + 3 : homeDetail.p;
        awayDetail.p = matches[i].home_result < matches[i].away_result ? awayDetail.p + 3 : awayDetail.p;
        homeDetail.p = matches[i].home_result == matches[i].away_result ? homeDetail.p + 1 : homeDetail.p;
        awayDetail.p = matches[i].home_result == matches[i].away_result ? awayDetail.p + 1 : awayDetail.p;

        homeDetail.ng = homeDetail.ng + (matches[i].away_result == null ? 0 : matches[i].away_result);
        awayDetail.ng = awayDetail.ng + (matches[i].home_result == null ? 0 : matches[i].home_result);

        homeDetail.pg = homeDetail.ng + (matches[i].home_result == null ? 0 : matches[i].home_result);
        awayDetail.pg = awayDetail.ng + (matches[i].away_result == null ? 0 : matches[i].away_result);
    }

    group.forEach((i) => i.details.sort(function (a, b) {
        if(a.p == b.p){
            return (b.pg - b.ng) - (a.pg - a.ng); 
        }
        return b.p - a.p;
    }));

}

export default zeroPad;



