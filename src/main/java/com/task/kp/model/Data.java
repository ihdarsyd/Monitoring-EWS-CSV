package com.task.kp.model;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2271208542829555393L;

	private String date_dt;

	private String id;

	private List<String> hashtag;

	private String sentiment;

	private String ori_post;

	private String username;

	private String id_user;

	private String post;

	private String type;

	private String typeMedia;

	private String url;

	private String emotion;

	private String stance;

	private List<String> listCategory;

	private String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<String> getListCategory() {
		return listCategory;
	}

	public void setListCategory(List<String> listCategory) {
		this.listCategory = listCategory;
	}

	public String getDate_dt() {
		return date_dt;
	}

	public void setDate_dt(String date_dt) {
		this.date_dt = date_dt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSentiment() {
		return sentiment;
	}

	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}

	public String getOri_post() {
		return ori_post;
	}

	public void setOri_post(String ori_post) {
		this.ori_post = ori_post;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getId_user() {
		return id_user;
	}

	public void setId_user(String id_user) {
		this.id_user = id_user;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmotion() {
		return emotion;
	}

	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}

	public String getStance() {
		return stance;
	}

	public void setStance(String stance) {
		this.stance = stance;
	}

	public List<String> getHashtag() {
		return hashtag;
	}

	public void setHashtag(List<String> hashtag) {
		this.hashtag = hashtag;
	}

	public String getTypeMedia() {
		return typeMedia;
	}

	public void setTypeMedia(String typeMedia) {
		this.typeMedia = typeMedia;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	

	public String toString1() {
		return "ClassPojo [date_dt = " + date_dt + ", id = " + id + ", sentiment = " + sentiment + ", ori_post = "
				+ ori_post + ", username = " + username + ", id_user = " + id_user + ", post = " + post + ", type = "
				+ type + ", emotion = " + emotion + "]";
	}
	
	@Override
	public String toString() {
		return ( id + ", " + id_user + ", "  + username + ", " + date_dt+ ", " + sentiment + ", "+ emotion+ ", "+ hashtag.toString().replace(",", ". ") + ", "
			   + post.replace(",", ". ").replace("\n", " ").replace("\t", " ") + ", "+ type + ", "+ typeMedia + ", " + url.replace(",", ". "));
	}


}

