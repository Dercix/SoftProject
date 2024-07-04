package com.auggie.student_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("Code")
public class Code {
    private String inputCode;
    private String orCode;

    public String getInputCode() {
        return inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    public String getOrCode() {
        return orCode;
    }

    public void setOrCode(String orCode) {
        this.orCode = orCode;
    }
}
