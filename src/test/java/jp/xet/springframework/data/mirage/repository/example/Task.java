/*
 * Copyright 2011-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.xet.springframework.data.mirage.repository.example;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.miragesql.miragesql.annotation.Column;
import com.miragesql.miragesql.annotation.Table;

@Table(name = "tasks")
@SuppressWarnings("serial")
public class Task implements Serializable {
	
	@Id
	@Column(name = "task_id")
	private String taskId;
	
	@Column(name = "task_name")
	private String taskName;
	
	@Column(name = "deadline")
	private Long deadline;
	
	
	public Task(String taskName, Long deadline) {
		this.taskId = UUID.randomUUID().toString();
		this.taskName = taskName;
		this.deadline = deadline;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Task))
			return false;
		Task task = (Task) o;
		return Objects.equals(getTaskId(), task.getTaskId()) &&
				Objects.equals(getTaskName(), task.getTaskName()) &&
				Objects.equals(getDeadline(), task.getDeadline());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getTaskId(), getTaskName(), getDeadline());
	}
	
	public String getTaskId() {
		return taskId;
	}
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String getTaskName() {
		return taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public Long getDeadline() {
		return deadline;
	}
	
	public void setDeadline(Long deadline) {
		this.deadline = deadline;
	}
}
