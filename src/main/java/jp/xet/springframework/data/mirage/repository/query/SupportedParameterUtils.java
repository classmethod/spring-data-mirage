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
package jp.xet.springframework.data.mirage.repository.query;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import jp.xet.sparwings.spring.data.chunk.Chunkable;
import jp.xet.sparwings.spring.data.slice.Sliceable;

/**
 * SupportedParameter 用の Utils.
 */
public class SupportedParameterUtils {
	
	/** 
	 * 特別なパラメータと判定する class List.
	 * 
	 * <p>新しい概念を追加する場合、追加してください。</p>
	 */
	private static final List<Class<?>> TYPES =
			Arrays.asList(Pageable.class, Sort.class, Chunkable.class, Sliceable.class);
	
	
	/**
	 * Returns whether the parameter is a special parameter.
	 * @param methodParameter MethodParameter
	 * @return 特別なパラメータの場合、true
	 */
	public static boolean isSpecialParameter(MethodParameter methodParameter) {
		return TYPES.contains(methodParameter.getParameterType());
	}
}
