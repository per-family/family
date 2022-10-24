package com.akikun.family.controller.content.model;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import com.akikun.family.cache.AbstractStringCacheStore;
import com.akikun.family.exception.ForbiddenException;
import com.akikun.family.model.entity.Sheet;
import com.akikun.family.model.entity.SheetMeta;
import com.akikun.family.model.enums.PostStatus;
import com.akikun.family.model.support.HaloConst;
import com.akikun.family.model.vo.SheetDetailVO;
import com.akikun.family.service.OptionService;
import com.akikun.family.service.SheetMetaService;
import com.akikun.family.service.SheetService;
import com.akikun.family.service.ThemeService;
import com.akikun.family.service.assembler.SheetRenderAssembler;

/**
 * Sheet model.
 *
 * @author ryanwang
 * @date 2020-01-07
 */
@Component
public class SheetModel {

    private final SheetService sheetService;

    private final SheetRenderAssembler sheetRenderAssembler;

    private final SheetMetaService sheetMetaService;

    private final AbstractStringCacheStore cacheStore;

    private final ThemeService themeService;

    private final OptionService optionService;

    public SheetModel(SheetService sheetService,
        SheetRenderAssembler sheetRenderAssembler,
        SheetMetaService sheetMetaService,
        AbstractStringCacheStore cacheStore,
        ThemeService themeService,
        OptionService optionService) {
        this.sheetService = sheetService;
        this.sheetRenderAssembler = sheetRenderAssembler;
        this.sheetMetaService = sheetMetaService;
        this.cacheStore = cacheStore;
        this.themeService = themeService;
        this.optionService = optionService;
    }

    /**
     * Sheet content.
     *
     * @param sheet sheet
     * @param token token
     * @param model model
     * @return template name
     */
    public String content(Sheet sheet, String token, Model model) {
        SheetDetailVO sheetDetailVo;
        if (StringUtils.isEmpty(token)) {
            sheet = sheetService.getBy(PostStatus.PUBLISHED, sheet.getSlug());
            sheetDetailVo = sheetRenderAssembler.convertToDetailVo(sheet);
        } else {
            // verify token
            String cachedToken = cacheStore.getAny(token, String.class)
                .orElseThrow(() -> new ForbiddenException("您没有该页面的访问权限"));
            if (!cachedToken.equals(token)) {
                throw new ForbiddenException("您没有该页面的访问权限");
            }
            sheetDetailVo = sheetRenderAssembler.convertToPreviewDetailVo(sheet);
        }

        sheetService.publishVisitEvent(sheet.getId());

        List<SheetMeta> metas = sheetMetaService.listBy(sheet.getId());

        // Generate meta keywords.
        if (StringUtils.isNotEmpty(sheet.getMetaKeywords())) {
            model.addAttribute("meta_keywords", sheet.getMetaKeywords());
        } else {
            model.addAttribute("meta_keywords", optionService.getSeoKeywords());
        }

        // Generate meta description.
        if (StringUtils.isNotEmpty(sheet.getMetaDescription())) {
            model.addAttribute("meta_description", sheet.getMetaDescription());
        } else {
            model.addAttribute("meta_description",
                sheetService.generateDescription(sheet.getContent().getContent()));
        }

        // sheet and post all can use
        model.addAttribute("sheet", sheetDetailVo);
        model.addAttribute("post", sheetDetailVo);
        model.addAttribute("is_sheet", true);
        model.addAttribute("metas", sheetMetaService.convertToMap(metas));

        if (themeService.templateExists(
            ThemeService.CUSTOM_SHEET_PREFIX + sheet.getTemplate() + HaloConst.SUFFIX_FTL)) {
            return themeService.render(ThemeService.CUSTOM_SHEET_PREFIX + sheet.getTemplate());
        }
        return themeService.render("sheet");
    }
}
