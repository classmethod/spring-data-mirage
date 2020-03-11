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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;

import jp.xet.sparwings.spring.data.slice.Slice;
import jp.xet.sparwings.spring.data.slice.SliceRequest;
import jp.xet.sparwings.spring.data.slice.Sliceable;

import jp.xet.springframework.data.mirage.repository.TestConfiguration;

/**
 * repository の
 * - {interface 名}.sql
 * 
 * の sql ファイルのテスト
 */
@Transactional
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class TaskSliceableFilterRepositoryTest {
	
	@Autowired
	TaskSliceableFilterRepository repos;
	
	
	@Test
	public void testWithFilter_ASC() {
		// setup
		repos.create(new Task("name1", 1L));
		repos.create(new Task("name2", 2L));
		Task task3 = repos.create(new Task("name3", 3L));
		Task task4 = repos.create(new Task("name4", 4L));
		Task task5 = repos.create(new Task("name5", 5L));
		Task task6 = repos.create(new Task("name6", 5L));
		
		Sliceable firstSliceable = new SliceRequest(0, Sort.Direction.ASC, 3);
		
		// exercise(最初のページ)
		Slice<Task> firstActual = repos.withFilter(3L, null, firstSliceable);
		
		// verify
		assertThat(firstActual).hasSize(3);
		assertThat(firstActual.getContent().get(0)).isEqualTo(task3);
		assertThat(firstActual.getContent().get(1)).isEqualTo(task4);
		assertThat(firstActual.getContent().get(2)).isEqualTo(task5);
		assertThat(firstActual.hasNext()).isTrue();
		
		// 次のページ
		Sliceable nextSliceable = firstActual.nextSlice();
		Slice<Task> nextActual = repos.withFilter(3L, null, nextSliceable);
		assertThat(nextActual).hasSize(1);
		assertThat(nextActual.getContent().get(0)).isEqualTo(task6); // task_name のソート順でこの順番で取れる
		assertThat(nextActual.hasNext()).isFalse();
	}
	
	@Test
	public void testWithFilter_DESC() {
		// setup
		Task task1 = repos.create(new Task("name1", 1L));
		Task task2 = repos.create(new Task("name2", 2L));
		Task task3 = repos.create(new Task("name3", 4L));
		Task task4 = repos.create(new Task("name4", 4L));
		repos.create(new Task("name5", 5L));
		repos.create(new Task("name6", 6L));
		
		Sliceable firstSliceable = new SliceRequest(0, Sort.Direction.DESC, 3);
		
		// exercise(最初のページ)
		Slice<Task> firstActual = repos.withFilter(null, 4L, firstSliceable);
		
		// verify
		assertThat(firstActual).hasSize(3);
		assertThat(firstActual.getContent().get(0)).isEqualTo(task4);
		assertThat(firstActual.getContent().get(1)).isEqualTo(task3); // task_name のソート順でこの順番で取れる
		assertThat(firstActual.getContent().get(2)).isEqualTo(task2);
		assertThat(firstActual.hasNext()).isTrue();
		
		// 次のページ
		Sliceable nextSliceable = firstActual.nextSlice();
		Slice<Task> nextActual = repos.withFilter(null, 4L, nextSliceable);
		assertThat(nextActual).hasSize(1);
		assertThat(nextActual.getContent().get(0)).isEqualTo(task1);
		assertThat(nextActual.hasNext()).isFalse();
	}
	
	@Test
	public void testWithFilter() {
		// setup
		repos.create(new Task("name1", 1L));
		Task task2 = repos.create(new Task("name2", 2L));
		Task task3 = repos.create(new Task("name3", 4L));
		Task task4 = repos.create(new Task("name4", 4L));
		repos.create(new Task("name5", 5L));
		repos.create(new Task("name6", 6L));
		
		Sliceable firstSliceable = new SliceRequest(0, Sort.Direction.ASC, 3);
		
		// exercise
		Slice<Task> firstActual = repos.withFilter(2L, 4L, firstSliceable);
		
		// verify
		assertThat(firstActual).hasSize(3);
		assertThat(firstActual.getContent().get(0)).isEqualTo(task2);
		assertThat(firstActual.getContent().get(1)).isEqualTo(task3);
		assertThat(firstActual.getContent().get(2)).isEqualTo(task4);
		assertThat(firstActual.hasNext()).isFalse();
	}
}
