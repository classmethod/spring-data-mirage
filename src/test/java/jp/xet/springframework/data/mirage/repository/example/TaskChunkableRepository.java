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

import jp.xet.sparwings.spring.data.repository.ChunkableRepository;
import jp.xet.sparwings.spring.data.repository.WritableRepository;
import jp.xet.sparwings.spring.data.slice.Slice;
import jp.xet.sparwings.spring.data.slice.Sliceable;

/**
 * ChunkableRepository を extends して、Slice の戻り値のメソッドを定義した Repository.
 */
public interface TaskChunkableRepository extends WritableRepository<Task, String>, ChunkableRepository<Task, String> {
	
	/**
	 * Slice 取得.
	 * @param sliceable Slice 条件
	 * @return 検索結果
	 */
	Slice<Task> findSlice(Sliceable sliceable);
}
