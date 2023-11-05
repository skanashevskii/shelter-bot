package ru.devpro.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.devpro.model.Avatar;

import java.io.IOException;

@Service
public interface AvatarService {
    //void uploadAvatar(Long animalId, MultipartFile avatar) throws IOException;
    void uploadAvatar(MultipartFile avatar) throws IOException;

    Avatar findAvatar(Long id);

    Page<Avatar> listAvatars(Pageable pageable);
}
