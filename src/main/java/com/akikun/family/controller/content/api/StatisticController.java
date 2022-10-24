package com.akikun.family.controller.content.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.akikun.family.model.dto.StatisticDTO;
import com.akikun.family.model.dto.StatisticWithUserDTO;
import com.akikun.family.service.StatisticService;

/**
 * Content statistic controller.
 *
 * @author ryan0up
 * @date 2019-12-16
 */
@RestController("ApiContentStatisticController")
@RequestMapping("/api/content/statistics")
public class StatisticController {

    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping
    @ApiOperation("Gets blog statistics.")
    public StatisticDTO statistics() {
        return statisticService.getStatistic();
    }

    @GetMapping("user")
    @ApiOperation("Gets blog statistics with user")
    public StatisticWithUserDTO statisticsWithUser() {
        return statisticService.getStatisticWithUser();
    }
}
