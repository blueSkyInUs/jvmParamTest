package mybatis;

import domain.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by lesson on 2017/10/6.
 */

@Mapper
public interface TaskMapper {

    @Select("select * from task limit 1")
    Task getTask();
}
