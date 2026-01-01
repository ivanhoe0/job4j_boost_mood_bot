package ru.job4j.bmb.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.bmb.model.MoodLog;
import ru.job4j.bmb.model.User;

import java.util.List;

@Repository
public interface MoodLogRepository extends CrudRepository<MoodLog, Long> {
    List<MoodLog> findByUser(User user);

    @Query("SELECT u FROM User u")
    List<User> findUsersWhoDidNotVoteToday(long startOfDay, long endOfDay);
}
