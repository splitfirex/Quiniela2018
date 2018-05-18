package quinielas.repository2;

import org.springframework.stereotype.Repository;
import quinielas.utils.dom.DOMGroup;
import quinielas.utils.dom.DOMMatch;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface DOMGroupCustomRepository{
    void updateGroupsMatchesByNearDate();

}
