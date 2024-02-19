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

import com.intellij.psi.PsiAnnotation;
import org.jbehave.core.steps.StepType;

public class StepDefinitionAnnotation {
    private final StepType stepType;
    private final String annotationText;
    private final PsiAnnotation annotation;

    public StepDefinitionAnnotation(StepType stepType, String annotationText, PsiAnnotation annotation) {
        this.stepType = stepType;
        this.annotationText = annotationText;
        this.annotation = annotation;
    }

    public String getAnnotationText() {
        return annotationText;
    }

    public StepType getStepType() {
        return stepType;
    }

    public PsiAnnotation getAnnotation() {
        return annotation;
    }
}
