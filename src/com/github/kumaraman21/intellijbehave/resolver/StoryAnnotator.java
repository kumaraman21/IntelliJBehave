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

import static com.github.kumaraman21.intellijbehave.utility.ParametrizedString.StringToken;

import org.jetbrains.annotations.NotNull;

import com.github.kumaraman21.intellijbehave.parser.StepPsiElement;
import com.github.kumaraman21.intellijbehave.utility.ParametrizedString;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;

public class StoryAnnotator implements Annotator {
	@Override
	public void annotate(@NotNull final PsiElement psiElement, @NotNull final AnnotationHolder annotationHolder) {
		if (!(psiElement instanceof StepPsiElement)) {
			return;
		}

		final StepPsiElement stepPsiElement = (StepPsiElement) psiElement;
		final StepDefinitionAnnotation annotationDef = stepPsiElement.getReference().stepDefinitionAnnotation();
		if (annotationDef == null) {
			annotationHolder.createErrorAnnotation(psiElement, "No definition found for the step");
		} else {
			annotateParameters(stepPsiElement, annotationDef, annotationHolder);
		}
	}

	private void annotateParameters(final StepPsiElement stepPsiElement,
									final StepDefinitionAnnotation annotation,
									final AnnotationHolder annotationHolder) {
		final String stepText = stepPsiElement.getStepText();
		final String annotationText = annotation.getAnnotationText();
		final ParametrizedString pString = new ParametrizedString(annotationText);

		int offset = stepPsiElement.getTextOffset();
		for (final StringToken token : pString.tokenize(stepText)) {
			final int length = token.getValue().length();
			if (token.isIdentifier()) {
				annotationHolder.createInfoAnnotation(
						TextRange.from(offset, length), "Parameter");
			}
			offset += length;
		}
	}
}
