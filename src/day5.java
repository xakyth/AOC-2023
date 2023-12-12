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
    System.out.println("part2: " + part2(input));
  }

  public static long part1(List<String> lines) {
    List<Long> seeds = new ArrayList<>();
    for (String s : lines.get(0).substring((lines.get(0).indexOf(':') + 2)).split(" ")) {
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

  public static long part2(List<String> lines) {
    List<Long[]> seeds = new ArrayList<>();
    String[] seedsLine = lines.get(0).substring(7).split(" ");
    for (int i = 0; i < seedsLine.length; i++) {
      if (i % 2 == 1) {
        seeds.add(new Long[] { Long.parseLong(seedsLine[i-1]), Long.parseLong(seedsLine[i]) });
      }
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
    minValue = Long.MAX_VALUE;
    for (Long[] seedRange : seeds) {
        backtrack2(new LineSegment(seedRange[0], seedRange[0] + seedRange[1] - 1), 0, maps);
    }
    
    return minValue;
  }

  private static void backtrack2(LineSegment seg, int depth, List<List<Long[]>> maps) {
    if (depth >= 7) {
      if (seg.x0 < minValue) {
        minValue = seg.x0;
      }
    } else {
        List<Long[]> curMaps = maps.get(depth);
        List<LineSegment> unusedSegments = new ArrayList<>();
        boolean flag = false;
        for (Long[] map : curMaps) {
          LineSegment mapSeg = new LineSegment(map[1], map[1] + map[2] - 1);
          LineSegment intersection = getIntersection(mapSeg, seg);
          if (intersection != null) {
            if (seg.x0 < intersection.x0) {
              unusedSegments.add(new LineSegment(seg.x0, intersection.x0));
            }
            if (intersection.x1 < seg.x1)
              unusedSegments.add(new LineSegment(intersection.x1, seg.x1));
            flag = true;
            backtrack2(new LineSegment(
                map[0] + (intersection.x0 - map[1]),
                map[0] + (intersection.x0 - map[1]) + (intersection.x1 - intersection.x0)),
                depth + 1, maps);
          }
        }
        if (unusedSegments.size() > 0) {
          List<LineSegment> intersectedUnusedSegments = new ArrayList<>();
          intersectedUnusedSegments.add(seg);
          for (LineSegment unusedSeg : unusedSegments) {
            List<LineSegment> temp = new ArrayList<>();
            for (int i = 0; i < intersectedUnusedSegments.size(); i++) {
              LineSegment tempSeg = getIntersection(unusedSeg, intersectedUnusedSegments.get(i));
              if (tempSeg != null) temp.add(tempSeg);
            }
            intersectedUnusedSegments.clear();
            intersectedUnusedSegments.addAll(temp);
          }
          for (LineSegment unusedSeg : intersectedUnusedSegments) {
            backtrack2(unusedSeg, depth + 1, maps);
          }
        }
        if (!flag) {
          backtrack2(seg, depth + 1, maps);
        }
      }
  }

  private static LineSegment getIntersection(LineSegment seg1, LineSegment seg2) {
    long x0 = Math.max(seg1.x0, seg2.x0);
    long x1 = Math.min(seg1.x1, seg2.x1);
    if (x0 <= x1) {
      return new LineSegment(x0, x1);
    }
    return null;
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
