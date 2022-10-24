package com.akikun.family.controller.content.model;

import static org.springframework.data.domain.Sort.Direction.DESC;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import com.akikun.family.model.dto.TagDTO;
import com.akikun.family.model.entity.Post;
import com.akikun.family.model.entity.Tag;
import com.akikun.family.model.enums.PostStatus;
import com.akikun.family.model.vo.PostListVO;
import com.akikun.family.service.OptionService;
import com.akikun.family.service.PostTagService;
import com.akikun.family.service.TagService;
import com.akikun.family.service.ThemeService;
import com.akikun.family.service.assembler.PostRenderAssembler;

/**
 * Tag Model.
 *
 * @author ryanwang
 * @date 2020-01-11
 */
@Component
public class TagModel {

    private final TagService tagService;

    private final PostRenderAssembler postRenderAssembler;

    private final PostTagService postTagService;

    private final OptionService optionService;

    private final ThemeService themeService;

    public TagModel(TagService tagService,
        PostRenderAssembler postRenderAssembler,
        PostTagService postTagService,
        OptionService optionService,
        ThemeService themeService) {
        this.tagService = tagService;
        this.postRenderAssembler = postRenderAssembler;
        this.postTagService = postTagService;
        this.optionService = optionService;
        this.themeService = themeService;
    }

    public String list(Model model) {
        model.addAttribute("is_tags", true);
        model.addAttribute("meta_keywords", optionService.getSeoKeywords());
        model.addAttribute("meta_description", optionService.getSeoDescription());
        return themeService.render("tags");
    }

    public String listPost(Model model, String slug, Integer page) {
        // Get tag by slug
        final Tag tag = tagService.getBySlugOfNonNull(slug);
        TagDTO tagDTO = tagService.convertTo(tag);

        final Pageable pageable = PageRequest
            .of(page - 1, optionService.getArchivesPageSize(), Sort.by(DESC, "createTime"));
        Page<Post> postPage =
            postTagService.pagePostsBy(tag.getId(), PostStatus.PUBLISHED, pageable);
        Page<PostListVO> posts = postRenderAssembler.convertToListVo(postPage);

        model.addAttribute("is_tag", true);
        model.addAttribute("posts", posts);
        model.addAttribute("tag", tagDTO);
        model.addAttribute("meta_keywords", optionService.getSeoKeywords());
        model.addAttribute("meta_description", optionService.getSeoDescription());
        return themeService.render("tag");
    }
}
