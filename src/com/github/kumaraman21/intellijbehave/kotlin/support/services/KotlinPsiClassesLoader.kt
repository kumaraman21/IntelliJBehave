package com.github.kumaraman21.intellijbehave.kotlin.support.services

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFile
import org.jetbrains.kotlin.asJava.toLightClass
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFile

/**
 * Created by Rodrigo Quesada on 20/09/15.
 */
class KotlinPsiClassesLoader private constructor() {

    companion object {

        val INSTANCE = KotlinPsiClassesLoader();

        @JvmStatic
        public fun getInstance() = INSTANCE
    }

    fun getPsiClasses(psiFile: PsiFile): List<PsiClass>? = if (psiFile is KtFile) {
        psiFile.classes.toList()
    }
    else null
}