package com.akikun.family.controller.content;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.akikun.family.controller.content.auth.CategoryAuthentication;
import com.akikun.family.controller.content.auth.ContentAuthentication;
import com.akikun.family.controller.content.auth.ContentAuthenticationManager;
import com.akikun.family.controller.content.auth.ContentAuthenticationRequest;
import com.akikun.family.controller.content.auth.PostAuthentication;
import com.akikun.family.exception.AuthenticationException;
import com.akikun.family.model.entity.Category;
import com.akikun.family.model.enums.EncryptTypeEnum;
import com.akikun.family.service.CategoryService;
import com.akikun.family.service.PostCategoryService;
import com.akikun.family.service.PostService;

/**
 * Test for {@link com.akikun.family.controller.content.auth.ContentAuthenticationManager}.
 *
 * @author guqing
 * @date 2022-02-26
 */
@ExtendWith(SpringExtension.class)
public class ContentAuthenticationManagerTest {
    @MockBean
    private CategoryService categoryService;

    @MockBean
    private CategoryAuthentication categoryAuthentication;

    @MockBean
    private PostService postService;

    @MockBean
    private PostAuthentication postAuthentication;

    @MockBean
    private PostCategoryService postCategoryService;

    private ContentAuthenticationManager contentAuthenticationManager;

    @BeforeEach
    public void setUp() {
        contentAuthenticationManager =
            new ContentAuthenticationManager(categoryService, categoryAuthentication, postService,
                postAuthentication, postCategoryService);
    }

    @Test
    public void authenticateCategoryTest() {
        /*
         * category-1(加密)
         * |   |-category-2(未设密码)
         */
        Category category1 = new Category();
        category1.setId(1);
        category1.setPassword("123");
        category1.setName("category-1");
        category1.setSlug("category-1");
        category1.setParentId(0);

        Category category2 = new Category();
        category2.setId(2);
        category2.setPassword(null);
        category2.setName("category-2");
        category2.setSlug("category-2");
        category2.setParentId(1);

        // piling object
        when(categoryService.lookupFirstEncryptedBy(2))
            .thenReturn(Optional.of(category1));
        when(categoryService.getById(1)).thenReturn(category1);
        when(categoryService.getById(2)).thenReturn(category2);

        // build parameter
        ContentAuthenticationRequest authRequest =
            ContentAuthenticationRequest.of(2, "", EncryptTypeEnum.CATEGORY.getName());

        // test empty password
        assertThatThrownBy(() -> contentAuthenticationManager.authenticate(authRequest))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("密码不正确");

        // test null password
        authRequest.setPassword(null);
        assertThatThrownBy(() -> contentAuthenticationManager.authenticate(authRequest))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("密码不正确");

        // test incorrect password
        authRequest.setPassword("ABCD");
        assertThatThrownBy(() -> contentAuthenticationManager.authenticate(authRequest))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("密码不正确");

        // test correct password
        authRequest.setPassword("123");
        ContentAuthentication authentication =
            contentAuthenticationManager.authenticate(authRequest);
        assertThat(authentication).isNotNull();
    }
}
