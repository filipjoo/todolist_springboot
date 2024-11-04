package com.example.todolist.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.todolist.entity.Todo;
import com.example.todolist.form.TodoData;
import com.example.todolist.repository.TodoRepository;
import com.example.todolist.service.TodoService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class TodoListController {
	private static final Logger logger = LoggerFactory.getLogger(TodoListController.class);

	
	private final TodoRepository todoRepository;
	private final TodoService todoService;

	// 一覧画面表示
	@GetMapping("/todo")
	public ModelAndView showTodoList(ModelAndView mv) {
		logger.debug("showTodoList() start");
		logger.debug("mv：" + mv);
		mv.setViewName("todoList");
		List<Todo> todoList = todoRepository.findAll();
		mv.addObject("todoList", todoList);

		logger.debug("showTodoList() end");
		logger.debug("mv：" + mv);
		return mv;
	}

	// ToDo入力フォーム表示
	// 処理1
	// 起動：ToDo一覧画面（todoList.html）で[新規追加]ボタンがクリックされたとき
	// 処理内容：ToDo入力フォーム画面（todoForm.html）を表示する
	@GetMapping("/todo/create")
	public ModelAndView createTodo(ModelAndView mv) {
		logger.debug("GetcreateTodo() start");
		logger.debug("mv：" + mv);
		
		mv.setViewName("todoForm");

		mv.addObject("todoData", new TodoData());
		logger.debug("GetcreateTodo() end");
		logger.debug("mv：" + mv);
		return mv;
	}

	// ToDo追加処理
    // 処理2
	// 起動：ToDo入力画面（todoForm.html）で[登録]ボタンがクリックされたとき
	// 処理内容：入力されたToDoをDBに登録し、ToDo一覧画面（todoList.html）を表示する
	@PostMapping("/todo/create")
	public ModelAndView createTodo(
			@ModelAttribute @Validated TodoData todoData,
			BindingResult result, // TodoDataのバリデーションチェック結果
			ModelAndView mv) {
		logger.debug("PostcreateTodo() start");
		logger.debug("@ModelAttribute todoData：" + todoData);
		logger.debug("BindingResult result：" + result);
		logger.debug("mv：" + mv);

		// エラーチェック（バリデーション以外）
		boolean isValid = todoService.isValid(todoData, result);
		
		if (!result.hasErrors() && isValid) {
			// エラーなし
			Todo todo = todoData.toEntity();
			todoRepository.saveAndFlush(todo); //INSERT　INTO　todo(title,　importance,　urgency,　deadline,　done) VALUES(画面に入力された件名,　重要度,　緊急度,　期限,　完了)
			logger.debug("PostcreateTodo() end");
			logger.debug("mv：" + mv);
			return showTodoList(mv);
		} else {
			// エラーあり
			mv.setViewName("todoForm");
			logger.debug("PostcreateTodo() end");
			logger.debug("mv：" + mv);
			return mv;
		}
	}

	// ToDo一覧へ戻る（Todolist2で追加）
	// 処理3
	// 起動：ToDo入力画面（todoForm.html）で[キャンセル]ボタンがクリックされたとき
	// 処理内容：ToDo一覧画面（todoList.html）を表示する
	@PostMapping("/todo/cancel")
	public String cancel() {
		logger.debug("cancel() start&end");
		return "redirect:/todo";
	}

}