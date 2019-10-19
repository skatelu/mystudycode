package mybatis;

import java.util.Map;

public interface Mapper {

    Map<String,Object> selectBlog();

    void insertContact(Map map);

}
