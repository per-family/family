package com.akikun.family.model.params;

import lombok.Data;
import com.akikun.family.model.enums.JournalType;

/**
 * Journal query params.
 *
 * @author ryanwang
 * @date 2019/04/26
 */
@Data
public class JournalQuery {

    /**
     * Keyword.
     */
    private String keyword;

    private JournalType type;
}
