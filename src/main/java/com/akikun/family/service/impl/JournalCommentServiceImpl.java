package com.akikun.family.service.impl;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.akikun.family.exception.NotFoundException;
import com.akikun.family.model.entity.JournalComment;
import com.akikun.family.repository.JournalCommentRepository;
import com.akikun.family.repository.JournalRepository;
import com.akikun.family.service.JournalCommentService;
import com.akikun.family.service.OptionService;
import com.akikun.family.service.UserService;
import com.akikun.family.service.assembler.comment.JournalCommentAssembler;

/**
 * Journal comment service implementation.
 *
 * @author johnniang
 * @author guqing
 * @date 2019-04-25
 */
@Service
public class JournalCommentServiceImpl extends BaseCommentServiceImpl<JournalComment>
    implements JournalCommentService {

    private final JournalRepository journalRepository;

    public JournalCommentServiceImpl(JournalCommentRepository journalCommentRepository,
        OptionService optionService,
        UserService userService,
        ApplicationEventPublisher eventPublisher,
        JournalRepository journalRepository,
        JournalCommentAssembler journalCommentAssembler) {
        super(journalCommentRepository, optionService, userService, eventPublisher,
            journalCommentAssembler);
        this.journalRepository = journalRepository;
    }

    @Override
    public void validateTarget(@NonNull Integer journalId) {
        if (!journalRepository.existsById(journalId)) {
            throw new NotFoundException("查询不到该日志信息").setErrorData(journalId);
        }
    }
}
