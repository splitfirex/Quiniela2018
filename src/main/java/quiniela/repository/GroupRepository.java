package quiniela.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import quiniela.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@Repository
public interface GroupRepository extends MongoRepository<Group, Long> {

    @Query("{ 'name' : ?0 }")
    Group findByName(String model);

    @Query("{ 'id' : ?0 }")
    Group findById(Integer model);
}
