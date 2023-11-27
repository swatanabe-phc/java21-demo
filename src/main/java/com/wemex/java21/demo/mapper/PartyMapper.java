package com.wemex.java21.demo.mapper;

import com.wemex.java21.demo.entity.PartyEntity;
import com.wemex.java21.demo.model.party.Party;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.UUID;

@Mapper
public interface PartyMapper {

    @Insert("""
            INSERT INTO
              party
              (id, name, representative_name, address, state)
            VALUES
              (#{id}, #{name}, #{representativeName}, #{address}, #{state})
            """)
    void insert(Party party);

    @Select("""
            SELECT * FROM party WHERE id = #{id}
            """)
    PartyEntity selectById(UUID id);

    @Update("""
            MERGE INTO party
            USING (VALUES (
              #{id},
              #{name},
              #{representativeName},
              #{address},
              #{state}
            )) AS new_values (id, name, representative_name, address, state)
            ON party.id = new_values.id
            WHEN MATCHED THEN
              UPDATE SET
                name = new_values.name,
                representative_name = new_values.representative_name,
                address = new_values.address,
                state = new_values.state
            WHEN NOT MATCHED THEN
              INSERT (id, name, representative_name, address, state)
              VALUES (new_values.id, new_values.name, new_values.representative_name, new_values.address, new_values.state)
            """
    )
    void update(Party party);

}
