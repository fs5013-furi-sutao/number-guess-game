import java.util.Random;
import java.util.Scanner;

/**
 * 数当てゲーム
 * 
 * 数当てゲームのサンプルコードです。 クラスを使用しないバージョンです。
 */
public class NumberGuessGame {

    // 正解を表示するか・しないかのデバッグ機能 ON/OFF 設定
    private static final boolean IS_TEST = true;

    private static final Random RANDOM = new Random();
    private static final Scanner STDIN = new Scanner(System.in);

    // 生成する正解の桁数などの設定
    private static final int CORRECT_DIGIT = 3;
    private static final int RANDOM_NUM_RANGE = 10;

    // チャレンジ回数設定
    private static final int MAX_CHALLENGE_TIMES = 10;

    // どれだけ離れていたら、どんな判定メッセージを表示するかの設定
    private static final int[] DISTANCE_TYPES = { 10, 50, 100, 200, };
    private static final String[] DISTANCE_TYPE_MESSAGES = {
            "あとほんの少し", "あと少し", "それより", "まだまだ", "もっと", };
    private static final String OUT_DISTANCE_MESSAGE = "まだかなり";

    public static void main(String[] args) {

        String generatedCorrectNumStr;
        int challengeTimesCounter = 1;

        generatedCorrectNumStr = generateCorrectNumStr(CORRECT_DIGIT);

        showCorrect(IS_TEST, generatedCorrectNumStr);

        showStartMessages(CORRECT_DIGIT, MAX_CHALLENGE_TIMES);

        String answerNumStr;
        boolean isCorrectInChallengeTimes = false;

        while (isNotOverMaxChallengeTimes(challengeTimesCounter)) {

            answerNumStr = recieveAnswerNumStr(
                    challengeTimesCounter, CORRECT_DIGIT);

            showJudgeAnswer(generatedCorrectNumStr, answerNumStr);

            if (isCorrectEqualsAnswer(
                    generatedCorrectNumStr, answerNumStr)) {

                showResultOfMatchCorrect(challengeTimesCounter);

                isCorrectInChallengeTimes = true;
                break;
            }

            challengeTimesCounter++;
        }

        if (!isCorrectInChallengeTimes) {
            showResultOfNotCorrect(generatedCorrectNumStr);
        }

        STDIN.close();
    }

    private static String generateCorrectNumStr(int digit) {
        int randomNum = 0;
        String generatedNumStr = "";

        for (int i = 0; i < digit; i++) {

            randomNum = RANDOM.nextInt(RANDOM_NUM_RANGE);
            generatedNumStr += randomNum;
        }
        return generatedNumStr;
    }

    private static void showCorrect(boolean isTest, String numStr) {

        if (isTest)
            System.out.format("[TEST 表示]: 正解 %s %n", numStr);
    }

    private static void showResultOfNotCorrect(String correctNumStr) {

        System.out.format("残念！！ 正解は %s でした！ %n", correctNumStr);
    }

    private static void showResultOfMatchCorrect(int challengeTimesCounter) {

        System.out.format("すごい！！ %d 回で当てられちゃった！", challengeTimesCounter);
    }

    private static void showJudgeAnswer(
            String correctNumStr, String answerNumStr) {

        if (isCorrectEqualsAnswer(correctNumStr, answerNumStr))
            return;

        System.out.print(getHowDistance(correctNumStr, answerNumStr));

        if (isCorrectBig(correctNumStr, answerNumStr)) {
            System.out.println("大きい数字だよ");
        }

        if (isCorrectSmall(correctNumStr, answerNumStr)) {
            System.out.println("小さい数字だよ");
        }
        System.out.println();
    }

    private static String getHowDistance(
            String correctNumStr, String answerNumStr) {

        int correctNum = Integer.parseInt(correctNumStr);
        int answerNum = Integer.parseInt(answerNumStr);
        int distance = Math.abs(correctNum - answerNum);

        for (int i = 0; i < DISTANCE_TYPES.length; i++) {
            if (distance <= DISTANCE_TYPES[i]) {
                return DISTANCE_TYPE_MESSAGES[i];
            }
        }
        return OUT_DISTANCE_MESSAGE;
    }

    private static boolean isCorrectEqualsAnswer(
            String correctNumStr, String answerNumStr) {

        return answerNumStr.equals(correctNumStr);
    }

    private static boolean isCorrectBig(
            String correctNumStr, String answerNumStr) {

        return Integer.parseInt(answerNumStr) < Integer.parseInt(correctNumStr);
    }

    private static boolean isCorrectSmall(
            String correctNumStr, String answerNumStr) {

        return Integer.parseInt(answerNumStr) > Integer.parseInt(correctNumStr);
    }

    private static String recieveAnswerNumStr(int count, int correctDigit) {

        boolean isNotInputValidated = true;
        String answerNumStr = "";

        while (isNotInputValidated) {
            System.out.format("%d 回目: ", count);

            try {
                answerNumStr = STDIN.nextLine();
                Integer.parseInt(answerNumStr);
                isNotInputValidated = false;

            } catch (NumberFormatException e) {
                showMessageOfInvalidInputForNumber();
                isNotInputValidated = true;
                continue;
            }

            if (isNotInputCorrectDigit(answerNumStr)) {
                showMessageOfInvalidInputForNumber();
                isNotInputValidated = true;
            }
        }
        return answerNumStr;
    }

    private static boolean isNotInputCorrectDigit(String answerNumStr) {

        return answerNumStr.length() != CORRECT_DIGIT;
    }

    private static void showMessageOfInvalidInputForNumber() {
        System.out.format("%d 桁の数字で入力してください %n", CORRECT_DIGIT);
    }

    private static void showStartMessages(int digit, int times) {

        System.out.println("0 ～ 9 までの数字を組み合わせた");
        System.out.format("%d 桁の数字を当ててみてね。%n", digit);
        System.out.println();
        System.out.format("答えられるのは %d 回までだよ。", times);
        System.out.println();
    }

    private static boolean isNotOverMaxChallengeTimes(
            int challengeTimesCounter) {

        return challengeTimesCounter <= MAX_CHALLENGE_TIMES;
    }
}
