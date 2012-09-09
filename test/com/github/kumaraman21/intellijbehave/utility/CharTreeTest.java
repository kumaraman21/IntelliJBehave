package com.github.kumaraman21.intellijbehave.utility;

import static com.github.kumaraman21.intellijbehave.utility.JBKeyword.Narrative;
import static org.fest.assertions.api.Assertions.assertThat;

import com.github.kumaraman21.intellijbehave.utility.CharTree;
import com.github.kumaraman21.intellijbehave.utility.JBKeyword;

import org.jbehave.core.i18n.LocalizedKeywords;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class CharTreeTest {

    private CharTree<JBKeyword> charTree;

    @Before
    public void setUp () {
        LocalizedKeywords keywords = new LocalizedKeywords();
        charTree = new CharTree<JBKeyword>('/', null);
        for (JBKeyword kw : JBKeyword.values()) {
            String asString = kw.asString(keywords);
            charTree.push(asString, kw);
        }
    }

    @Test
    public void narrative_doubleDot() {
        assertThat(charTree.lookup("Narrative: \n", 0)).isEqualTo(new CharTree.Entry<JBKeyword>(Narrative, 10));
    }
}
