package com.auggie.student_server.controller;

import com.auggie.student_server.entity.Code;
import com.auggie.student_server.entity.Student;
import com.auggie.student_server.service.StudentService;
import com.auggie.student_server.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.auggie.student_server.utils.SecurityUtils.matchesPassword;

@RestController
@CrossOrigin("*")
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    HttpSession se;

    @PostMapping("/validCode")
    public boolean checkCode(@RequestBody Code code) {
        System.out.println("正在校验验证码是否正确");
        if (code.getInputCode().toLowerCase().equals(code.getOrCode().toLowerCase())) {
            //System.out.println("验证正确");
            return true;
        }
        //System.out.println("验证错误");
        //System.out.println(code.getInputCode());
        //System.out.println(code.getOrCode());
        return false;
    }


    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
        public boolean addStudent(@RequestBody Student student, HttpSession session) {
        System.out.println("正在保存学生对象" + student);
        // 加密密码
        student.setPassword(SecurityUtils.encodePassword(student.getPassword()));
        return studentService.save(student);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public boolean login(@RequestBody Student student, HttpSession httpSession) {
        System.out.println("正在验证学生登陆 " + student);
        Student s = studentService.findById(student.getSid());
        // 判断密码是否可用
        if (s == null || !matchesPassword(student.getPassword(), s.getPassword())) {
            return false;
        } else {
            httpSession.setAttribute("user", "student");
            se = httpSession;
            return true;
        }
    }

    @RequestMapping(value = "/findBySearch", method = RequestMethod.POST)
    public List<Student> findBySearch(@RequestBody Student student, HttpSession session) {
        Integer fuzzy = (student.getPassword() == null) ? 0 : 1;
        return studentService.findBySearch(student.getSid(), student.getSname(), fuzzy);
    }

    @GetMapping("/findById/{sid}")
    public Student findById(@PathVariable("sid") Integer sid) {
        System.out.println("正在查询学生信息 By id " + sid);
        return studentService.findById(sid);
    }

    @RequestMapping(value = "/findByPage/{page}/{size}", method = RequestMethod.GET)
    public List<Student> findByPage(@PathVariable("page") int page, @PathVariable("size") int size, HttpSession session) {
        HttpSession httpSession = se;
        System.out.println("查询学生列表分页 " + page + " " + size);
        return studentService.findByPage(page, size);
    }

    @GetMapping("/getLength")
    public Integer getLength() {
        return studentService.getLength();
    }

    @GetMapping("/deleteById/{sid}")
    public boolean deleteById(@PathVariable("sid") int sid,HttpServletRequest request) {
        if(request != null && request.getServletContext().getAttribute("admin").equals("admin")){
            System.out.println("正在删除学生 sid：" + sid);
            return studentService.deleteById(sid);
        }
        return false;
    }

    @RequestMapping(value = "/updateStudent", method = RequestMethod.POST)
    public boolean updateStudent(@RequestBody Student student, HttpServletRequest request) {
        HttpSession httpSession = se;
        if (httpSession != null  && httpSession.getAttribute("user").equals("student")) {
            // 加密密码
            student.setPassword(SecurityUtils.encodePassword(student.getPassword()));
            return studentService.updateById(student);
        }else if(request != null && request.getServletContext().getAttribute("admin").equals("admin")) {
            // 加密密码
            student.setPassword(SecurityUtils.encodePassword(student.getPassword()));
            return studentService.updateById(student);
        }else {
            return false;
        }
    }


    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public boolean logoutStudent() {
        HttpSession httpSession = se;
        System.out.println(httpSession.getId());
        System.out.println(httpSession.getAttribute("user"));
        if (httpSession.getAttribute("user").equals("student")) {
            System.out.println("退出");
            se.invalidate();
            return true;
        }else {
            System.out.println("fail");
            return false;
        }
    }
}
