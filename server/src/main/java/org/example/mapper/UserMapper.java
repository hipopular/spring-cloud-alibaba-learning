package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.domain.Test;

/**
 * Created by huen on 2021/10/25 14:45
 */
@Mapper
public interface UserMapper extends BaseMapper<Test> {
}
