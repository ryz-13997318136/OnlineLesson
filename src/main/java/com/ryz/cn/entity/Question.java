package com.ryz.cn.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "question")
public class Question  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="group_id")
	private String groupId;
	@Id
	@Column(name="question_id")
	private String questionId;
	
	@Column(name="title1")
	private String title1;
	
	@Column(name="title2")
	private String title2;
	
	@Column(name="option_a")
	private String optionA;
	
	@Column(name="option_b")
	private String optionB;
	
	@Column(name="option_c")
	private String optionC;
	
	@Column(name="option_d")
	private String optionD;
	
	@Column(name="option_e")
	private String optionE;
	
	@Column(name="option_f")
	private String optionF;
	
	@Column(name="answer")
	private String answer;
	
	@Column(name="tip")
	private String tip;
	
	@Column(name="point")
	private float point;
	
	@Column(name="type")
	private String type; 
	
	@Column(name="user_id")
	private String userId; 
	
	@Column(name="status")
	private String status;
	
	@Column(name="image_id")
	private String imageId;
	
	@Column(name="update_date")
	private Date updateDate;
	
	@Column(name="question_index")
	private Integer questionIndex;
	public Question() {
	}
	
	public Question(String groupId, String questionId, String title1, String title2, String optionA, String optionB,
			String optionC, String optionD, String optionE, String optionF, String answer, String tip,float point,
			String type,String userId,String status,String imageId,Date updateDate,Integer questionIndex) {
		super();
		this.groupId = groupId;
		this.questionId = questionId;
		this.title1 = title1;
		this.title2 = title2;
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionC = optionC;
		this.optionD = optionD;
		this.optionE = optionE;
		this.optionF = optionF;
		this.answer = answer;
		this.tip = tip;
		this.point = point;
		this.type = type;
		this.userId = userId;
		this.status = status;
		this.imageId = imageId;
		this.updateDate = updateDate;
		this.questionIndex = questionIndex;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getTitle1() {
		return title1;
	}
	public void setTitle1(String title1) {
		this.title1 = title1;
	}
	public String getTitle2() {
		return title2;
	}
	public void setTitle2(String title2) {
		this.title2 = title2;
	}
	public String getOptionA() {
		return optionA;
	}
	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}
	public String getOptionB() {
		return optionB;
	}
	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}
	public String getOptionC() {
		return optionC;
	}
	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}
	public String getOptionD() {
		return optionD;
	}
	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}
	public String getOptionE() {
		return optionE;
	}
	public void setOptionE(String optionE) {
		this.optionE = optionE;
	}
	public String getOptionF() {
		return optionF;
	}
	public void setOptionF(String optionF) {
		this.optionF = optionF;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}

	public float getPoint() {
		return point;
	}

	public void setPoint(float point) {
		this.point = point;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getQuestionIndex() {
		return questionIndex;
	}

	public void setQuestionIndex(Integer questionIndex) {
		this.questionIndex = questionIndex;
	}

	
	
	
}
