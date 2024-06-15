package com.auggie.student_server.controller;

import com.auggie.student_server.entity.Code;
import com.auggie.student_server.entity.Teacher;
import com.auggie.student_server.service.TeacherService;
import com.auggie.student_server.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import static com.auggie.student_server.utils.SecurityUtils.matchesPassword;

@RestController
@CrossOrigin("*")
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    HttpSession se;

    @PostMapping("/validCode")
    public boolean checkCode(@RequestBody Code code) {
        System.out.println("正在校验111验证码是否正确");
        if (code.getInputCode().toLowerCase().equals(code.getOrCode().toLowerCase())) {
            return true;
        }
        return false;
    }

    @PostMapping("/addTeacher")
    public boolean addTeacher(@RequestBody Teacher teacher) {
        HttpSession httpSession = se;
        if (httpSession.getAttribute("admin").equals("admin")) {
            teacher.setPassword(SecurityUtils.encodePassword(teacher.getPassword()));
            return teacherService.save(teacher);
        }else {
            return false;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public boolean login(@RequestBody Teacher teacher, HttpSession httpSession,HttpServletRequest request) {
        Teacher t = teacherService.findById(teacher.getTid());
        // 判断密码是否可用
        if (t == null || !matchesPassword(teacher.getPassword(), t.getPassword())) {
            return false;
        } else {
            if(t.getTname().equals("admin")){
                httpSession.setAttribute("user", "teacher");
                httpSession.setAttribute("admin", "admin");
                request.getServletContext().setAttribute("admin","admin");
                System.out.println("session: " + httpSession.getAttribute("user"));
                se = httpSession;
                return true;
            }else{
                httpSession.setAttribute("user", "teacher");
                System.out.println("session: " + httpSession.getAttribute("user"));
                se = httpSession;
                return true;
            }
        }
    }

    @GetMapping("/findById/{tid}")
    public Teacher findById(@PathVariable("tid") Integer tid) {
        HttpSession httpSession = se;
        if (httpSession.getAttribute("user").equals("teacher")) {
            System.out.println("正在查询教师信息 By id " + tid);
            return teacherService.findById(tid);
        }else{
            return null;
        }
    }

    @PostMapping("/findBySearch")
    public List<Teacher> findBySearch(@RequestBody Map<String, String> map) {
        return teacherService.findBySearch(map);
    }

    @GetMapping("/deleteById/{tid}")
    public boolean deleteById(@PathVariable("tid") int tid) {
        HttpSession httpSession = se;
        if (httpSession.getAttribute("admin").equals("admin")) {
            return teacherService.deleteById(tid);
        } else {
            return false;
        }
    }

    @PostMapping("/updateTeacher")
    public boolean updateTeacher(@RequestBody Teacher teacher) {
        HttpSession httpSession = se;
        if (httpSession.getAttribute("user").equals("teacher")){
            System.out.println("更新 " + teacher);
            // 加密密码
            teacher.setPassword(SecurityUtils.encodePassword(teacher.getPassword()));
            return teacherService.updateById(teacher);
        }else {
            return false;
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public boolean logoutStudent() {
        HttpSession httpSession = se;
        if (httpSession.getAttribute("user").equals("teacher")){
            System.out.println(se.getAttribute("user"));
            se.invalidate();
            return true;
        }else {
            return false;
        }
    }
}
