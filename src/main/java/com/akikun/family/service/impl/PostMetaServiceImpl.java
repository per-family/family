package com.akikun.family.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.akikun.family.exception.NotFoundException;
import com.akikun.family.model.entity.PostMeta;
import com.akikun.family.repository.PostRepository;
import com.akikun.family.repository.base.BaseMetaRepository;
import com.akikun.family.service.PostMetaService;

/**
 * Post meta service implementation class.
 *
 * @author ryanwang
 * @author ikaisec
 * @author guqing
 * @date 2019-08-04
 */
@Slf4j
@Service
public class PostMetaServiceImpl extends BaseMetaServiceImpl<PostMeta> implements PostMetaService {

    private final PostRepository postRepository;

    public PostMetaServiceImpl(BaseMetaRepository<PostMeta> baseMetaRepository,
        PostRepository postRepository) {
        super(baseMetaRepository);
        this.postRepository = postRepository;
    }

    @Override
    public void validateTarget(@NonNull Integer postId) {
        postRepository.findById(postId)
            .orElseThrow(() -> new NotFoundException("查询不到该文章的信息").setErrorData(postId));
    }
}
