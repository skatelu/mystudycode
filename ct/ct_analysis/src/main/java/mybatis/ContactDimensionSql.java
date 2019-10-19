package mybatis;

import kv.base.BaseDimension;

import java.util.List;
import java.util.Map;

public interface ContactDimensionSql {

    List<String> selectID(BaseDimension baseDimension);

    void insertContactDimension(BaseDimension baseDimension);

}
