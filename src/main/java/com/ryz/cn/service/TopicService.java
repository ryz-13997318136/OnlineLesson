package com.ryz.cn.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ryz.cn.entity.Question;
import com.ryz.cn.model.TopicForm;

@Service
@Transactional
public class TopicService extends BaseService {
	
	public void saveTopic(TopicForm topicForm){
		String topicType = topicForm.getTopicType();
		Question question = new Question();
		question.setGroupId(topicForm.getGroupNo());
		if(topicForm.getTopicId()!=null&&!topicForm.getTopicId().equals("")){
			question.setQuestionId(topicForm.getTopicId());
		}else{
			question.setQuestionId(getUUID());
		}
		
		question.setType(topicType);
		question.setTitle1(topicForm.getTopicName());
		question.setTitle2(topicForm.getSubTopicName());
		
		question.setOptionA(topicForm.getOptionA());
		question.setOptionB(topicForm.getOptionB());
		question.setOptionC(topicForm.getOptionC());
		question.setOptionD(topicForm.getOptionD());
		question.setOptionE(topicForm.getOptionE());
		question.setOptionF(topicForm.getOptionF());
		question.setTip(topicForm.getTopicTip());
		question.setPoint(topicForm.getPoint());
		question.setUserId(topicForm.getUserId());
		question.setQuestionIndex(topicForm.getIndex());
		question.setStatus("1");
		question.setUpdateDate(new Date());
		if("1".equals(topicType)){ // 简单题
			question.setAnswer(topicForm.getAnswer1());
		}
		if("2".equals(topicType)){ // 选择题
			question.setAnswer(topicForm.getAnswer2());
		}
		if("3".equals(topicType)){ // 简单题
			question.setAnswer(topicForm.getAnswer3());
		}
		if("4".equals(topicType)){ // 判别题
			question.setAnswer(topicForm.getAnswer4());
		}
		question.setImageId(topicForm.getImageId());
		dao.saveOrUpdate(question);
	}
	
	public Map<String,Object> search(long userId,String groupNo,String topicType,String keyword,String point,int pageSize, int index){
		String sql = "select topic from Question topic where topic.userId = ?   and topic.status <> 0";
		List<Object> params = new ArrayList<Object>();
		params.add(String.valueOf(userId));
		if(!isBlank(groupNo)){
			sql += " and topic.groupId=? ";
			params.add(groupNo);
		}
		if(!isBlank(topicType)&&!"0".equals(topicType)){
			sql += " and topic.type=? ";
			params.add(topicType);
		}
		if(!isBlank(point)){
			sql += " and topic.point=? ";
			params.add(Float.valueOf(point));
		}
		if(!isBlank(keyword)){
			sql += " and (topic.title1 like '%'||?||'%' or topic.title2 like '%'||?||'%' or topic.tip like '%'||?||'%' or topic.answer like '%'||?||'%') ";
			params.add(keyword);
			params.add(keyword);
			params.add(keyword);
			params.add(keyword);
		}
		sql +=" order by topic.questionIndex ";
		int count = dao.HqlQuery(Question.class, sql, params.toArray()).size();
		List<Question> list =  (List<Question>)dao.HqlQuery(Question.class, sql, params.toArray(),pageSize,index);
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("count", count);
		res.put("list", list);
		return res;
	}
	
	public List<Question> searchByGroup(long userId,String groupNo){
		String sql = "select topic from Question topic where userId = ? and groupId=?  and status <> 0 order by questionIndex ";
		return (List<Question>)dao.HqlQuery(Question.class, sql, new Object[]{String.valueOf(userId),groupNo});
	}
	
	public void delete(long userId,String topicId){
		Question question = (Question) dao.get(Question.class, topicId);
		if(question == null){
			throw new RuntimeException("Topic not exist");
		}
		question.setStatus("0");
	}
	public List<Question> searchById(String questionId){
		String sql = "select topic from Question topic where questionId = ?  and status <> 0";
		List<Question> list = (List<Question>)dao.HqlQuery(Question.class, sql, new Object[]{questionId});
		if(list.size()==0){
			throw new RuntimeException("Topic not exist");
		}
		return list;
	}
	
	public void saveTopic( List<TopicForm> topicFormList ){
		for(TopicForm topicForm : topicFormList ){
			saveTopic(topicForm);
		}


	}
}
