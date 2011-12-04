/*
 * Copyright 2011 Aman Kumar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.kumaraman21.intellijbehave.parser;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import static com.github.kumaraman21.intellijbehave.language.StoryFileType.STORY_FILE_TYPE;

public class StoryElementType extends IElementType {

  public StoryElementType(@NotNull @NonNls String debugName) {
    super(debugName, STORY_FILE_TYPE.getLanguage());
  }

  public static final IFileElementType STORY_FILE = new IFileElementType(STORY_FILE_TYPE.getLanguage());
  public static final IElementType STORY = new StoryElementType("STORY");
  public static final IElementType SCENARIO = new StoryElementType("SCENARIO");
  public static final IElementType STEP = new StoryElementType("STEP");
}
