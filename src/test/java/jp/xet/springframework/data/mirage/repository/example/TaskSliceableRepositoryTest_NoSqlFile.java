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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;

import jp.xet.sparwings.spring.data.exceptions.InvalidSliceableException;
import jp.xet.sparwings.spring.data.slice.Slice;
import jp.xet.sparwings.spring.data.slice.SliceRequest;
import jp.xet.sparwings.spring.data.slice.Sliceable;

import jp.xet.springframework.data.mirage.repository.TestConfiguration;

/**
 * SliceableRepository の sql ファイル無しのテスト.
 *
 * repository の
 * - {interface 名}.sql
 * - {interface 名}_{メソッド名}.sql
 * のファイルが存在しないので、baseSelect.sql を使用。
 *
 * <pre>
 * SELECT *
 * FROM {Entity の table の name}
 * ORDER BY {Entity の @Id の column 名} {SliceRequest#getDirection}
 * LIMIT {SliceRequest#getOffset},
 * {SliceRequest#getMaxContentSize + 1}
 * </pre>
 * 
 * の SQL が発行されること
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@Transactional
public class TaskSliceableRepositoryTest_NoSqlFile {
	
	@Autowired
	TaskSliceableRepository repos;
	
	
	@Test
	public void testFindAll_ASC() {
		// setup
		Task task1 = repos.create(createTask("1-1", "name1", null));
		Task task2 = repos.create(createTask("1-2", "name2", 2L));
		Task task3 = repos.create(createTask("1-3", "name4", 1L));
		Task task4 = repos.create(createTask("2-1", "name3", 3L));
		
		Sliceable firstSliceable = new SliceRequest(0, Sort.Direction.ASC, 3);
		
		// exercise(最初のページ)
		// task_id 項目でソートを行う
		Slice<Task> firstActual = repos.findAll(firstSliceable);
		
		// verify
		assertThat(firstActual).hasSize(3);
		assertThat(firstActual.getContent().get(0)).isEqualTo(task1);
		assertThat(firstActual.getContent().get(1)).isEqualTo(task2);
		assertThat(firstActual.getContent().get(2)).isEqualTo(task3);
		assertThat(firstActual.hasNext()).isTrue();
		
		// 次のページ
		Sliceable nextSliceable = firstActual.nextSlice();
		Slice<Task> nextActual = repos.findAll(nextSliceable);
		assertThat(nextActual).hasSize(1);
		assertThat(nextActual.getContent().get(0)).isEqualTo(task4);
		assertThat(nextActual.hasNext()).isFalse();
	}
	
	@Test
	public void testFindAll_DESC() {
		// setup
		Task task1 = repos.create(createTask("1-1", "name1", null));
		Task task2 = repos.create(createTask("2-1", "name2", 2L));
		Task task3 = repos.create(createTask("2-2", "name4", 1L));
		Task task4 = repos.create(createTask("2-3", "name3", 3L));
		
		Sliceable firstSliceable = new SliceRequest(0, Sort.Direction.DESC, 3);
		
		// exercise(最初のページ)
		// task_id 項目でソートを行う
		Slice<Task> firstActual = repos.findAll(firstSliceable);
		
		// verify
		assertThat(firstActual).hasSize(3);
		assertThat(firstActual.getContent().get(0)).isEqualTo(task4);
		assertThat(firstActual.getContent().get(1)).isEqualTo(task3);
		assertThat(firstActual.getContent().get(2)).isEqualTo(task2);
		assertThat(firstActual.hasNext()).isTrue();
		
		// 次のページ
		Sliceable nextSliceable = firstActual.nextSlice();
		Slice<Task> nextActual = repos.findAll(nextSliceable);
		assertThat(nextActual).hasSize(1);
		assertThat(nextActual.getContent().get(0)).isEqualTo(task1);
		assertThat(nextActual.hasNext()).isFalse();
	}
	
	@Test
	public void testFindAll_InvalidSliceable() {
		// setup
		Sliceable sliceable = new SliceRequest(1, Sort.Direction.ASC, 1001);
		
		// exercise
		Throwable actual = catchThrowable(() -> repos.findAll(sliceable));
		
		// verify
		assertThat(actual).isInstanceOfSatisfying(InvalidSliceableException.class,
				e -> assertThat(e.getMessage()).isEqualTo("Cannot get elements beyond 2000."));
	}
	
	private Task createTask(String taskId, String taskName, Long deadline) {
		Task task = new Task(taskName, deadline);
		task.setTaskId(taskId);
		return task;
	}
}
