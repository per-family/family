package com.akikun.family.service.impl;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.akikun.family.exception.BadRequestException;
import com.akikun.family.exception.NotFoundException;
import com.akikun.family.model.entity.Sheet;
import com.akikun.family.model.entity.SheetComment;
import com.akikun.family.repository.SheetCommentRepository;
import com.akikun.family.repository.SheetRepository;
import com.akikun.family.service.OptionService;
import com.akikun.family.service.SheetCommentService;
import com.akikun.family.service.UserService;
import com.akikun.family.service.assembler.comment.SheetCommentAssembler;

/**
 * Sheet comment service implementation.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-04-24
 */
@Service
public class SheetCommentServiceImpl extends BaseCommentServiceImpl<SheetComment>
    implements SheetCommentService {

    private final SheetRepository sheetRepository;

    public SheetCommentServiceImpl(SheetCommentRepository sheetCommentRepository,
        OptionService optionService,
        UserService userService,
        ApplicationEventPublisher eventPublisher,
        SheetRepository sheetRepository,
        SheetCommentAssembler sheetCommentAssembler) {
        super(sheetCommentRepository, optionService, userService, eventPublisher,
            sheetCommentAssembler);
        this.sheetRepository = sheetRepository;
    }

    @Override
    public void validateTarget(@NonNull Integer sheetId) {
        Sheet sheet = sheetRepository.findById(sheetId)
            .orElseThrow(() -> new NotFoundException("查询不到该页面的信息").setErrorData(sheetId));

        if (sheet.getDisallowComment()) {
            throw new BadRequestException("该页面已被禁止评论").setErrorData(sheetId);
        }
    }
}
