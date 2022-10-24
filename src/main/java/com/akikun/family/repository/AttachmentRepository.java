package com.akikun.family.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import com.akikun.family.model.entity.Attachment;
import com.akikun.family.model.enums.AttachmentType;
import com.akikun.family.repository.base.BaseRepository;

/**
 * Attachment repository
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-04-03
 */
public interface AttachmentRepository
    extends BaseRepository<Attachment, Integer>, JpaSpecificationExecutor<Attachment> {

    /**
     * Find all attachment media type.
     *
     * @return list of media type.
     */
    @Query(value = "select distinct a.mediaType from Attachment a")
    List<String> findAllMediaType();

    /**
     * Find all attachment type.
     *
     * @return list of type.
     */
    @Query(value = "select distinct a.type from Attachment a")
    List<AttachmentType> findAllType();

    /**
     * Counts by attachment path.
     *
     * @param path attachment path must not be blank
     * @return count of the given path
     */
    long countByPath(@NonNull String path);

    /**
     * Counts by attachment file key and type.
     *
     * @param fileKey attachment file key must not be blank
     * @param type attachment type must not be null
     * @return count of the given path and type
     */
    long countByFileKeyAndType(@NonNull String fileKey, @NonNull AttachmentType type);
}
