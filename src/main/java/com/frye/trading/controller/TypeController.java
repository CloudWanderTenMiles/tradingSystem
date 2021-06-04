package com.frye.trading.controller;

import com.frye.trading.pojo.model.ParentType;
import com.frye.trading.pojo.model.Type;
import com.frye.trading.service.ParentTypeService;
import com.frye.trading.service.TypeService;
import com.frye.trading.utils.DataJsonUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TypeController {

    @Autowired
    ParentTypeService parentTypeService;
    @Autowired
    TypeService typeService;

    /**
     * 获取parent type list列表
     * @param page 页码
     * @param limit 每页行数
     * @param parentTypeName 父类型名称
     * @return json格式的数据
     */
    @RequestMapping(value = "/op/parentTypeList", method = RequestMethod.POST)
    @ResponseBody
    public String getParentTypeList(@Param("page") int page, @Param("limit") int limit,
                                    @Param("parentTypeName") String parentTypeName) {
        List<ParentType> parentTypeList = parentTypeService.getParentTypeList(page,limit,parentTypeName);
        int count = parentTypeService.getCount(parentTypeName);
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (count >= 0) {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("get data successfully");
            dataJsonUtils.setCount(count);
            dataJsonUtils.setData(parentTypeList);
        } else {
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg("get data failed");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 从父类型跳转到子类型页面
     * @param id parent type id
     * @param model model
     * @return typeList.html页面
     */
    @RequestMapping("/admin/typeList/{id}")
    public String toTypeList(@PathVariable("id") String id, Model model) {
        model.addAttribute("id",id);
        return "admin/typeList";
    }

    /**
     * 获取parentType的所有type的list
     * @param page 页码
     * @param limit 行数
     * @param typeName 类型名
     * @param parentTypeName 父类型名
     * @param parentTypeId 父类型id
     * @return type list的json数据
     */
    @RequestMapping(value = "/op/typeList", method = RequestMethod.POST)
    @ResponseBody
    public String getTypeList(@Param("page") int page, @Param("limit") int limit,@Param("typeName") String typeName,
                              @Param("parentTypeName") String parentTypeName, @Param("parentTypeId") String parentTypeId) {
        Map<String,String> params = new LinkedHashMap<>();
        params.put("typeName", typeName);
        params.put("parentTypeName", parentTypeName);
        params.put("parentTypeId", parentTypeId);
        List<Type> typeList = typeService.getTypeList(page, limit, params);
        int count = typeService.getCount(params);
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (count >= 0) {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("get data successfully");
            dataJsonUtils.setCount(count);
            dataJsonUtils.setData(typeList);
        } else {
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg("get data failed");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 获取所有的parent type
     * @return parent type list的json数据
     */
    @RequestMapping(value = "/op/getAllParentType", method = RequestMethod.POST)
    @ResponseBody
    public String getAllParentType() {
        List<ParentType> parentTypeList = parentTypeService.getAllParentType();
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        dataJsonUtils.setCode(200);
        dataJsonUtils.setData(parentTypeList);
        dataJsonUtils.setMsg("pull data successfully");
        return dataJsonUtils.toString();
    }

    /**
     * 根据parent type id获取所有的 type
     * @return type list的json数据
     */
    @RequestMapping(value = "/op/getAllTypeByPid", method = RequestMethod.POST)
    @ResponseBody
    public String getAllTypeByParentTypeId(@RequestBody String id) {
        List<Type> typeList = typeService.getAllTypeByParentTypeId(Integer.parseInt(id));
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        dataJsonUtils.setCode(200);
        dataJsonUtils.setData(typeList);
        dataJsonUtils.setMsg("pull data successfully");
        return dataJsonUtils.toString();
    }

    /**
     * 添加一个type
     * @param map type的属性值
     * @return 添加结果
     */
    @RequestMapping(value = "/op/typeAdd", method = RequestMethod.POST)
    @ResponseBody
    public String addType(@RequestBody Map<String, String> map) {
        Type type = new Type();
        type.setParentTypeId(Integer.parseInt(map.get("parentTypeId")));
        type.setTypeName(map.get("typeName"));
        type.setSort(Integer.parseInt(map.get("sort")));
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (typeService.addType(type) < 0) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("add type error! please check the data you enter.");
        } else {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("add type successfully!");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 删除type
     * @return json
     */
    @RequestMapping(value = "/op/typeDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteCustomer(@RequestBody String[] idList) {
        boolean error = false;
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        for (String id : idList){
            if (typeService.deleteType(id) < 0){
                error = true;
            }
        }
        if (error) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("delete error");
        } else {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("delete successfully!");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 跳转到修改页面
     * @param id 修改的type id
     * @param model 传递参数
     * @return 页面url
     */
    @RequestMapping( "/admin/typeUpdate/{id}")
    public String toUpdatePage(@PathVariable("id") String id, Model model) {
        model.addAttribute(id);
        return "/admin/typeUpdate";
    }

    /**
     * 从后台获取type的信息填充到form中
     * @param id type id
     * @return 返回type 的json
     */
    @RequestMapping(value = "/op/getType", method = RequestMethod.POST)
    @ResponseBody
    public String getType(@RequestBody String id) {
        Type type = typeService.getTypeById(id);
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (type != null) {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setData(type);
            dataJsonUtils.setMsg("Pull data successfully");
        } else {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("Error pulling data");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 更新type
     * @param map  type信息
     * @return     更改结果
     */
    @RequestMapping(value = "/op/typeUpdate", method = RequestMethod.POST)
    @ResponseBody
    public String updateType(@RequestBody Map<String, String> map) {
        Type type = new Type();
        type.setTypeId(Integer.parseInt(map.get("typeId")));
        type.setParentTypeId(Integer.parseInt(map.get("parentTypeId")));
        type.setTypeName(map.get("typeName"));
        type.setSort(Integer.parseInt(map.get("sort")));
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (typeService.updateType(type) < 0) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("update type error! please check the data you enter.");
        } else {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("update type successfully!");
        }
        return dataJsonUtils.toString();
    }
}
