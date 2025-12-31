package ru.job4j.bmb.repositories;

import org.springframework.test.fake.CrudRepositoryFake;
import ru.job4j.bmb.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserFakeRepository extends CrudRepositoryFake<User, Long> implements UserRepository {

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<>(memory.values());
    }

    @Override
    public User findByClientId(Long clientId) {
        return null;
    }
}
