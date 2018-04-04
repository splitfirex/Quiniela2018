package quiniela.repository;

import org.springframework.data.mongodb.repository.Query;
import quiniela.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

public interface GroupRepository extends MongoRepository<Group, Long> {

    @Query("{ 'name' : ?0 }")
    Group findByName(String model);
}
