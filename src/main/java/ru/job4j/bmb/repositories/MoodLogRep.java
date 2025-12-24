package ru.job4j.bmb.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.bmb.model.MoodLog;

@Repository
public interface MoodLogRep extends CrudRepository<MoodLog, Long> {
}
