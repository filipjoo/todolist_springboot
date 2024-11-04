package com.example.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.todolist.entity.Todo;

@Repository
//JpaRepository<対象エンティティ, 対象エンティティの@Idプロパティの型> 
public interface TodoRepository extends JpaRepository<Todo, Integer> {
	
}
