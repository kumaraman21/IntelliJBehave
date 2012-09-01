package com.github.kumaraman21.intellijbehave.utility;

import org.apache.commons.lang.LocaleUtils;
import org.jbehave.core.i18n.LocalizedKeywords;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class LocalizedKeywordsProvider {
    public LocalizedKeywords getKeywords(String locale) {
        return new LocalizedKeywords(LocaleUtils.toLocale(locale));
    }
}
