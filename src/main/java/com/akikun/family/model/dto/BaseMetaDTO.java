package com.akikun.family.model.dto;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import com.akikun.family.model.dto.base.OutputConverter;
import com.akikun.family.model.entity.BaseMeta;

/**
 * Base meta Dto.
 *
 * @author ryanwang
 * @date 2019-12-10
 */
@Data
@ToString
@EqualsAndHashCode
public class BaseMetaDTO implements OutputConverter<BaseMetaDTO, BaseMeta> {
    private Long id;

    private Integer postId;

    private String key;

    private String value;

    private Date createTime;
}
