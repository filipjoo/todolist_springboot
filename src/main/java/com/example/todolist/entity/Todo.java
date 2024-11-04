package com.example.todolist.entity;

import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


	// テーブル定義に沿って、Entityクラスを作成する
	// TABLE todo
	// (
	// id SERIAL PRIMARY KEY,
	// title TEXT,
	// importance INTEGER, -- 0,1
	// urgency INTEGER, -- 0,1
	// deadline DATE,
	// done TEXT -- "N" or "Y"
	// );

	@Entity
	@Table(name = "todo")
	@Data
	public class Todo {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id")
		private Integer id;

		@Column(name = "title")
		private String title;

		@Column(name = "importance")
		private Integer importance;

		@Column(name = "urgency")
		private Integer urgency;

		@Column(name = "deadline")
		private Date deadline;

		@Column(name = "done")
		private String done;
	}

