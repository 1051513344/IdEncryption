package com.laoxu.idjiami.controller;


import com.github.pagehelper.PageInfo;
import com.laoxu.idjiami.dataobject.dto.EmpDTO;
import com.laoxu.idjiami.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping("/emp")
    @ResponseBody
    public EmpDTO emp(){
        return empService.getEmp();
    }

    @GetMapping("/emps")
    @ResponseBody
    public List<EmpDTO> emps(){
        return empService.getEmps();
    }

    @GetMapping("/empsp")
    @ResponseBody
    public PageInfo<EmpDTO> empsp(){
        return empService.getEmpsByPage(1,2);
    }

    @GetMapping("/upemp/{id}")
    @ResponseBody
    public String upemp(@PathVariable("id") String id){
        empService.updateEmpById(id,"张大虾",30);
        return id;
    }

    @GetMapping("/delemp/{id}")
    @ResponseBody
    public String delemp(@PathVariable("id") String id){
        empService.deleteEmpById(id);
        return id;
    }

    @GetMapping("/addemp/{name}")
    @ResponseBody
    public String addemp(@PathVariable("name") String name){
        EmpDTO empDTO = new EmpDTO();
        empDTO.setName(name);
        empDTO.setAge(25);
        empService.addEmp(empDTO);
        return "ok";
    }
}
