package com.akikun.family.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import com.akikun.family.model.dto.BaseCommentDTO;
import com.akikun.family.model.dto.post.BasePostMinimalDTO;

/**
 * PostComment list with post vo.
 *
 * @author johnniang
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class SheetCommentWithSheetVO extends BaseCommentDTO {

    private BasePostMinimalDTO sheet;
}
