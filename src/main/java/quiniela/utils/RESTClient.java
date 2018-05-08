package quiniela.utils;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quiniela.repository2.DOMGroupRepository;
import quiniela.repository2.DOMTeamRepository;
import quiniela.utils.dom.DOMGroup;
import quiniela.utils.dom.DOMTeam;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class RESTClient {

    public static List<DOMGroup> domGroups  = new ArrayList<>();
    public static List<DOMTeam> domTeams  = new ArrayList<>();

    @Autowired
    DOMTeamRepository domTeamRepository;

    @Autowired
    DOMGroupRepository domGroupRepository;

    public void requestData(){
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
            while(groupsIte.hasNext()){
                String key = (String)groupsIte.next();
                domGroups.add(new DOMGroup(key, "groups" ,groups.getJSONObject(key)));
            }

            JSONArray teams = obj.getJSONArray("teams");
            for(int i = 0; i < teams.length(); i++){
                domTeams.add(new DOMTeam((JSONObject) teams.get(i)));
            };

            JSONObject knockout =  obj.getJSONObject("knockout");
            Iterator knockoutite = obj.getJSONObject("knockout").keys();
            while(knockoutite.hasNext()){
                String key = (String)knockoutite.next();
                domGroups.add(new DOMGroup(key,"knockout", knockout.getJSONObject(key)));
            }

            domTeamRepository.saveAll(domTeams);
            domGroupRepository.saveAll(domGroups);


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
