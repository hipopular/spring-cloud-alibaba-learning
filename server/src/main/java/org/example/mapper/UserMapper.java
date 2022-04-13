package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.domain.Test;

/**
 * Created by huen on 2021/10/25 14:45
 */
@Mapper
public interface UserMapper extends BaseMapper<Test> {


    @Select("select BILL_NO from  pss_interface_store_flow where  TENANT_ID = #{tenantId} and CINEMA_UID = #{cinemaUid} AND ID = #{id} LIMIT 1")
    String query(@Param("tenantId") String tenantId, @Param("cinemaUid") String cinemaUid, @Param("id") long id);

    @Select("select ms.NAME  from  mer_mer_321566 mm left join mer_spu_321566 ms on ms.UID = mm.UID where  mm.SHORTHAND_CODE = #{code} limit 1")
    String sharding(@Param("code") String code);

    @Select("select ms.NAME  from  mer_mer_339158 mm left join mer_spu_339158 ms on ms.UID = mm.UID where  mm.SHORTHAND_CODE = #{code} limit 1")
    String sharding2(@Param("code") String code);
}
