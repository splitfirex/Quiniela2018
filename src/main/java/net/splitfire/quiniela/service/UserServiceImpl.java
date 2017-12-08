package net.splitfire.quiniela.service;

import java.util.*;

import com.mongodb.DBObject;
import net.splitfire.quiniela.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	DBService dbService;

	static Map<String,User> users = new HashMap<String,User>();
	static{
		testData();
	}

	@Override
	public User findByName(String name) {
		return users.get(name);
	}

	@Override
	public void saveUser(User user) {

	}

	@Override
	public List<User> getUserList() {
		DBObject doc = new User("Gallego", "splitfire","1234", "1234").getDBObject();
		List result = new ArrayList();
		Iterator i =dbService.getDBInstance().listCollectionNames().iterator();
		while (i.hasNext()){
			result.add(i.next());
		}
		if(!result.contains("users")){
			dbService.getDBInstance().createCollection("users");
		};

		List<User> liu = new ArrayList<User>(users.values());
		for(User u : liu){
			u.setPassword(null);
		}

		return liu;
	}


	public static void testData(){
		/*DBObject doc = new User("Gallego", "splitfire","1234", "1234").getDBObject();
		if(!dbService.getDBInstance().collectionExists("users")){
			dbService.getDBInstance().createCollection("users", null);
		};*/
		//users.put("Gallego",new User("Gallego", "splitfire","1234", "1234"));
		//users.put("Gallego2",new User("Gallego2", "splitfire2","12342", "12342"));
	}
}
