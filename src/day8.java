import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class day8 {
  public static void main(String[] args) throws IOException {
    List<String> mini = Files.readAllLines(Paths.get("src", "day8_input_mini.txt"));
    List<String> input = Files.readAllLines(Paths.get("src", "day8_input.txt"));
    System.out.println("part 1 mini: " + part1(mini));
    System.out.println("part1: " + part1(input));
  }

  public static int part1(List<String> lines) {
    int total = 0;
    String instructions = lines.get(0);
    class Fork {
      String left;
      String right;

      public Fork(String left, String right) {
        this.left = left;
        this.right = right;
      }
    }
    HashMap<String, Fork> paths = new HashMap<>();
    for (int i = 2; i < lines.size(); i++) {
      String line = lines.get(i);
      paths.put(line.substring(0, 3),
          new Fork(
              line.substring(line.indexOf('(') + 1, line.indexOf(',')),
              line.substring(line.indexOf(',') + 2, line.indexOf(')'))));
    }
    String curPos = "AAA";
    int curInstr = 0;
    while (!curPos.equals("ZZZ")) {
      Fork fork = paths.get(curPos);
      switch (instructions.charAt(curInstr)) {
        case 'L':
          curPos = fork.left;
          break;
        case 'R':
          curPos = fork.right;
          break;
        default:
          break;
      }
      curInstr++;
      if (curInstr == instructions.length())
        curInstr = 0;
      total++;
    }
    return total;
  }
}
