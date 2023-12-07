import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day5 {
  private static Long minValue = Long.MAX_VALUE;
  public static void main(String[] args) throws IOException {
    List<String> input = Files.readAllLines(Paths.get("src", "day5_input.txt"));
    System.out.println("part1: " + part1(input));
  }

  public static long part1(List<String> lines) {
    List<Long> seeds = new ArrayList<>();
    for (String s : lines.get(0).substring((lines.get(0).indexOf(':')+2)).split(" ")) {
      seeds.add(Long.parseLong(s));
    }
    List<List<Long[]>> maps = new ArrayList<>();
    Pattern pattern = Pattern.compile("(\\d+) (\\d+) (\\d+)");
    for (int i = 2; i < lines.size(); i++) {
      String line = lines.get(i);
      if (line.endsWith("map:")) {
        List<Long[]> curMap = new ArrayList<>();
        for (int j = i + 1; j < lines.size(); j++, i = j) {
          Matcher matcher = pattern.matcher(lines.get(j));
          if (matcher.find()) {
            curMap.add(new Long[] {
              Long.parseLong(matcher.group(1)),
              Long.parseLong(matcher.group(2)),
              Long.parseLong(matcher.group(3))
            });
          } else {
            break;
          }
        }
        maps.add(curMap);
      }
    }
    for (Long seed : seeds) {
      backtrack(seed, 0, maps);
    }
    return minValue;
  }
  private static void backtrack(Long curValue, int depth, List<List<Long[]>> maps) {
    if (depth >= 7) {
      if (curValue < minValue) {
        minValue = curValue;
      }
    } else {
      boolean curValueInMap = false;
      List<Long[]> map = maps.get(depth);
      for (Long[] data : map) {
        if (curValue >= data[1] && curValue <= data[1] + data[2] - 1) {
          curValueInMap = true;
          Long diff = curValue - data[1];
          backtrack(data[0] + diff, depth + 1, maps);
        }
      }
      if (!curValueInMap) {
        backtrack(curValue, depth + 1, maps);
      }
    }
  }
}
