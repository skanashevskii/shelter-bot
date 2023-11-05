package ru.devpro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.devpro.model.User;
@Repository
public interface UsersRepository extends JpaRepository<User,Long> {

    }

