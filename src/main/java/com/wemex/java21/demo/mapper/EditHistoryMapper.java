package com.wemex.java21.demo.mapper;

import com.wemex.java21.demo.entity.EditHistory;
import com.wemex.java21.demo.model.workflow.event.TempSaveEvent;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.UUID;

@Mapper
public interface EditHistoryMapper {
    @Insert("""
            INSERT INTO
              edit_history
              (id, party_id, party_name, representative_name, address, saved_time, user_id)
            VALUES
              (#{id}, #{partyId}, #{partyName}, #{representativeName}, #{address}, #{savedTime}, #{userId})
            """
    )
    void insert(TempSaveEvent tempSaveEvent);

    @Select("""
            SELECT
              id
            , party_id
            , party_name
            , representative_name
            , address
            , saved_time
            , user_id
             FROM edit_history WHERE party_id = #{partyId}
            """)
    List<EditHistory> selectByPartyId(UUID partyId);
}
