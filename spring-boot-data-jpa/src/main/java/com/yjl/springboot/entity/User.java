package com.yjl.springboot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 使用JPA注解配置映射关系
 *  @Entity 这个注解告诉JPA 这是一个实体类（他是和数据表映射的类）
 *  @Table(name = "tbl_user") 来制定和哪个数据表对应  如果省略默认表明就是类名
 */
@Table(name = "tbl_user")
@Entity
public class User {
    @Id // 这是主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) //z自增主键
    private Integer id;

    @Column(name = "last_name") //这是和数据表对应的一个列  name = "last_name" 表示对应哪个列
    private String lastName;
    @Column // 省略 默认就是属性名
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
