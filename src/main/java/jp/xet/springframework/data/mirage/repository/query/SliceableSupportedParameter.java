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

import org.springframework.core.MethodParameter;
import org.springframework.data.repository.query.Parameter;

import jp.xet.sparwings.spring.data.slice.Sliceable;

/**
 * Sliceable をサポートするパラメータ.
 */
public class SliceableSupportedParameter extends Parameter {
	
	private MethodParameter parameter;
	
	
	public SliceableSupportedParameter(MethodParameter parameter) {
		super(parameter);
		this.parameter = parameter;
	}
	
	@Override
	public boolean isSpecialParameter() {
		return SupportedParameterUtils.isSpecialParameter(parameter);
	}
	
	/**
	 * Returns whether the {@link Parameter} is a {@link Sliceable} parameter.
	 * 
	 * @return Sliceable のパラメータを保持している場合、true
	 */
	boolean isSliceable() {
		return Sliceable.class.isAssignableFrom(getType());
	}
}
