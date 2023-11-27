package com.wemex.java21.demo.mapper;

import com.wemex.java21.demo.model.workflow.event.RegisterEvent;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.UUID;

@Mapper
public interface RegisterHistoryMapper {

    @Insert("""
            INSERT INTO
              register_history
              (id, party_id, party_name, representative_name, address, register_time, user_id, remarks)
            VALUES
              (#{id}, #{partyId}, #{partyName}, #{representativeName}, #{address}, #{registerTime}, ${userId}, #{remarks})
            
            """)
    void insert(RegisterEvent registerEvent);

    @Select("""
            SELECT *
            FROM
              register_history
            WHERE
              party_id = #{partyId}
            """)
    RegisterEvent selectByPartyId(UUID partyId);
}
