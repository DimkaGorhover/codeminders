package org.hd.codeminders;

import java.io.File;
import java.util.Set;
import org.hd.codeminders.utils.CollectionUtils;
import org.hd.codeminders.utils.IOUtils;
import org.hd.codeminders.utils.StringUtils;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.IExitCodeGenerator;
import picocli.CommandLine.MissingParameterException;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Spec;

/**
 * @author Horkhover Dmytro
 * @since 2020-09-03
 */
@Command
public class App implements Runnable, IExitCodeGenerator {

  private static final Set<String> GLOBAL_EXCLUDES = CollectionUtils.setOf(
      "package-info.java",
      "module-info.java",
      ".gradle",
      ".git",
      ".vscode",
      ".mvnw",
      ".idea"
  );

  @Spec
  CommandSpec spec;

  @Option(
      names = {"-p", "--prefix"},
      paramLabel = "COUNT",
      defaultValue = "2",
      arity = "1..1024",
      description = "controls how many spaces will be added to the next level of children")
  int prefixOffset;

  @Option(
      names = {"-f", "--file"},
      paramLabel = "FILE",
      description = "path to the file, that will be analysed")
  File file;

  @Option(
      names = {"-d", "--directory"},
      paramLabel = "DIR",
      description = "path to the directory, that will be analysed")
  File directory;

  @Option(
      names = {"-e", "--exclude"},
      defaultValue = "build,target,bin,out",
      description = "[,] or [;] separated file names, that will be excluded from analysis")
  String exclude;

  private CodeFileInfoFactory codeFileInfoFactory;

  public static void main(String[] args) {
    System.exit(new CommandLine(new App()).execute(args));
  }

  private void validate() {
    if (!IOUtils.exists(directory) && !IOUtils.exists(file)) {
      throw new MissingParameterException(
          spec.commandLine(),
          spec.args(),
          "you have to set one of the next parameters: --directory or --file");
    }

    if (IOUtils.exists(file) && !file.isFile()) {
      throw new ParameterException(
          spec.commandLine(),
          String.format("\"%s\" isn't file", file));
    }

    if (IOUtils.exists(directory) && !directory.isDirectory()) {
      throw new ParameterException(
          spec.commandLine(),
          String.format("\"%s\" isn't directory", directory));
    }
    prefixOffset = Math.max(0, prefixOffset);
  }

  @Override
  public void run() {
    validate();

    Set<String> excludeSet = CollectionUtils.mergeSets(GLOBAL_EXCLUDES, StringUtils.toSet(exclude));
    codeFileInfoFactory = new SimpleCodeFileInfoFactory(excludeSet, prefixOffset);

    if (IOUtils.exists(file)) {
      System.out.printf("Analysing file: [%s]:%n", file);
      doRun(file);
      System.out.println();
    }

    if (IOUtils.exists(directory)) {
      System.out.printf("Analysing directory [%s]:%n", directory);
      doRun(directory);
      System.out.println();
    }
  }

  private void doRun(File file) {
    codeFileInfoFactory.createStream(file)
        .flatMap(CodeFileInfo::toStrings)
        .forEach(System.out::println);
  }

  @Override
  public int getExitCode() {
    return 0;
  }
}
