package com.github.kumaraman21.intellijbehave.kotlin.psi

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.JetElement

/**
 * Created by Rodrigo Quesada on 20/09/15.
 */
class NavigableKotlinPsiAnnotation(
        private val psiAnnotation: PsiAnnotation,
        jetElement: JetElement
) : PsiAnnotation by psiAnnotation {

    private val navigableKotlinPsiElement = NavigableKotlinPsiElement(psiAnnotation, jetElement)

    override fun getTextOffset(): Int = navigableKotlinPsiElement.textOffset

    override fun getParent(): PsiElement = navigableKotlinPsiElement.parent

    override fun getNavigationElement(): PsiElement = navigableKotlinPsiElement.navigationElement
}