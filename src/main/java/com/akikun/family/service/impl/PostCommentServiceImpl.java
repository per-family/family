package com.akikun.family.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.akikun.family.exception.BadRequestException;
import com.akikun.family.exception.ForbiddenException;
import com.akikun.family.exception.NotFoundException;
import com.akikun.family.model.entity.Post;
import com.akikun.family.model.entity.PostComment;
import com.akikun.family.model.enums.CommentViolationTypeEnum;
import com.akikun.family.model.properties.CommentProperties;
import com.akikun.family.repository.PostCommentRepository;
import com.akikun.family.repository.PostRepository;
import com.akikun.family.service.CommentBlackListService;
import com.akikun.family.service.OptionService;
import com.akikun.family.service.PostCommentService;
import com.akikun.family.service.UserService;
import com.akikun.family.service.assembler.comment.PostCommentAssembler;
import com.akikun.family.utils.ServletUtils;

/**
 * PostCommentService implementation class.
 *
 * @author ryanwang
 * @author johnniang
 * @author guqing
 * @date 2019-03-14
 */
@Slf4j
@Service
public class PostCommentServiceImpl extends BaseCommentServiceImpl<PostComment>
    implements PostCommentService {

    private final PostRepository postRepository;

    private final CommentBlackListService commentBlackListService;

    public PostCommentServiceImpl(PostCommentRepository postCommentRepository,
        PostRepository postRepository,
        UserService userService,
        OptionService optionService,
        CommentBlackListService commentBlackListService,
        ApplicationEventPublisher eventPublisher,
        PostCommentAssembler postCommentAssembler) {
        super(postCommentRepository, optionService, userService, eventPublisher,
            postCommentAssembler);
        this.postRepository = postRepository;
        this.commentBlackListService = commentBlackListService;
    }

    @Override
    public void validateTarget(@NonNull Integer postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new NotFoundException("查询不到该文章的信息").setErrorData(postId));

        if (post.getDisallowComment()) {
            throw new BadRequestException("该文章已经被禁止评论").setErrorData(postId);
        }
    }

    @Override
    public void validateCommentBlackListStatus() {
        CommentViolationTypeEnum banStatus =
            commentBlackListService.commentsBanStatus(ServletUtils.getRequestIp());
        Integer banTime = optionService
            .getByPropertyOrDefault(CommentProperties.COMMENT_BAN_TIME, Integer.class, 10);
        if (banStatus == CommentViolationTypeEnum.FREQUENTLY) {
            throw new ForbiddenException(String.format("您的评论过于频繁，请%s分钟之后再试。", banTime));
        }
    }

}
