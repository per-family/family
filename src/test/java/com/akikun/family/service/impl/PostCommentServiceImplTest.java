package com.akikun.family.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ActiveProfiles;
import com.akikun.family.model.dto.BaseCommentDTO;
import com.akikun.family.model.entity.PostComment;
import com.akikun.family.model.params.PostCommentParam;
import com.akikun.family.model.properties.CommentProperties;
import com.akikun.family.repository.PostCommentRepository;
import com.akikun.family.repository.PostRepository;
import com.akikun.family.service.CommentBlackListService;
import com.akikun.family.service.OptionService;
import com.akikun.family.service.UserService;
import com.akikun.family.service.assembler.comment.PostCommentAssembler;

@SpringBootTest
@ActiveProfiles("test")
public class PostCommentServiceImplTest {

    private static final String POST_COMMENT_CONTENT_TEST = "TestContent";

    private static final String POST_COMMENT_AUTHOR_TEST = "TestAuthor";

    private static final String POST_COMMENT_GRAVATAR_MD5_TEST = "d41d8cd98f00b204e9800998ecf8427e";

    private static final String GRAVATAR_SOURCE_TEST = "//example.com/avatar/";

    private static final String GRAVATAR_DEFAULT_TEST = "";

    @Mock
    private PostCommentRepository mockPostCommentRepository;

    @Mock
    private PostRepository mockPostRepository;

    @Mock
    private UserService mockUserService;

    @Mock
    private OptionService mockOptionService;

    @Mock
    private CommentBlackListService mockCommentBlackListService;

    @Mock
    private ApplicationEventPublisher mockApplicationEventPublisher;

    @Mock
    private PostComment mockPostComment;

    private PostCommentServiceImpl postCommentService;

    private PostCommentAssembler postCommentAssembler;

    private PostCommentParam postCommentParam;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        postCommentAssembler = new PostCommentAssembler(mockOptionService, mockPostRepository);

        postCommentService = new PostCommentServiceImpl(
            mockPostCommentRepository,
            mockPostRepository,
            mockUserService,
            mockOptionService,
            mockCommentBlackListService,
            mockApplicationEventPublisher,
            postCommentAssembler
        );
        postCommentParam = new PostCommentParam();
    }

    @Test
    public void nullEmailWithAuditCreatedByTest() {
        // Arrange
        postCommentParam.setContent(POST_COMMENT_CONTENT_TEST);
        postCommentParam.setAuthor(POST_COMMENT_AUTHOR_TEST);
        when(mockOptionService.getByPropertyOrDefault(CommentProperties.NEW_NEED_CHECK,
            Boolean.class, true)).thenReturn(true);
        when(mockPostComment.getId()).thenReturn(1L);
        when(mockPostCommentRepository.save(any(PostComment.class))).thenReturn(mockPostComment);

        // Act
        PostComment result = postCommentService.createBy(postCommentParam);

        // Assert
        Assertions.assertEquals(mockPostComment, result);
    }

    @Test
    public void nullEmailWithoutAuditCreatedByTest() {
        // Arrange
        postCommentParam.setContent(POST_COMMENT_CONTENT_TEST);
        postCommentParam.setAuthor(POST_COMMENT_AUTHOR_TEST);
        when(mockOptionService.getByPropertyOrDefault(CommentProperties.NEW_NEED_CHECK,
            Boolean.class, true)).thenReturn(false);
        when(mockPostComment.getId()).thenReturn(1L);
        when(mockPostCommentRepository.save(any(PostComment.class))).thenReturn(mockPostComment);

        // Act
        PostComment result = postCommentService.createBy(postCommentParam);

        // Assert
        Assertions.assertEquals(mockPostComment, result);
    }

    @Test
    public void nullEmailConvertToTest() {
        // Arrange
        when(mockPostComment.getGravatarMd5()).thenReturn(POST_COMMENT_GRAVATAR_MD5_TEST);
        when(mockOptionService.getByPropertyOrDefault(CommentProperties.GRAVATAR_SOURCE,
            String.class)).thenReturn(GRAVATAR_SOURCE_TEST);
        when(mockOptionService.getByPropertyOrDefault(CommentProperties.GRAVATAR_DEFAULT,
            String.class)).thenReturn(GRAVATAR_DEFAULT_TEST);

        // Act
        BaseCommentDTO result = postCommentAssembler.convertTo(mockPostComment);

        // Assert
        Assertions.assertEquals(
            GRAVATAR_SOURCE_TEST + POST_COMMENT_GRAVATAR_MD5_TEST + "?s=256&d="
                + GRAVATAR_DEFAULT_TEST, result.getAvatar());
    }
}
