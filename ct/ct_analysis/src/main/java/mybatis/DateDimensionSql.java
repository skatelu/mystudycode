package mybatis;

import kv.base.BaseDimension;

import java.util.List;
import java.util.Map;

public interface DateDimensionSql {

    List<String> selectID(BaseDimension baseDimension);

    void insertDateDimension(BaseDimension baseDimension);
}
