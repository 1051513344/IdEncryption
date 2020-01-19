package com.laoxu.idjiami.service;

import com.github.pagehelper.PageInfo;
import com.laoxu.idjiami.dataobject.dto.EmpDTO;

import java.util.List;

public interface EmpService {
    EmpDTO getEmp();
    List<EmpDTO> getEmps();
    PageInfo<EmpDTO> getEmpsByPage(Integer pageNum, Integer pageSize);
    void updateEmpById(String id, String name, Integer age);
    void deleteEmpById(String id);
    void addEmp(EmpDTO empDTO);
}
