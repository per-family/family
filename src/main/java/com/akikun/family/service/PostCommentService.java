package com.akikun.family.service;

import com.akikun.family.model.entity.PostComment;
import com.akikun.family.service.base.BaseCommentService;

/**
 * Post comment service interface.
 *
 * @author johnniang
 * @author ryanwang
 * @author guqing
 * @date 2019-03-14
 */
public interface PostCommentService extends BaseCommentService<PostComment> {

    /**
     * Validate CommentBlackList Status.
     */
    void validateCommentBlackListStatus();
}
