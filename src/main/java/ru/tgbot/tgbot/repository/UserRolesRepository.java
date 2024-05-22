package ru.tgbot.tgbot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.tgbot.tgbot.model.UserRole;

public interface UserRolesRepository extends CrudRepository<UserRole, Long> {
}
