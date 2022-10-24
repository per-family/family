package com.akikun.family.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.akikun.family.model.entity.Option;
import com.akikun.family.repository.base.BaseRepository;

/**
 * Option repository.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-03-20
 */
public interface OptionRepository
    extends BaseRepository<Option, Integer>, JpaSpecificationExecutor<Option> {

    /**
     * Query option by key
     *
     * @param key key
     * @return Option
     */
    Optional<Option> findByKey(String key);

    /**
     * Delete option by key
     *
     * @param key key
     */
    void deleteByKey(String key);
}
