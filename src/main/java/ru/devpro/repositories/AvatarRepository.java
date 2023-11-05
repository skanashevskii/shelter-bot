package ru.devpro.repositories;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.devpro.model.Avatar;

import java.util.Optional;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar,Long> {

    @NotNull
    Optional<Avatar> findById(@NotNull Long animalId);

    @NotNull
    Page<Avatar> findAll(@NotNull Pageable pageable);
}
