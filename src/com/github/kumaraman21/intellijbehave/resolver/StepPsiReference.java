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

import com.github.kumaraman21.intellijbehave.parser.StepPsiElement;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;
import org.jbehave.core.steps.StepType;
import org.jetbrains.annotations.NotNull;

import static com.github.kumaraman21.intellijbehave.utility.ProjectFinder.getCurrentProject;
import static org.apache.commons.lang.StringUtils.substringAfter;
import static org.apache.commons.lang.StringUtils.trim;

public class StepPsiReference implements PsiReference {

  private StepPsiElement stepPsiElement;

  public StepPsiReference(StepPsiElement stepPsiElement) {
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

  @Override
  public PsiElement resolve() {
    final Project project = getCurrentProject();

    StepType stepType = stepPsiElement.getStepType();
    String stepText = trim(substringAfter(stepPsiElement.getText(), " "));

    StepAnnotationFinder stepAnnotationFinder = new StepAnnotationFinder(project, stepType, stepText);
    ProjectRootManager.getInstance(project).getFileIndex().iterateContent(stepAnnotationFinder);

    return stepAnnotationFinder.getMatchingAnnotation();
  }

private static class StepAnnotationFinder implements ContentIterator {

  private Project project;
  private StepType stepType;
  private String stepText;
  private PsiElement matchingAnnotation;

  private StepAnnotationFinder(Project project, StepType stepType, String stepText) {
    this.project = project;
    this.stepType = stepType;
    this.stepText = stepText;
  }

  @Override
  public boolean processFile(VirtualFile virtualFile) {
    PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
    if (psiFile instanceof PsiClassOwner) {
      PsiClass[] psiClasses = ((PsiClassOwner)psiFile).getClasses();

      for (PsiClass psiClass : psiClasses) {
        PsiMethod[] methods = psiClass.getMethods();

        for (PsiMethod method : methods) {
          PsiAnnotation[] annotations = method.getModifierList().getApplicableAnnotations();
          DeclaredAnnotationSet declaredAnnotations = new DeclaredAnnotationSet(annotations);

          matchingAnnotation = declaredAnnotations.getMatchingAnnotation(stepType, stepText);
          if(matchingAnnotation != null) {
            return false;
          }
        }
      }
    }

    return true;
  }

  public PsiElement getMatchingAnnotation() {
    return matchingAnnotation;
  }
}

  @NotNull
  @Override
  public String getCanonicalText() {
    return trim(stepPsiElement.getText());
  }

  @Override
  public PsiElement handleElementRename(String s) throws IncorrectOperationException {
    throw new IncorrectOperationException();
  }

  @Override
  public PsiElement bindToElement(@NotNull PsiElement psiElement) throws IncorrectOperationException {
    throw new IncorrectOperationException();
  }

  @Override
  public boolean isReferenceTo(PsiElement psiElement) {
    return psiElement instanceof StepPsiElement && Comparing.equal(resolve(), psiElement);
  }

  @NotNull
  @Override
  public Object[] getVariants() {
    return new Object[0];
  }

  @Override
  public boolean isSoft() {
    return false;
  }
}
