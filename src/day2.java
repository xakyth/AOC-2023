import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day2 {
  public static void main(String[] args) throws IOException {
    List<String> input = Files.readAllLines(Paths.get("src", "day2_input.txt"));
    System.out.println("part1: " + part1(input));
    System.out.println("part2: " + part2(input));
  }

  public static int part1(List<String> lines) {
    int sumOfIds = 0;
    int red = 12, green = 13, blue = 14;
    Pattern pattern = Pattern.compile("(\\d+) (red|green|blue)");
    for (String line : lines) {
      int gameId = Integer.parseInt(line.substring(5, line.indexOf(':')));
      line = line.substring(line.indexOf(':') + 1);
      String[] sets = line.split(";");
      boolean flag = true;
      for (String set : sets) {
        Matcher matcher = pattern.matcher(set);
        while (matcher.find()) {
          int numOfCubes = Integer.parseInt(matcher.group(1));
          switch (matcher.group(2)) {
            case "red":
              if (numOfCubes > red)
                flag = false;
              break;
            case "green":
              if (numOfCubes > green)
                flag = false;
              break;
            case "blue":
              if (numOfCubes > blue)
                flag = false;
              break;
          }
        }
        if (!flag)
          break;
      }
      if (flag) {
        sumOfIds += gameId;
      }
    }

    return sumOfIds;
  }
  public static int part2(List<String> lines) {
    int totalSum = 0;
    Pattern pattern = Pattern.compile("(\\d+) (red|green|blue)");
    for (String line : lines) {
      int red = 0, green = 0, blue = 0;
      line = line.substring(line.indexOf(':') + 1);
      String[] sets = line.split(";");
      for (String set : sets) {
        Matcher matcher = pattern.matcher(set);
        while (matcher.find()) {
          int numOfCubes = Integer.parseInt(matcher.group(1));
          switch (matcher.group(2)) {
            case "red":
              if (numOfCubes > red)
                red = numOfCubes;
              break;
            case "green":
              if (numOfCubes > green)
                green = numOfCubes;
              break;
            case "blue":
              if (numOfCubes > blue)
                blue = numOfCubes;
              break;
          }
        }
      }
      totalSum += red * green * blue;
    }

    return totalSum;
  }
}
