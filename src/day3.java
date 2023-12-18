import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class day3 {
  public static void main(String[] args) throws IOException {
    List<String> mini = Files.readAllLines(Paths.get("src", "day3_input_mini.txt"));
    List<String> input = Files.readAllLines(Paths.get("src", "day3_input.txt"));
    System.out.println("part1: " + part1(input));
    System.out.println("part2 mini: " + part2(mini));
    System.out.println("part2: " + part2(input));
  }

  public static int part1(List<String> lines) {
    boolean[][] adj = new boolean[lines.size()][lines.get(0).length()];
    for (int i = 0; i < adj.length; i++) {
      String line = lines.get(i);
      for (int j = 0; j < line.length(); j++) {
        char c = line.charAt(j);
        if (!Character.isDigit(c) && c != '.') {
          if (i > 0 && j > 0)
            adj[i - 1][j - 1] = true;
          if (i > 0)
            adj[i - 1][j] = true;
          if (i > 0 && j + 1 < adj.length)
            adj[i - 1][j + 1] = true;
          if (j > 0)
            adj[i][j - 1] = true;
          if (j + 1 < adj.length)
            adj[i][j + 1] = true;
          if (i < adj.length && j > 0)
            adj[i + 1][j - 1] = true;
          if (i < adj.length)
            adj[i + 1][j] = true;
          if (i < adj.length && j + 1 < adj.length)
            adj[i + 1][j + 1] = true;
        }
      }
    }
    int sumParts = 0;
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < adj.length; i++) {
      String line = lines.get(i);
      sb.setLength(0);
      boolean isAdjacent = false;
      for (int j = 0; j < line.length(); j++) {
        char c = line.charAt(j);
        if (Character.isDigit(c)) {
          isAdjacent = isAdjacent == false ? adj[i][j] : isAdjacent;
          sb.append(c);
        } else {
          if (!sb.isEmpty()) {
            if (isAdjacent)
              sumParts += Integer.parseInt(sb.toString());
            sb.setLength(0);
            isAdjacent = false;
          }
        }
      }
      if (!sb.isEmpty()) {
        if (isAdjacent)
          sumParts += Integer.parseInt(sb.toString());
        sb.setLength(0);
      }
    }
    return sumParts;
  }

  public static int part2(List<String> lines) {
    int total = 0;
    int rows = lines.size();
    int cols = lines.get(0).length();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (lines.get(i).charAt(j) == '*') {
          List<Integer> adjs = getAdjacentNums(lines, i, j, rows, cols);
          if (adjs.size() == 2) {
            total += adjs.get(0) * adjs.get(1);
          }
        }
      }
    }
    return total;
  }

  private static List<Integer> getAdjacentNums(List<String> lines, int i, int j, int rows, int cols) {
    List<Integer> result = new ArrayList<>();
    if (i - 1 >= 0) {
      int endI = -1;
      if (j - 1 >= 0) {
        if (Character.isDigit(lines.get(i - 1).charAt(j - 1))) {
          int[] num = getNumber(lines, i - 1, j - 1);
          result.add(num[0]);
          endI = num[1];
        }
      }
      if (Character.isDigit(lines.get(i - 1).charAt(j)) && j > endI) {
        int[] num = getNumber(lines, i - 1, j);
        result.add(num[0]);
        endI = num[1];
      }
      if (cols > j + 1 && j + 1 > endI) {
        if (Character.isDigit(lines.get(i - 1).charAt(j + 1))) {
          int[] num = getNumber(lines, i - 1, j + 1);
          result.add(num[0]);
        }
      }
    }
    if (j - 1 >= 0 && Character.isDigit(lines.get(i).charAt(j - 1))) {
      int[] num = getNumber(lines, i, j - 1);
      result.add(num[0]);
    }
    if (cols > j + 1 && Character.isDigit(lines.get(i).charAt(j + 1))) {
      int[] num = getNumber(lines, i, j + 1);
      result.add(num[0]);
    }
    if (i + 1 < rows) {
      int endI = -1;
      if (j - 1 > 0) {
        if (Character.isDigit(lines.get(i + 1).charAt(j - 1))) {
          int[] num = getNumber(lines, i + 1, j - 1);
          result.add(num[0]);
          endI = num[1];
        }
      }
      if (Character.isDigit(lines.get(i + 1).charAt(j)) && j > endI) {
        int[] num = getNumber(lines, i + 1, j);
        result.add(num[0]);
        endI = num[1];
      }
      if (cols > j + 1 && j + 1 > endI) {
        if (Character.isDigit(lines.get(i + 1).charAt(j + 1))) {
          int[] num = getNumber(lines, i + 1, j + 1);
          result.add(num[0]);
          endI = num[1];
        }
      }
    }

    return result;
  }

  private static int[] getNumber(List<String> lines, int x, int y) {
    int startI = y;
    int endI = y;
    for (int i = y; i >= 0; i--) {
      if (!Character.isDigit(lines.get(x).charAt(i))) {
        startI = i + 1;
        break;
      }
      if (i == 0) {
        startI = i;
      }
    }
    for (int i = y; i < lines.get(x).length(); i++) {
      if (!Character.isDigit(lines.get(x).charAt(i))) {
        endI = i - 1;
        break;
      }
      if (i == lines.get(x).length() - 1) {
        endI = i;
      }
    }
    int num = Integer.parseInt(lines.get(x).substring(startI, endI + 1));
    return new int[] { num, endI };
  }
}
