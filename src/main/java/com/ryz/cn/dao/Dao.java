package com.ryz.cn.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.ryz.cn.entity.MyEntity;
import com.ryz.cn.entity.User;

@Repository
public class Dao implements DomainRepository{
	
	@Autowired
    private SessionFactory sessionFactory;
	
	private Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}
	@Override
	public Object load(Class claszz,Serializable id) {
		return (Class)getCurrentSession().load(claszz, id);
	}

	@Override
	public Object get(Class claszz,Serializable id) {
		return getCurrentSession().get(claszz, id);
	}

	@Override
	public List findAll() {
		return null;
	}

	@Override
	public void persist(Object entity) {
		getCurrentSession().persist(entity);
	}

	@Override
	public Serializable save(Object entity) {
		return getCurrentSession().save(entity);
	}

	@Override
	public void saveOrUpdate(Object entity) {
		getCurrentSession().saveOrUpdate(entity);
	}

	@Override
	public void delete(Object entity) {
		getCurrentSession().delete(entity);
	}

	@Override
	public void flush() {
		getCurrentSession().flush();
	}

	@Override
	public List<Map<String,Object>> query (String sql,String[] params){
		SQLQuery query = getCurrentSession().createSQLQuery(sql);  
        for(int i=0;i<params.length;i++){
        	query.setString(i, params[i]);
        }
        List<?> results = query.list();
        String[] arr = getColumn(sql);
        Map<String,Object> map = null;
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        for(int i=0;i<results.size();i++){
			Object[] row = (Object[])results.get(i);
			map = new HashMap<String,Object>();
			for(int j=0;j<arr.length;j++){
				map.put(arr[j], row[j]);
			}
			mapList.add(map);
		}
        
        return mapList;
	}
	public static String[] getColumn(String sql){
		String str1 = sql.substring(sql.indexOf("select")+7,sql.indexOf("from"));
		while(str1.indexOf(", ")!=-1||str1.indexOf(" ,")!=-1){
			str1 = str1.replace(", ", ",").replace(" ,", ",");
		}
		String[] arr = str1.split(",");
		int index;
		for(int i = 0;i<arr.length;i++){
			index = arr[i].indexOf(" ");
			if(index !=-1){
				arr[i] = arr[i].substring(arr[i].indexOf(".")+1);
				arr[i] = arr[i].substring(index).replace(" ", "");
			}
		}
		return arr;
	}
	@Override
	public List<Map<String, Object>> selectSql(String sql) {
		return this.query(sql,new String[]{});
	}
	@Override
	public List<Map<String,Object>> executeQuery(String sql,String[] params){
		@SuppressWarnings("deprecation")
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		try {
			ConnectionProvider cp =((SessionFactoryImplementor)sessionFactory).getConnectionProvider();  
		    Connection con =cp.getConnection();  
			PreparedStatement st = con.prepareStatement(sql);
			for(int i = 0;i<params.length;i++){
				st.setObject(i+1, params[i]);
			}
			ResultSet res = st.executeQuery();
			ResultSetMetaData rsmd = res.getMetaData();
			int count = rsmd.getColumnCount();
			String[] columns = new String[count];
			for(int j=0;j<count;j++){
				columns[j] = rsmd.getColumnName(j+1);
			}
			Map<String,Object> map = null;
			while(res.next()){
				map = new HashMap<String,Object>();
				for(String c : columns){
					map.put(c, res.getString(c));
				}
				mapList.add(map);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapList;
	}
	@Override
	public List<Map<String,Object>> executeQuery(String sql){
		return this.executeQuery(sql, new String[]{});
	}
	@Override
	public int executeUpdate(String sql,String[] params){
		try {
			@SuppressWarnings("deprecation")
			ConnectionProvider cp =((SessionFactoryImplementor)sessionFactory).getConnectionProvider();  
		    Connection con =cp.getConnection();  
			PreparedStatement st = con.prepareStatement(sql);
			for(int i = 0;i<params.length;i++){
				st.setObject(i+1, params[i]);
			}
			int res = st.executeUpdate();
			return res;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public int executeUpdate(String sql){
		return this.executeUpdate(sql, new String[]{});
	}
	@Override
	public List HqlQuery(Class clazz,String hql,Object[] params){
		Query query = getCurrentSession().createQuery(hql);
		for(int i=0;i<params.length;i++){
			query.setParameter(i, params[i]);
		}
		return query.list();
	}
	
	@Override
	public List HqlQuery(Class clazz, String hql, Object[] params, int pageSize, int index) {
		Query query = getCurrentSession().createQuery(hql);
		for(int i=0;i<params.length;i++){
			query.setParameter(i, params[i]);
		}
		if(pageSize==0){
			pageSize = 10;
		}
		query.setMaxResults(pageSize);
		int firstResult = (index - 1) * pageSize;
        query.setFirstResult(firstResult);
		return query.list();
	}
	
}
