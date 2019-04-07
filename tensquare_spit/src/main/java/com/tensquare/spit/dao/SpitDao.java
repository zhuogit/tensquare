package com.tensquare.spit.dao;

import com.tensquare.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface SpitDao extends MongoRepository<Spit, String> {

    /**
     * 根据上级id查询
     *
     * @param parentId
     * @return
     */
    public Page<Spit> findByParentid(String parentId, Pageable pageable);

}
