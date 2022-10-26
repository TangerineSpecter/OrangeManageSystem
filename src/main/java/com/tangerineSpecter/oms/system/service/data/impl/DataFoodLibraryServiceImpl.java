package com.tangerinespecter.oms.system.service.data.impl;

import com.tangerinespecter.oms.common.query.FoodLibraryQueryObject;
import com.tangerinespecter.oms.system.convert.data.FoodConvert;
import com.tangerinespecter.oms.system.domain.entity.DataFoodLibrary;
import com.tangerinespecter.oms.system.domain.vo.data.FoodLibraryInfoVo;
import com.tangerinespecter.oms.system.mapper.DataFoodLibraryMapper;
import com.tangerinespecter.oms.system.service.data.IDataFoodLibraryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataFoodLibraryServiceImpl implements IDataFoodLibraryService {

    private final DataFoodLibraryMapper foodLibraryMapper;

    @Override
    public List<DataFoodLibrary> list(FoodLibraryQueryObject qo) {
        return foodLibraryMapper.queryForPage(qo);
    }

    @Override
    public void insertInfo(FoodLibraryInfoVo vo) {
        foodLibraryMapper.insert(FoodConvert.INSTANCE.convert(vo));
    }

    @Override
    public void updateInfo(FoodLibraryInfoVo vo) {
        foodLibraryMapper.updateById(FoodConvert.INSTANCE.convert(vo));
    }

    @Override
    public void deleteInfo(Long id) {
        foodLibraryMapper.deleteById(id);
    }

    @Override
    public DataFoodLibrary detailInfo(Long id) {
        return foodLibraryMapper.selectById(id);
    }
}
