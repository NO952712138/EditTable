package com.tfx.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfx.dao.CidsiDataDao;
import com.tfx.domain.CidsiData;

@Service
public class CidsiDataService {

	@Autowired
	CidsiDataDao cidsiDataDao;
	public List<CidsiData> queryData(int rowOffSet,int pageSize)
	{
		return cidsiDataDao.queryData(rowOffSet,pageSize);
	}
	public int getNumOfRecord()
	{
		return cidsiDataDao.getNumOfRecord();
	}
	public int insertData(CidsiData record)
	{
		return cidsiDataDao.insertData(record);
	}
}
