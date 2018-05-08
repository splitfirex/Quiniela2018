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
import quinielas.utils.dom.DOMTeam;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;

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

   /* @PostConstruct
    private void init() {
        if(cleanAndBuild) {
            requestData();
        }
    }*/



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

            domTeamRepository.saveAll(domTeams);
            domGroupRepository.saveAll(domGroups);

            groupService.setDefaultList(domGroups);

        }catch(Exception ex){
            ex.printStackTrace();
        }
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
