import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day6 {
  public static void main(String[] args) throws IOException {
    List<String> input_mini = Files.readAllLines(Paths.get("src", "day6_input_mini.txt"));
    List<String> input = Files.readAllLines(Paths.get("src", "day6_input.txt"));
    System.out.println("part1 mini: " + part1(input_mini));
    System.out.println("part1: " + part1(input));
    System.out.println("part2 mini: " + part2(input_mini));
    System.out.println("part2: " + part2(input));
  }

  public static int part1(List<String> lines) {
    int res = 1;
    List<Integer> time = new ArrayList<>();
    List<Integer> distances = new ArrayList<>();
    Pattern pattern = Pattern.compile("\\d+");
    Matcher matcher = pattern.matcher(lines.get(0));
    while (matcher.find()) {
      time.add(Integer.parseInt(matcher.group()));
    }
    matcher = pattern.matcher(lines.get(1));
    while (matcher.find()) {
      distances.add(Integer.parseInt(matcher.group()));
    }
    for (int i = 0; i < time.size(); i++) {
      int t = time.get(i);
      int d = distances.get(i);
      double x0 = (-t + Math.sqrt(Math.pow(t, 2) - 4 * d)) / (-2);
      double x1 = (-t - Math.sqrt(Math.pow(t, 2) - 4 * d)) / (-2);
      int x_0 = 0;
      int x_1 = 0;
      if (x0 % 1 == 0) {
        x_0 = (int) x0 + 1;
      } else {
        x_0 = (int) Math.ceil(x0);
      }
      if (x1 % 1 == 0) {
        x_1 = (int) x1 - 1;
      } else {
        x_1 = (int) Math.floor(x1);
      }
      res *= (x_1 - x_0 + 1);
    }
    return res;
  }

  public static long part2(List<String> lines) {
    long res = 1;
    long time = 0;
    long dist = 0;
    Pattern pattern = Pattern.compile("\\d+");
    Matcher matcher = pattern.matcher(lines.get(0));
    StringBuilder temp = new StringBuilder();
    while (matcher.find()) {
      temp.append(matcher.group());
    }
    time = Long.parseLong(temp.toString());
    temp.setLength(0);
    matcher = pattern.matcher(lines.get(1));
    while (matcher.find()) {
      temp.append(matcher.group());
    }
    dist = Long.parseLong(temp.toString());
    double x0 = (-time + Math.sqrt(Math.pow(time, 2) - 4 * dist)) / (-2);
    double x1 = (-time - Math.sqrt(Math.pow(time, 2) - 4 * dist)) / (-2);
    long x_0 = 0;
    long x_1 = 0;
    if (x0 % 1 == 0) {
      x_0 = (long) x0 + 1;
    } else {
      x_0 = (long) Math.ceil(x0);
    }
    if (x1 % 1 == 0) {
      x_1 = (long) x1 - 1;
    } else {
      x_1 = (long) Math.floor(x1);
    }
    res *= (x_1 - x_0 + 1);
    return res;
  }
}
