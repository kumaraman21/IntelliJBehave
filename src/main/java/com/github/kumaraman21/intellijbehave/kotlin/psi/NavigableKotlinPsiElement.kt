package com.github.kumaraman21.intellijbehave.kotlin.psi

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import org.jetbrains.kotlin.psi.KtElement

/**
 * Created by Rodrigo Quesada on 20/09/15.
 */
open class NavigableKotlinPsiElement(
        private val psiElement: PsiElement,
        private val ktElement: KtElement)
: PsiElement by psiElement {

    override fun getTextOffset(): Int = ktElement.textOffset

    override fun getParent(): PsiElement {
        val psiParent = psiElement.parent
        val ktParent = ktElement.parent

        return if (ktParent !is KtElement) {
            psiParent
        } else {
            when (psiParent) {
                is PsiAnnotation -> NavigableKotlinPsiAnnotation(psiParent, ktParent)
                is PsiMethod -> NavigableKotlinPsiMethod(psiParent, ktParent)
                else -> {
                    NavigableKotlinPsiElement(psiParent, ktParent)
                }
            }
        }
    }

    override fun getNavigationElement(): PsiElement {
        return this
    }
}