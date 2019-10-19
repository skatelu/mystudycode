package com.yjl.cache.service;

import com.yjl.cache.bean.Employee;
import com.yjl.cache.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 将方法的运行结果进行缓存，以后再要相同的数据，直接从缓存中获取，不用调用方法
     *
     * CacheManager管理多个Cache组件，对缓存的正真CRUD操作在Cache组件中，每一个缓存组件有自己唯一一个名字；
     *  @Cacheable几个属性
     *            cacheNames/ value ：指定缓存组件的名字
     *            key：缓存数据时使用的key；默认是使用方法参数的值
     *              key 也可以使用SpEL表达式
     * @param id
     * @return
     */
    @Cacheable(value = "emp")
    public Employee getEmp(Integer id){
        System.out.println("查询"+id+"号员工");
        Employee employee = employeeMapper.getEmployee(id);
        return employee;
    }
}
