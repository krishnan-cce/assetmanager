package com.udyata.linentrack.linentrack.repositorynnew;

import com.udyata.linentrack.linentrack.entitynew.profilephoto.ProfilePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfilePhotoRepository extends JpaRepository<ProfilePhoto,Long> {
    Optional<ProfilePhoto> findByUserId(Long userId);
}
