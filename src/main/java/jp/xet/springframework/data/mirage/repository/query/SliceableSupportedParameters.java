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

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.data.repository.query.DefaultParameters;
import org.springframework.data.repository.query.Parameters;

import jp.xet.sparwings.spring.data.slice.Sliceable;

/**
 * Sliceable の パラメータ.
 */
public class SliceableSupportedParameters
		extends Parameters<SliceableSupportedParameters, SliceableSupportedParameter> {
	
	/** Sliceable パラメータの index. */
	private int sliceableIndex;
	
	
	/**
	 * Creates a new {@link DefaultParameters} instance from the given {@link Method}.
	 * 
	 * @param method must not be {@literal null}.
	 */
	public SliceableSupportedParameters(Method method) {
		super(method);
		List<Class<?>> types = Arrays.asList(method.getParameterTypes());
		sliceableIndex = types.indexOf(Sliceable.class);
	}
	
	private SliceableSupportedParameters(List<SliceableSupportedParameter> originals) {
		super(originals);
		
		for (int i = 0; i < originals.size(); i++) {
			SliceableSupportedParameter original = originals.get(i);
			if (original.isSliceable()) {
				sliceableIndex = i;
				return;
			}
		}
		sliceableIndex = -1;
	}
	
	/**
	 * Returns the index of the {@link Sliceable} {@link Method} parameter if available. Will return {@literal -1} if there
	 * is no {@link Sliceable} argument in the {@link Method}'s parameter list.
	 * 
	 * @return the sliceableIndex
	 */
	public int getSliceableIndex() {
		return sliceableIndex;
	}
	
	/**
	 * Returns whether the method the {@link Parameters} was created for contains a {@link Sliceable} argument.
	 * 
	 * @return Sliceable のパラメータ指定が存在する場合、true
	 */
	public boolean hasSliceableParameter() {
		return sliceableIndex != -1;
	}
	
	@Override
	protected SliceableSupportedParameters createFrom(List<SliceableSupportedParameter> parameters) {
		return new SliceableSupportedParameters(parameters);
	}
	
	@Override
	protected SliceableSupportedParameter createParameter(MethodParameter parameter) {
		return new SliceableSupportedParameter(parameter);
	}
}
