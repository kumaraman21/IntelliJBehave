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
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.parsers.StepMatcher;
import org.jbehave.core.parsers.StepPatternParser;
import org.jbehave.core.steps.StepType;

import java.util.Set;

import static com.github.kumaraman21.intellijbehave.utility.StepTypeMappings.ANNOTATION_TO_STEP_TYPE_MAPPING;
import static com.google.common.collect.Sets.newHashSet;
import static org.apache.commons.lang.StringUtils.*;

public class DeclaredAnnotationSet {

  private Set<DeclaredAnnotation> declaredAnnotations = newHashSet();

  public DeclaredAnnotationSet(PsiAnnotation[] annotations) {
    StepType stepType = null;

    for (PsiAnnotation annotation : annotations) {
      // Given, When, Then
      if(ANNOTATION_TO_STEP_TYPE_MAPPING.keySet().contains(annotation.getQualifiedName())) {
        stepType = ANNOTATION_TO_STEP_TYPE_MAPPING.get(annotation.getQualifiedName());
        String annotationText = getTextFromValue(annotation.getParameterList().getAttributes()[0].getValue());
        declaredAnnotations.add(new DeclaredAnnotation(stepType, annotationText, annotation));
      }
      else if(annotation.getQualifiedName().equals(Alias.class.getName())) {
        String annotationText = getTextFromValue(annotation.getParameterList().getAttributes()[0].getValue());
        declaredAnnotations.add(new DeclaredAnnotation(stepType, annotationText, annotation));
      }
      else if(annotation.getQualifiedName().equals(Aliases.class.getName())) {

        PsiElement[] values = annotation.getParameterList().getAttributes()[0].getValue().getChildren();
        for (PsiElement value : values) {
          if(value instanceof PsiLiteral) {
            String annotationText = getTextFromValue(value);
            declaredAnnotations.add(new DeclaredAnnotation(stepType, annotationText, annotation));
          }
        }
      }
    }
  }

  public PsiElement getMatchingAnnotation(StepType stepType, String stepText) {
    StepPatternParser stepPatternParser = new RegexPrefixCapturingPatternParser();

    for (DeclaredAnnotation declaredAnnotation : declaredAnnotations) {
      if(stepType == declaredAnnotation.getStepType()) {
        StepMatcher stepMatcher = stepPatternParser.parseStep(stepType, declaredAnnotation.getAnnotationText());

        if(stepMatcher.matches(stepText)) {
         return declaredAnnotation.getAnnotation();
        }
      }
    }

    return null;
  }

  private static String getTextFromValue(PsiElement value) {
    return remove(removeStart(removeEnd(value.getText(), "\""), "\""), "\\");
  }

  private static class DeclaredAnnotation {

    private StepType stepType;
    private String annotationText;
    private PsiAnnotation annotation;

    public DeclaredAnnotation(StepType stepType, String annotationText, PsiAnnotation annotation) {
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
}
