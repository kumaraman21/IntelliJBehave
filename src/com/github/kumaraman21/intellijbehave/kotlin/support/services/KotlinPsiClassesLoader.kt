package com.github.kumaraman21.intellijbehave.kotlin.support.services

import com.intellij.psi.PsiFile
import org.jetbrains.kotlin.asJava.LightClassUtil
import org.jetbrains.kotlin.psi.JetClass
import org.jetbrains.kotlin.psi.JetFile

/**
 * Created by Rodrigo Quesada on 20/09/15.
 */
class KotlinPsiClassesLoader private constructor() {

    companion object {

        val INSTANCE = KotlinPsiClassesLoader();

        @JvmStatic
        public fun getInstance() = INSTANCE
    }

    fun getPsiClasses(psiFile: PsiFile) = if (psiFile is JetFile) {
        psiFile.declarations.asSequence()
                .map({ LightClassUtil.getPsiClass(it as? JetClass) })
                .filterNotNull().toList()
    }
    else null
}