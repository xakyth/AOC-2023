import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class day1 {
  public static void main(String[] args) throws IOException {
    List<String> firstInput = Files.readAllLines(Paths.get("src", "day1_input1.txt"));
    System.out.println(sumOfCalibrationValues(firstInput));
  }

  public static int sumOfCalibrationValues(List<String> allLines) {
    int totalSum = 0;
    for (String line : allLines) {
      int firstDigit = 0;
      int lastDigit = 0;
      for (int i = 0; i < line.length(); i++) {
        if (Character.isDigit(line.charAt(i))) {
          firstDigit = line.charAt(i) - '0';
          break;
        }
      }
      for (int i = line.length() - 1; i >= 0; i--) {
        if (Character.isDigit(line.charAt(i))) {
          lastDigit = line.charAt(i) - '0';
          break;
        }
      }
      totalSum += firstDigit * 10 + lastDigit;
    }
    return totalSum;
  }
}
