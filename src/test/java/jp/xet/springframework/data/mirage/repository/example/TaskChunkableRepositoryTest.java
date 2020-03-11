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
 * ChunkableRepository を extends して、Slice の戻り値のメソッドを定義した Repository のテスト.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@Transactional
public class TaskChunkableRepositoryTest {
	
	@Autowired
	TaskChunkableRepository repos;
	
	
	@Test
	public void testFindSlice_ASC() {
		// setup
		Task task1 = repos.create(new Task("name1", null));
		Task task2 = repos.create(new Task("name2", 2L));
		Task task3 = repos.create(new Task("name4", 1L));
		Task task4 = repos.create(new Task("name3", 3L));
		
		Sliceable firstSliceable = new SliceRequest(0, Sort.Direction.ASC, 3);
		
		// exercise(最初のページ)
		Slice<Task> firstActual = repos.findSlice(firstSliceable);
		
		// verify
		assertThat(firstActual).hasSize(3);
		assertThat(firstActual.getContent().get(0)).isEqualTo(task1);
		assertThat(firstActual.getContent().get(1)).isEqualTo(task3);
		assertThat(firstActual.getContent().get(2)).isEqualTo(task2);
		assertThat(firstActual.hasNext()).isTrue();
		
		// 次のページ
		Sliceable nextSliceable = firstActual.nextSlice();
		Slice<Task> nextActual = repos.findSlice(nextSliceable);
		assertThat(nextActual).hasSize(1);
		assertThat(nextActual.getContent().get(0)).isEqualTo(task4);
		assertThat(nextActual.hasNext()).isFalse();
	}
	
	@Test
	public void testFindSlice_DESC() {
		// setup
		Task task1 = repos.create(new Task("name1", null));
		Task task2 = repos.create(new Task("name2", 2L));
		Task task3 = repos.create(new Task("name4", 1L));
		Task task4 = repos.create(new Task("name3", 3L));
		
		Sliceable firstSliceable = new SliceRequest(0, Sort.Direction.DESC, 3);
		
		// exercise(最初のページ)
		Slice<Task> firstActual = repos.findSlice(firstSliceable);
		
		// verify
		assertThat(firstActual).hasSize(3);
		assertThat(firstActual.getContent().get(0)).isEqualTo(task4);
		assertThat(firstActual.getContent().get(1)).isEqualTo(task2);
		assertThat(firstActual.getContent().get(2)).isEqualTo(task3);
		assertThat(firstActual.hasNext()).isTrue();
		
		// 次のページ
		Sliceable nextSliceable = firstActual.nextSlice();
		Slice<Task> nextActual = repos.findSlice(nextSliceable);
		assertThat(nextActual).hasSize(1);
		assertThat(nextActual.getContent().get(0)).isEqualTo(task1);
		assertThat(nextActual.hasNext()).isFalse();
	}
}
