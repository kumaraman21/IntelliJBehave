/*
 * Copyright 2011-12 Aman Kumar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.kumaraman21.intellijbehave.resolver;

import static com.google.common.cache.CacheBuilder.newBuilder;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.commons.lang.StringUtils.trim;

import java.util.List;

import org.jbehave.core.steps.StepType;
import org.jetbrains.annotations.NotNull;

import com.github.kumaraman21.intellijbehave.parser.StepPsiElement;
import com.github.kumaraman21.intellijbehave.utility.ScanUtils;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.util.IncorrectOperationException;

public class StepPsiReference implements PsiReference {

	private final StepPsiElement stepPsiElement;
	private static final CacheLoader<CacheKey, StepDefinitionAnnotation> loader = new CacheLoader<CacheKey, StepDefinitionAnnotation>() {
		@Override
		public StepDefinitionAnnotation load(final CacheKey cacheKey) throws Exception {
			final StepAnnotationFinder stepAnnotationFinder = new StepAnnotationFinder(cacheKey.getStepPsiElement());
			ScanUtils.iterateInContextOf(cacheKey.getStepPsiElement(), stepAnnotationFinder);
			return stepAnnotationFinder.getMatchingAnnotation();
		}
	};
	private static final LoadingCache<CacheKey, StepDefinitionAnnotation> cache
			= newBuilder()
			.expireAfterWrite(5, SECONDS)
			.build(loader);

	public StepPsiReference(final StepPsiElement stepPsiElement) {
		this.stepPsiElement = stepPsiElement;
	}

	@Override
	public PsiElement getElement() {
		return stepPsiElement;
	}

	@Override
	public TextRange getRangeInElement() {
		return TextRange.from(0, stepPsiElement.getTextLength());
	}

	public StepDefinitionAnnotation stepDefinitionAnnotation() {
		try {
			return cache.getUnchecked(key(stepPsiElement));
		} catch (final Exception e) {
			return null;
		}
	}

	private CacheKey key(final StepPsiElement stepPsiElement) {
		return new CacheKey(stepPsiElement);
	}

	@Override
	public PsiElement resolve() {
		StepDefinitionAnnotation stepDefinitionAnnotation = stepDefinitionAnnotation();
		if (stepDefinitionAnnotation == null) {
			return null;
		}
		return stepDefinitionAnnotation.getAnnotation();
	}

	private static final boolean useVariants = false;

	@NotNull
	@Override
	public Object[] getVariants() {

		if (useVariants) {
			StepType stepType = stepPsiElement.getStepType();
			String actualStepPrefix = stepPsiElement.getActualStepPrefix();

			StepSuggester stepSuggester = new StepSuggester(stepType, actualStepPrefix, stepPsiElement);
			ScanUtils.iterateInContextOf(stepPsiElement, stepSuggester);

			return stepSuggester.getSuggestions().toArray();
		} else {
			return new Object[0];
		}
	}

	private static class StepSuggester extends StepDefinitionIterator {

		private final List<String> suggestions = newArrayList();
		private final String actualStepPrefix;

		public StepSuggester(StepType stepType, String actualStepPrefix, StepPsiElement storyRef) {
			super(stepType, storyRef);
			this.actualStepPrefix = actualStepPrefix;
		}

		@Override
		public boolean processStepDefinition(StepDefinitionAnnotation stepDefinitionAnnotation) {
			suggestions.add(actualStepPrefix + " " + stepDefinitionAnnotation.getAnnotationText());
			return true;
		}

		public List<String> getSuggestions() {
			return suggestions;
		}
	}

	@NotNull
	@Override
	public String getCanonicalText() {
		return trim(stepPsiElement.getText());
	}

	@Override
	public PsiElement handleElementRename(final String s) throws IncorrectOperationException {
		throw new IncorrectOperationException();
	}

	@Override
	public PsiElement bindToElement(@NotNull final PsiElement psiElement) throws IncorrectOperationException {
		throw new IncorrectOperationException();
	}

	@Override
	public boolean isReferenceTo(final PsiElement psiElement) {
		return psiElement instanceof StepPsiElement && Comparing.equal(resolve(), psiElement);
	}

	@Override
	public boolean isSoft() {
		return false;
	}

	private class CacheKey {
		private final StepType stepType;
		private final String stepText;
		private final StepPsiElement stepPsiElement;

		public CacheKey(final StepPsiElement stepPsiElement) {
			this.stepType = stepPsiElement.getStepType();
			this.stepText = stepPsiElement.getStepText();
			this.stepPsiElement = stepPsiElement;
		}

		public StepPsiElement getStepPsiElement() {
			return stepPsiElement;
		}

		@Override
		public boolean equals(final Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}

			final CacheKey cacheKey = (CacheKey) o;

			if (stepText != null ? !stepText.equals(cacheKey.stepText) : cacheKey.stepText != null) {
				return false;
			}
			if (stepType != cacheKey.stepType) {
				return false;
			}

			return true;
		}

		@Override
		public int hashCode() {
			int result = stepType != null ? stepType.hashCode() : 0;
			result = 31 * result + (stepText != null ? stepText.hashCode() : 0);
			return result;
		}
	}
}
