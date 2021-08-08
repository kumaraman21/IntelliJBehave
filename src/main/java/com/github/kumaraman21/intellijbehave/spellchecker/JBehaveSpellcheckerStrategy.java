package com.github.kumaraman21.intellijbehave.spellchecker;

import java.util.List;

import com.github.kumaraman21.intellijbehave.highlighter.StoryTokenType;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.spellchecker.quickfixes.AcceptWordAsCorrect;
import com.intellij.spellchecker.quickfixes.ChangeTo;
import com.intellij.spellchecker.tokenizer.SpellcheckingStrategy;
import com.intellij.spellchecker.tokenizer.Tokenizer;
import org.jetbrains.annotations.NotNull;

/**
 * Provides spellchecking support for .story files.
 */
public class JBehaveSpellcheckerStrategy extends SpellcheckingStrategy {
    @NotNull
    public Tokenizer getTokenizer(PsiElement element) {
        if (element instanceof LeafElement) {
            ASTNode node = element.getNode();
            if (node != null && node.getElementType() instanceof StoryTokenType) {
                return TEXT_TOKENIZER;
            }
        }

        return super.getTokenizer(element);
    }

    @Override
    public LocalQuickFix[] getRegularFixes(PsiElement element, @NotNull TextRange textRange, boolean useRename, String wordWithTypo) {
        List<LocalQuickFix> fixes = new ChangeTo(wordWithTypo, element, textRange).getAllAsFixes();
        fixes.add(new AcceptWordAsCorrect(wordWithTypo));
        return fixes.toArray(LocalQuickFix[]::new);
    }
}
