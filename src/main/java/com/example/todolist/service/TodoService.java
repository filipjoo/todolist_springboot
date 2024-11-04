package com.example.todolist.service;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.example.todolist.form.TodoData;

@Service
public class TodoService {
	private static final Logger logger = LoggerFactory.getLogger(TodoService.class);

	public boolean isValid(TodoData todoData, BindingResult result) {

		logger.debug("isValid() start");
		logger.debug("@ModelAttribute todoData：" + todoData);
		logger.debug("BindingResult result：" + result);

		boolean ans = true;

		// 件名が全角スペースだけで構成されているかチェック
		String title = todoData.getTitle();
		if (title != null && !title.equals("")) {
			boolean isAllDoubleSpace = true;

			for (int i = 0; i < title.length(); i++) {
				if (title.charAt(i) != '　') {
					isAllDoubleSpace = false;
					break;
				}
			}

			if (isAllDoubleSpace) {
				FieldError fieldError = new FieldError(result.getObjectName(), "title", "件名が全角スペースだけです"); // Formクラス名,フィールド名,エラーメッセージ
				result.addError(fieldError);
				ans = false;
			}
		}

		// 期限が過去日付かチェック
		String deadline = todoData.getDeadline();
		if (!deadline.equals("")) {
			LocalDate today = LocalDate.now();
			LocalDate deadlineDate = null;
			try {
				deadlineDate = LocalDate.parse(deadline);
				if (deadlineDate.isBefore(today)) {
					FieldError fieldError = new FieldError(result.getObjectName(), "deadline", "期限が過去日付です");
					result.addError(fieldError);
					ans = false;
				}
			} catch (DateTimeException e) {
				FieldError fieldError = new FieldError(result.getObjectName(), "deadline", "期限をyyyy-mm-dd形式で入力してください");
				result.addError(fieldError);
				ans = false;
			}
		}

		logger.debug("isValid() end");
		logger.debug("BindingResult result：" + result);
		logger.debug("ans：" + ans);
		return ans;
	}

}
