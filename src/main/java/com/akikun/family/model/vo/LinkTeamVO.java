package com.akikun.family.model.vo;

import java.util.List;
import lombok.Data;
import lombok.ToString;
import com.akikun.family.model.dto.LinkDTO;

/**
 * Link team vo.
 *
 * @author ryanwang
 * @date 2019/3/22
 */
@Data
@ToString
public class LinkTeamVO {

    private String team;

    private List<LinkDTO> links;
}
