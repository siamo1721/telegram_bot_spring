package ru.tgbot.tgbot.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import ru.tgbot.tgbot.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
