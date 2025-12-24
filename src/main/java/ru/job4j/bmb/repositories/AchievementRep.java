package ru.job4j.bmb.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.bmb.model.Achievement;

@Repository
public interface AchievementRep extends CrudRepository<Achievement, Long> {
}
