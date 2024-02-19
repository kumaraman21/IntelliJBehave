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
package com.github.kumaraman21.intellijbehave.codeInspector;

import com.github.kumaraman21.intellijbehave.highlighter.StorySyntaxHighlighter;
import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.github.kumaraman21.intellijbehave.resolver.StepPsiReference;
import com.github.kumaraman21.intellijbehave.service.JavaStepDefinition;
import com.github.kumaraman21.intellijbehave.utility.ParametrizedString;
import com.github.kumaraman21.intellijbehave.utility.ParametrizedString.StringToken;
import com.intellij.codeInspection.*;
import com.intellij.codeInspection.ex.ProblemDescriptorImpl;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.NotNull;

public class UndefinedStepInspection extends LocalInspectionTool {

    @NotNull
    @Override
    public String getShortName() {
        return "UndefinedStep";
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new PsiElementVisitor() {

            @Override
            public void visitElement(PsiElement psiElement) {
                super.visitElement(psiElement);

                if (!(psiElement instanceof JBehaveStep)) {
                    return;
                }

                JBehaveStep step = (JBehaveStep) psiElement;
                PsiReference[] references = step.getReferences();

                if (references.length != 1 || !(references[0] instanceof StepPsiReference)) {
                    return;
                }

                StepPsiReference reference = (StepPsiReference) references[0];
                JavaStepDefinition definition = reference.resolveToDefinition();

                if (definition == null) {
                    holder.registerProblem(step, "Step <code>#ref</code> is not defined");
                } else {
                    highlightParameters(step, definition, holder);
                }
            }
        };
    }


    private void highlightParameters(JBehaveStep step, JavaStepDefinition javaStepDefinition, ProblemsHolder holder) {
        String stepText = step.getStepText();

        String annotationText = javaStepDefinition.getAnnotationTextFor(stepText);
        ParametrizedString pString = new ParametrizedString(annotationText);

        int offset = step.getStepTextOffset();
        for (StringToken token : pString.tokenize(stepText)) {
            int length = token.getValue().length();
            if (token.isIdentifier()) {
                registerHiglighting(StorySyntaxHighlighter.TABLE_CELL, step, TextRange.from(offset, length), holder);
            }
            offset += length;
        }
    }

    private static void registerHiglighting(TextAttributesKey attributesKey,
                                            JBehaveStep step,
                                            TextRange range,
                                            ProblemsHolder holder) {
        final ProblemDescriptor descriptor = new ProblemDescriptorImpl(
                step, step, "", LocalQuickFix.EMPTY_ARRAY,
                ProblemHighlightType.INFORMATION, false, range, false, null,
                holder.isOnTheFly());
        descriptor.setTextAttributes(attributesKey);
        holder.registerProblem(descriptor);
    }
}

