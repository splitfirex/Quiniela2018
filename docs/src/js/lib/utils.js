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
            return teams[match[loc] - 1];
        case "qualified":
            var g = group.filter(function (el) {
                return el.groupName == match[loc2].split("_")[0];
            })[0];
            if (g.details[parseInt(match[loc2].split("_")[1]) - 1].p == 0) return match[loc2];
            return teams[g.details[parseInt(match[loc2].split("_")[1]) - 1].id - 1];
        case "winner":
            var m = matches.filter((e) => e.id == match[loc2].replace(/\D+/g, '') )[0];
            if (m.home_result == null || m.away_result == null) return match[loc2];
            if (m.home_result == m.away_result) {
                return teams[(m.home_penalty > m.away_penalty ? m.home_team : m.away_team)];
            }
            return teams[(m.home_result > m.away_result ? m.home_team : m.away_team)];
        case "loser":
            var m = matches.filter((e) => e.id == match[loc2].replace(/\D+/g, '') )[0];
            if (m.home_result == null || m.away_result == null) return match[loc2];
            if (m.home_result == m.away_result) {
                return teams[(m.home_penalty < m.away_penalty ? m.home_team : m.away_team)];
            }
            return teams[(m.home_result < m.away_result ? m.home_team : m.away_team)];

    }
}

export default zeroPad;



