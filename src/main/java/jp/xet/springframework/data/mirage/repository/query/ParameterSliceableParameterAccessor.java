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

import org.springframework.data.repository.query.Parameters;
import org.springframework.data.repository.query.ParametersParameterAccessor;

import jp.xet.sparwings.spring.data.slice.Sliceable;

/**
 * Sliceable の ParameterAccessor.
 */
public class ParameterSliceableParameterAccessor extends ParametersParameterAccessor
		implements SliceableParameterAccessor {
	
	private final Parameters<?, ?> parameters;
	
	private final Object[] values;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param parameters Parameters
	 * @param values 引数の値
	 */
	public ParameterSliceableParameterAccessor(Parameters<?, ?> parameters, Object[] values) {
		super(parameters, values);
		this.parameters = parameters;
		this.values = values.clone();
	}
	
	@Override
	public Sliceable getSliceable() {
		if (parameters instanceof SliceableSupportedParameters) {
			SliceableSupportedParameters sliceableSupportedParameters = (SliceableSupportedParameters) parameters;
			if (sliceableSupportedParameters.hasSliceableParameter() == false) {
				return null;
			}
			return (Sliceable) values[sliceableSupportedParameters.getSliceableIndex()];
		}
		return null;
	}
}
