package org.hd.codeminders;

import java.io.File;
import java.io.InputStream;
import org.jetbrains.annotations.NotNull;

/**
 * @author Horkhover Dmytro
 * @since 2020-09-03
 */
interface CodeLinesReader {

  int readCodeLinesCount(@NotNull File file);

  int readCodeLinesCount(@NotNull InputStream inputStream);
}
