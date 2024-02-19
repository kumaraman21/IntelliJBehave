package com.github.kumaraman21.intellijbehave.kotlin.support.services

import com.github.kumaraman21.intellijbehave.kotlin.psi.NavigableKotlinPsiAnnotation
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.QualifiedName
import org.jetbrains.kotlin.asJava.LightClassUtil
import org.jetbrains.kotlin.idea.stubindex.KotlinAnnotationsIndex
import org.jetbrains.kotlin.psi.KtFunction

/**
 * Created by Rodrigo Quesada on 20/09/15.
 */
class KotlinAnnotationsLoader private constructor() {

    companion object {

        val INSTANCE = KotlinAnnotationsLoader()

        @JvmStatic
        public fun getInstance() = INSTANCE
    }

    fun getAnnotations(qualifiedName: QualifiedName, project: Project, scope: GlobalSearchScope): Collection<PsiAnnotation> {
        val name = qualifiedName.lastComponent
        return if (name != null) {

            KotlinAnnotationsIndex.getInstance().get(name, project, scope).asSequence()
                    .filterNotNull()
                    .map({ ktAnnotation ->

                        val function = ktAnnotation.parent?.parent as? KtFunction
                        function?.let {
                            val psiAnnotation = LightClassUtil.getLightClassMethod(function)?.modifierList?.findAnnotation(qualifiedName.toString())
                            psiAnnotation?.let { NavigableKotlinPsiAnnotation(psiAnnotation, ktAnnotation) }
                        }

                    }).filterNotNull().toList()
        } else emptyList()
    }
}