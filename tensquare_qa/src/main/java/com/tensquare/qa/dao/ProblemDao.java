package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;


/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {
    /**
     * 最新回答
     *
     * @return
     */
    @Query(value = "SELECT * FROM tb_problem,tb_pl WHERE id=problemid AND labelid=? ORDER BY replytime DESC", nativeQuery = true)
    public Page<Problem> newlist(String labelId, Pageable pageable);

    /**
     * 热门回答
     *
     * @return
     */
    @Query(value = "SELECT * FROM tb_problem,tb_pl WHERE id=problemid AND labelid=? ORDER BY reply DESC", nativeQuery = true)
    public Page<Problem> hotlist(String labelId, Pageable pageable);

    /**
     * 等待回答
     *
     * @return
     */
    @Query(value = "SELECT * FROM tb_problem,tb_pl WHERE id=problemid AND labelid='1' AND reply=0 ORDER BY createtime DESC", nativeQuery = true)
    public Page<Problem> waitlist(String labelId, Pageable pageable);
}
