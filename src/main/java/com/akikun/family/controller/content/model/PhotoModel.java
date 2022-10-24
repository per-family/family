package com.akikun.family.controller.content.model;

import static org.springframework.data.domain.Sort.Direction.DESC;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import com.akikun.family.model.dto.PhotoDTO;
import com.akikun.family.model.properties.SheetProperties;
import com.akikun.family.service.OptionService;
import com.akikun.family.service.PhotoService;
import com.akikun.family.service.ThemeService;

/**
 * @author ryanwang
 * @date 2020-02-11
 */
@Component
public class PhotoModel {

    private final PhotoService photoService;

    private final ThemeService themeService;

    private final OptionService optionService;

    public PhotoModel(PhotoService photoService,
        ThemeService themeService,
        OptionService optionService) {
        this.photoService = photoService;
        this.themeService = themeService;
        this.optionService = optionService;
    }

    public String list(Integer page, Model model) {

        int pageSize = optionService.getByPropertyOrDefault(SheetProperties.PHOTOS_PAGE_SIZE,
            Integer.class,
            Integer.parseInt(SheetProperties.PHOTOS_PAGE_SIZE.defaultValue()));

        Pageable pageable =
            PageRequest.of(page >= 1 ? page - 1 : page, pageSize, Sort.by(DESC, "createTime"));

        Page<PhotoDTO> photos = photoService.pageBy(pageable);

        model.addAttribute("is_photos", true);
        model.addAttribute("photos", photos);
        model.addAttribute("meta_keywords", optionService.getSeoKeywords());
        model.addAttribute("meta_description", optionService.getSeoDescription());
        return themeService.render("photos");
    }
}
