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
package com.github.kumaraman21.intellijbehave.parser;

import com.github.kumaraman21.intellijbehave.resolver.StepPsiReference;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiReference;
import org.jbehave.core.steps.StepType;
import org.jetbrains.annotations.NotNull;

import static org.apache.commons.lang.StringUtils.*;

public class StepPsiElement extends ASTWrapperPsiElement {
  private StepType stepType;

  public StepPsiElement(@NotNull ASTNode node, StepType stepType) {
    super(node);
    this.stepType = stepType;
  }

  @Override
  @NotNull
  public StepPsiReference getReference() {
    return new StepPsiReference(this);
  }

  public StepType getStepType() {
    return stepType;
  }

  public String getStepText() {
    return trim(substringAfter(getText(), " "));
  }

  public String getActualStepPrefix() {
    return substringBefore(getText(), " ");
  }

    public int getStepTextOffset() {
        return getText().indexOf(' ') + 1;
    }
}
