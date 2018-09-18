package com.tfx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.tfx.domain.CidsiData;

@Repository
public interface CidsiDataDao {
	public int insertData(@Param("record")CidsiData record);
	public List<CidsiData> queryData(@Param("rowOffSet")int rowOffSet,@Param("pageSize")int pageSize);
	public int getNumOfRecord();
}

