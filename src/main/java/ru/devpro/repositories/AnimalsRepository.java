package ru.devpro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import ru.devpro.model.Animal;

import java.util.List;
@Repository

public interface AnimalsRepository extends JpaRepository<Animal,Long> {
    List<Animal> findByBreedIgnoreCase(String breed);

    //Animal findByChatId(Long chatId);
}
