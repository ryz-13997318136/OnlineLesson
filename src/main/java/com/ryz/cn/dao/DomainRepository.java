package com.ryz.cn.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ryz.cn.entity.MyEntity;

public interface DomainRepository<T,PK extends Serializable>{
    T load(Class<T> classs,PK id);

    T get(Class<T> classs,PK id);

    List<T> findAll();

    void persist(T entity);

    PK save(T entity);

    void saveOrUpdate(T entity);

    void delete(Object entity);

    void flush();
    
    List<Map<String,Object>> query(String sql,String[] params);
    public List<Map<String, Object>> selectSql(String sql) ;
    public List<Map<String,Object>> executeQuery(String sql,String[] params);
    public List<Map<String,Object>> executeQuery(String sql);
    public int executeUpdate(String sql,String[] params);
    public int executeUpdate(String sql);
    public List HqlQuery(Class clazz,String hql,Object[] params);
    public List HqlQuery(Class clazz,String hql,Object[] params,int pageSize,int index);
}
