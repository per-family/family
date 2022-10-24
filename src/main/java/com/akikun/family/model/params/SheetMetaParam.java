package com.akikun.family.model.params;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import com.akikun.family.model.entity.SheetMeta;

/**
 * Sheet meta param.
 *
 * @author ryanwang
 * @author ikaisec
 * @date 2019-08-04
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SheetMetaParam extends BaseMetaParam<SheetMeta> {
}
