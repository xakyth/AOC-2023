import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;

public class day4 {
  public static void main(String[] args) throws IOException {
    List<String> input = Files.readAllLines(Paths.get("src", "day4_input.txt"));
    System.out.println("part1: " + part1(input));
  }

  public static int part1(List<String> lines) {
    int total = 0;
    HashSet<Integer> winningNumbers = new HashSet<>();
    for (String line : lines) {
      winningNumbers.clear();
      String[] winning = line.substring(line.indexOf(':') + 2, line.indexOf('|')).trim().split("  ?");
      for (int i = 0; i < winning.length; i++) {
        winningNumbers.add(Integer.parseInt(winning[i].trim()));
      }
      String[] myNumbers = line.substring(line.indexOf('|') + 2).trim().split("  ?");
      int cnt = 0;
      for (int i = 0; i < myNumbers.length; i++) {
        if (winningNumbers.contains(Integer.parseInt(myNumbers[i].trim())))
          cnt++;
      }
      total += cnt > 0 ? 1 << cnt - 1 : 0;
    }
    return total;
  }
}
