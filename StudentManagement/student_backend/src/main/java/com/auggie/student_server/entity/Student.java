package com.auggie.student_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data //注在类上，提供类的get、set、equals、hashCode、canEqual、toString方法
@NoArgsConstructor  //注在类上，提供类的无参构造
@AllArgsConstructor //注在类上，提供类的全参构造
@Alias("Student")
public class Student {
    private Integer sid;
    private String sname;
    private String password;
}
