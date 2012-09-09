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
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteral;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.steps.PatternVariantBuilder;
import org.jbehave.core.steps.StepType;

import java.util.List;
import java.util.Set;

import static com.github.kumaraman21.intellijbehave.utility.StepTypeMappings.ANNOTATION_TO_STEP_TYPE_MAPPING;
import static com.google.common.collect.Sets.newHashSet;
import static org.apache.commons.lang.StringUtils.*;

public class StepDefinitionAnnotationConverter {

  public Set<StepDefinitionAnnotation> convertFrom(PsiAnnotation[] annotations) {

    Set<StepDefinitionAnnotation> stepDefinitionAnnotations = newHashSet();
    StepType stepType = null;

    for (PsiAnnotation annotation : annotations) {
      // Given, When, Then
      if (ANNOTATION_TO_STEP_TYPE_MAPPING.keySet().contains(annotation.getQualifiedName())) {
        stepType = ANNOTATION_TO_STEP_TYPE_MAPPING.get(annotation.getQualifiedName());
        String annotationText = getTextFromValue(annotation.getParameterList().getAttributes()[0].getValue());
        stepDefinitionAnnotations.addAll(getPatternVariants(stepType, annotationText, annotation));
      } else if (annotation.getQualifiedName().equals(Alias.class.getName())) {
        String annotationText = getTextFromValue(annotation.getParameterList().getAttributes()[0].getValue());
        stepDefinitionAnnotations.addAll(getPatternVariants(stepType, annotationText, annotation));
      } else if (annotation.getQualifiedName().equals(Aliases.class.getName())) {
        PsiElement[] values = annotation.getParameterList().getAttributes()[0].getValue().getChildren();
        for (PsiElement value : values) {
          if (value instanceof PsiLiteral) {
            String annotationText = getTextFromValue(value);
            stepDefinitionAnnotations.addAll(getPatternVariants(stepType, annotationText, annotation));
          }
        }
      }
    }
    return stepDefinitionAnnotations;
  }

  private Set<StepDefinitionAnnotation> getPatternVariants(StepType stepType, String annotationText, PsiAnnotation annotation) {
    Set<StepDefinitionAnnotation> stepDefinitionAnnotations = newHashSet();
    PatternVariantBuilder b = new PatternVariantBuilder(annotationText);
    for (String variant : b.allVariants()) {
      stepDefinitionAnnotations.add(new StepDefinitionAnnotation(stepType, variant, annotation));
    }
    return stepDefinitionAnnotations;
  }

  private static String getTextFromValue(PsiElement value) {
    return remove(removeStart(removeEnd(value.getText(), "\""), "\""), "\\");
  }
}
