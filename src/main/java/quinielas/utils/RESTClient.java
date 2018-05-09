package quinielas.utils;

import com.fasterxml.uuid.Generators;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import quinielas.repository2.DOMGroupRepository;
import quinielas.repository2.DOMTeamRepository;
import quinielas.service2.GroupService;
import quinielas.utils.dom.DOMGroup;
import quinielas.utils.dom.DOMMatch;
import quinielas.utils.dom.DOMTeam;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RESTClient {



    @Autowired
    DOMTeamRepository domTeamRepository;

    @Autowired
    DOMGroupRepository domGroupRepository;

    @Autowired
    GroupService groupService;

    @Value("${clean_and_build}")
    Boolean cleanAndBuild;

    public void requestData(){
        List<DOMGroup> domGroups  = new LinkedList<>();
        List<DOMTeam> domTeams  = new LinkedList<>();
        try {
            domTeamRepository.deleteAll();
            domGroupRepository.deleteAll();

            ClassLoader classLoader = CVSParser.class.getClassLoader();
            File file = new File(classLoader.getResource("data.json").getFile());
            FileInputStream fis = new FileInputStream(file);

            String data = getFileContent(fis,"UTF-8");
            JSONObject obj = new JSONObject(data);

            JSONObject groups =  obj.getJSONObject("groups");
            Iterator groupsIte = obj.getJSONObject("groups").keys();
            short counter =0;
            while(groupsIte.hasNext()){
                String key = (String)groupsIte.next();
                domGroups.add(new DOMGroup(Generators.randomBasedGenerator().generate().toString(),counter++, "groups" ,groups.getJSONObject(key)));
            }

            JSONArray teams = obj.getJSONArray("teams");
            for(int i = 0; i < teams.length(); i++){
                domTeams.add(new DOMTeam((JSONObject) teams.get(i)));
            };

            JSONObject knockout =  obj.getJSONObject("knockout");
            Iterator knockoutite = obj.getJSONObject("knockout").keys();

            while(knockoutite.hasNext()){
                String key = (String)knockoutite.next();
                domGroups.add(new DOMGroup(Generators.randomBasedGenerator().generate().toString(),counter++,"knockout", knockout.getJSONObject(key)));
            }

            updateReferences(domGroups);

            domTeamRepository.saveAll(domTeams);
            domGroupRepository.saveAll(domGroups);

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void updateReferences(List<DOMGroup> domGroups){
        List<DOMMatch> matches = domGroups.stream().map(v -> v.getMatches()).flatMap(List::stream)
                .sorted((f2, f1) -> f2.getDate().compareTo(f1.getDate())).collect(Collectors.toList());

        matches.stream().forEach( m ->{
            if(m.getType().equals("winner") || m.getType().equals("loser")){
                Long idMatchHome = Long.parseLong(m.getHome_team_ph().replace("W","").replace("L",""));
                Long idMatchAway = Long.parseLong(m.getAway_team_ph().replace("W","").replace("L",""));

                matches.stream().filter(p-> p.getId() == idMatchHome).forEach(p-> p.getReferences().add(m.getId()));
                matches.stream().filter(p-> p.getId() == idMatchAway).forEach(p-> p.getReferences().add(m.getId()));
            }
        });
    }

    public static String getFileContent(
            FileInputStream fis,
            String          encoding ) throws IOException
    {
        try( BufferedReader br =
                     new BufferedReader( new InputStreamReader(fis, encoding )))
        {
            StringBuilder sb = new StringBuilder();
            String line;
            while(( line = br.readLine()) != null ) {
                sb.append( line );
                sb.append( '\n' );
            }
            return sb.toString();
        }
    }
}
