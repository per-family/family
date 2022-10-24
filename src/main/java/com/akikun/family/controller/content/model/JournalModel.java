package com.akikun.family.controller.content.model;

import static org.springframework.data.domain.Sort.Direction.DESC;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import com.akikun.family.model.entity.Journal;
import com.akikun.family.model.enums.JournalType;
import com.akikun.family.model.properties.SheetProperties;
import com.akikun.family.service.JournalService;
import com.akikun.family.service.OptionService;
import com.akikun.family.service.ThemeService;

/**
 * @author ryanwang
 * @date 2020-02-11
 */
@Component
public class JournalModel {

    private final JournalService journalService;

    private final OptionService optionService;

    private final ThemeService themeService;

    public JournalModel(JournalService journalService,
        OptionService optionService,
        ThemeService themeService) {
        this.journalService = journalService;
        this.optionService = optionService;
        this.themeService = themeService;
    }

    public String list(Integer page, Model model) {

        int pageSize = optionService
            .getByPropertyOrDefault(SheetProperties.JOURNALS_PAGE_SIZE, Integer.class,
                Integer.parseInt(SheetProperties.JOURNALS_PAGE_SIZE.defaultValue()));

        Pageable pageable =
            PageRequest.of(page >= 1 ? page - 1 : page, pageSize, Sort.by(DESC, "createTime"));

        Page<Journal> journals = journalService.pageBy(JournalType.PUBLIC, pageable);

        model.addAttribute("is_journals", true);
        model.addAttribute("journals", journalService.convertToCmtCountDto(journals));
        model.addAttribute("meta_keywords", optionService.getSeoKeywords());
        model.addAttribute("meta_description", optionService.getSeoDescription());
        return themeService.render("journals");
    }
}
