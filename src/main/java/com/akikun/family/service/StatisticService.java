package com.akikun.family.service;

import com.akikun.family.model.dto.StatisticDTO;
import com.akikun.family.model.dto.StatisticWithUserDTO;

/**
 * Statistic service interface.
 *
 * @author ryanwang
 * @date 2019-12-16
 */
public interface StatisticService {

    /**
     * Get blog statistic.
     *
     * @return statistic dto.
     */
    StatisticDTO getStatistic();

    /**
     * Get blog statistic with user info.
     *
     * @return statistic with user info dto.
     */
    StatisticWithUserDTO getStatisticWithUser();
}
