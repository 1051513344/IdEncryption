package com.laoxu.idjiami.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laoxu.idjiami.dataobject.dto.EmpDTO;
import com.laoxu.idjiami.dataobject.entity.Emp;
import com.laoxu.idjiami.mapper.EmpMapper;
import com.laoxu.idjiami.service.EmpService;
import com.laoxu.idjiami.util.IdHandler;
import com.laoxu.idjiami.util.ModelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;


    @Override
    public EmpDTO getEmp() {
        Emp emp = new Emp();
        emp.setId(1);
        emp.setAge(15);
        emp.setName("張工");
        return ModelUtils.toModel(emp, EmpDTO.class);
    }

    @Override
    public List<EmpDTO> getEmps() {
        List<Emp> emps = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Emp emp = new Emp();
            emp.setId(i);
            emp.setAge(15);
            emp.setName("張工" + i);
            emps.add(emp);
        }
        return ModelUtils.toModels(emps,EmpDTO.class);
    }

    @Override
    public PageInfo<EmpDTO> getEmpsByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Emp> emps = empMapper.selectAll();
        PageInfo<Emp> pageInfo = new PageInfo<>(emps);
        return ModelUtils.copyPager(pageInfo,EmpDTO.class);
    }

    @Override
    public void updateEmpById(String id, String name, Integer age) {
        //id解密
        Integer i = IdHandler.idDecryptToInteger(id);
        Emp emp = new Emp();
        emp.setId(i);
        emp.setName(name);
        emp.setAge(30);
        //i为真实id
        empMapper.updateByPrimaryKey(emp);
    }

    @Override
    public void deleteEmpById(String id) {
        //id解密
        Integer i = IdHandler.idDecryptToInteger(id);
        //i为真实id
        empMapper.deleteByPrimaryKey(i);
    }

    @Override
    public void addEmp(EmpDTO empDTO) {
        empMapper.insertSelective(ModelUtils.toModel(empDTO,Emp.class));
    }
}
