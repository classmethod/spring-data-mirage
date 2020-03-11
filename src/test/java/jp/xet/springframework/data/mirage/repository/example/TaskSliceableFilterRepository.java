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

import org.springframework.data.repository.query.Param;

import jp.xet.sparwings.spring.data.repository.SliceableRepository;
import jp.xet.sparwings.spring.data.repository.WritableRepository;
import jp.xet.sparwings.spring.data.slice.Slice;
import jp.xet.sparwings.spring.data.slice.Sliceable;

public interface TaskSliceableFilterRepository
		extends WritableRepository<Task, String>, SliceableRepository<Task, String> {
	
	/**
	 * filter 条件込みで Slice 取得.
	 * 
	 * <p>
	 * - 期限 From 指定時、期限 From <= tasks.deadline
	 * - 期限 To 指定時、tasks.deadline <= 期限 To
	 * を指定します。
	 * </p>
	 * @param deadlineFrom 期限From
	 * @param deadlineTo 期限To
	 * @param sliceable Slice 条件
	 * @return 検索結果
	 */
	Slice<Task> withFilter(@Param("deadlineFrom") Long deadlineFrom,
			@Param("deadlineTo") Long deadlineTo, Sliceable sliceable);
}
