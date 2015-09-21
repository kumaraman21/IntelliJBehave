package com.github.kumaraman21.intellijbehave.kotlin.psi

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import org.jetbrains.kotlin.psi.JetElement

/**
 * Created by Rodrigo Quesada on 20/09/15.
 */
open class NavigableKotlinPsiElement(
        private val psiElement: PsiElement,
        private val jetElement: JetElement)
: PsiElement by psiElement {

    override fun getTextOffset(): Int = jetElement.textOffset

    override fun getParent(): PsiElement {
        val psiParent = psiElement.parent
        val jetParent = jetElement.parent

        return if (jetParent !is JetElement) {
            psiParent
        } else {
            when (psiParent) {
                is PsiAnnotation -> NavigableKotlinPsiAnnotation(psiParent, jetParent)
                is PsiMethod -> NavigableKotlinPsiMethod(psiParent, jetParent)
                else -> {
                    NavigableKotlinPsiElement(psiParent, jetParent)
                }
            }
        }
    }

    override fun getNavigationElement(): PsiElement {
        return this
    }
}