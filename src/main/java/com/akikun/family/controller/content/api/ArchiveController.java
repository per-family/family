package com.akikun.family.controller.content.api;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.akikun.family.model.vo.ArchiveMonthVO;
import com.akikun.family.model.vo.ArchiveYearVO;
import com.akikun.family.service.PostService;

/**
 * Content archive controller.
 *
 * @author johnniang
 * @date 2019-04-02
 */
@RestController("ApiContentArchiveController")
@RequestMapping("/api/content/archives")
public class ArchiveController {

    private final PostService postService;

    public ArchiveController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("years")
    public List<ArchiveYearVO> listYearArchives() {
        return postService.listYearArchives();
    }

    @GetMapping("months")
    public List<ArchiveMonthVO> listMonthArchives() {
        return postService.listMonthArchives();
    }
}
