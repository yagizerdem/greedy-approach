import javax.print.attribute.standard.JobOriginatingUserName;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ArrayList<String> allTypes = new ArrayList<>();
        ArrayList<Judge> allJudges = new ArrayList<>();
        String filePath = "input.txt";
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                allTypes.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int judgeCount = 3;
        int switchTime = 1;
        for (int i = 0; i < judgeCount; i++) {
            allJudges.add(new Judge());
        }

        int result = Greedy(allJudges, allTypes, switchTime);

        System.out.println(result);

    }

    static class Judge {
        Stack<String> types = new Stack<>();

        public String getCurrentType() {
            return types.peek();
        }
    }

    static int Greedy(ArrayList<Judge> allJudges, ArrayList<String> allTypes, int switchTime) {
        outer:
        while (!allTypes.isEmpty()) {
            String currentType = allTypes.remove(0);

            for (Judge judge : allJudges) {
                if (!judge.types.isEmpty() && judge.getCurrentType().equals(currentType)) {
                    judge.types.push(currentType);
                    continue outer;
                }
            }
            // insert into emtpy if exist
            for (Judge judge : allJudges) {
                if (judge.types.isEmpty()) {
                    judge.types.push(currentType);
                    continue outer;
                }
            }

            int maxDistance = Integer.MIN_VALUE;
            Judge maxDistanceJudge = null;
            // insert into judge that has max distance
            for (Judge judge : allJudges) {
                String type = judge.getCurrentType();
                int distance = allTypes.indexOf(type);
                distance = distance == -1 ? Integer.MAX_VALUE : distance;
                if (distance > maxDistance) {
                    maxDistance = distance;
                    maxDistanceJudge = judge;
                }
            }
            maxDistanceJudge.types.push(currentType);
        }

        int totalTime = 0;
        // calculate time
        for (Judge judge : allJudges) {
            String cur = null;
            String prev = null;
            while (!judge.types.isEmpty()) {
                cur = judge.types.pop();
                if(!cur.equals(prev)) {
                    totalTime += switchTime;
                }

                prev = cur;
            }
        }

        return totalTime;
    }

}