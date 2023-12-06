import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class day3 {
  public static void main(String[] args) throws IOException {
    List<String> input = Files.readAllLines(Paths.get("src", "day3_input.txt"));
    System.out.println("part1: " + part1(input));
  }

  public static int part1(List<String> lines) {
    boolean[][] adj = new boolean[lines.size()][lines.get(0).length()];
    for (int i = 0; i < adj.length; i++) {
      String line = lines.get(i);
      for (int j = 0; j < line.length(); j++) {
        char c = line.charAt(j);
        if (!Character.isDigit(c) && c!= '.') {
          if (i > 0 && j > 0) adj[i-1][j-1] = true;
          if (i > 0) adj[i-1][j] = true;
          if (i > 0 && j + 1 < adj.length) adj[i-1][j+1] = true;
          if (j > 0) adj[i][j-1] = true;
          if (j + 1 < adj.length) adj[i][j+1] = true;
          if (i < adj.length && j > 0) adj[i+1][j-1] = true;
          if (i < adj.length) adj[i+1][j] = true;
          if (i < adj.length && j + 1 < adj.length) adj[i+1][j+1] = true;
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
            if (isAdjacent) sumParts += Integer.parseInt(sb.toString());
            sb.setLength(0);
            isAdjacent = false;
          }
        }
      }
      if (!sb.isEmpty()) {
        if (isAdjacent) sumParts += Integer.parseInt(sb.toString());
        sb.setLength(0);
      }
    }
    return sumParts;
  }
}
