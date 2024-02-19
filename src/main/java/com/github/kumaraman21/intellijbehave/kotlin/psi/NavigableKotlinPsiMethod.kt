package com.github.kumaraman21.intellijbehave.kotlin.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import org.jetbrains.kotlin.psi.KtElement

/**
 * Created by Rodrigo Quesada on 20/09/15.
 */
class NavigableKotlinPsiMethod(
        private val psiMethod: PsiMethod,
        ktElement: KtElement
) : PsiMethod by psiMethod {

    private val navigableKotlinPsiElement = NavigableKotlinPsiElement(psiMethod, ktElement)

    override fun getTextOffset(): Int = navigableKotlinPsiElement.textOffset

    override fun getParent(): PsiElement = navigableKotlinPsiElement.parent

    override fun getNavigationElement(): PsiElement = navigableKotlinPsiElement.navigationElement
}