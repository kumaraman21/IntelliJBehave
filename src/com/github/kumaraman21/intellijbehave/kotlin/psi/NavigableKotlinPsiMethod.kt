package com.github.kumaraman21.intellijbehave.kotlin.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import org.jetbrains.kotlin.psi.JetElement

/**
 * Created by Rodrigo Quesada on 20/09/15.
 */
class NavigableKotlinPsiMethod(
        private val psiMethod: PsiMethod,
        jetElement: JetElement
) : PsiMethod by psiMethod {

    private val navigableKotlinPsiElement = NavigableKotlinPsiElement(psiMethod, jetElement)

    override fun getTextOffset(): Int = navigableKotlinPsiElement.textOffset

    override fun getParent(): PsiElement = navigableKotlinPsiElement.parent

    override fun getNavigationElement(): PsiElement = navigableKotlinPsiElement.navigationElement
}