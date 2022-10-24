package com.akikun.family.repository;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import com.akikun.family.model.entity.SheetComment;
import com.akikun.family.model.enums.CommentStatus;
import com.akikun.family.model.projection.CommentChildrenCountProjection;
import com.akikun.family.model.projection.CommentCountProjection;
import com.akikun.family.repository.base.BaseCommentRepository;

/**
 * Sheet comment repository.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-04-24
 */
public interface SheetCommentRepository extends BaseCommentRepository<SheetComment> {

    /**
     * Count comments by sheet ids.
     *
     * @param sheetIds sheet id collection must not be null
     * @return a list of CommentCountProjection
     */
    @Query(
        "select new com.akikun.family.model.projection.CommentCountProjection(count(comment.id), "
            + "comment.postId) "
            + "from SheetComment comment "
            + "where comment.postId in ?1 group by comment.postId")
    @NonNull
    @Override
    List<CommentCountProjection> countByPostIds(@NonNull Collection<Integer> sheetIds);

    /**
     * Counts comment count by comment status and sheet id collection.
     *
     * @param status status must not be null
     * @param sheetsId sheet id collection must not be null
     * @return a list of comment count
     */
    @Query(
        "select new com.akikun.family.model.projection.CommentCountProjection(count(comment.id), "
            + "comment.postId) "
            + "from SheetComment comment "
            + "where comment.status = ?1 "
            + "and comment.postId in ?2 "
            + "group by comment.postId")
    @NonNull
    @Override
    List<CommentCountProjection> countByStatusAndPostIds(@NonNull CommentStatus status,
        @NonNull Collection<Integer> sheetsId);

    /**
     * Finds direct children count by comment ids.
     *
     * @param commentIds comment ids must not be null.
     * @return a list of CommentChildrenCountProjection
     */
    @Query(
        "select new com.akikun.family.model.projection.CommentChildrenCountProjection(count(comment"
            + ".id), comment.parentId) "
            + "from SheetComment comment "
            + "where comment.parentId in ?1 "
            + "group by comment.parentId")
    @NonNull
    @Override
    List<CommentChildrenCountProjection> findDirectChildrenCount(
        @NonNull Collection<Long> commentIds);
}
