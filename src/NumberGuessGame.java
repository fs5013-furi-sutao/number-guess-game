import java.util.Random;
import java.util.Scanner;

/**
 * 数当てゲーム
 * 
 * 数当てゲームのサンプルコードです。 クラスを使用しないバージョンです。
 */
public class NumberGuessGame {

    // 正解を表示するか・しないかのデバッグ機能 ON/OFF 設定
    private static final boolean IS_DEBUG_MODE = true;

    private static final Random RANDOM = new Random();
    private static final Scanner STDIN = new Scanner(System.in);

    // 生成する正解の桁数などの設定
    private static final int CORRECT_DIGIT = 3;
    private static final int RANDOM_NUM_RANGE = 10;

    // チャレンジ回数設定
    private static final int MAX_CHALLENGE_TIMES = 10;
    private static final int COUNT_OF_START = 1;

    // どれだけ離れていたら、どんな判定メッセージを表示するかの設定
    private static final int[] DISTANCE_TYPES = { 10, 50, 100, 200, };
    private static final String[] DISTANCE_TYPE_MESSAGES = {
        "あとほんの少し", "あと少し", "それより", "まだまだ", "もっと", 
    };
    private static final String OUT_DISTANCE_MESSAGE = "まだかなり";
    private static final String MESSAGE_OF_NUMBER_IS_TOO_SMALL = "小さい数字だよ";
    private static final String MESSAGE_OF_NUMBER_IS_TOO_BIG = "大きい数字だよ";

    public static void main(String[] args) {

        String generatedCorrectNumStr;
        int challengeTimesCounter = COUNT_OF_START;

        generatedCorrectNumStr = generateCorrectNumStr(CORRECT_DIGIT);

        showCorrect(IS_DEBUG_MODE, generatedCorrectNumStr);
        showStartMessages(CORRECT_DIGIT, MAX_CHALLENGE_TIMES);

        String answerNumStr;
        boolean isCorrectInChallengeTimes = false;

        while (!isOverMaxChallengeTimes(challengeTimesCounter)) {

            showTryCount(challengeTimesCounter);
            answerNumStr = recieveAnswerNumStr(challengeTimesCounter,
                    CORRECT_DIGIT);

            showJudgeBigOrSmall(generatedCorrectNumStr, answerNumStr);

            if (isCorrect(generatedCorrectNumStr, answerNumStr)) {

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

    private static void showJudgeBigOrSmall(String correctNumStr,
            String answerNumStr) {

        if (isCorrect(correctNumStr, answerNumStr))
            return;

        System.out.print(getMessageOfHowDistance(correctNumStr, answerNumStr));

        if (isCorrectBig(correctNumStr, answerNumStr)) {
            System.out.println(MESSAGE_OF_NUMBER_IS_TOO_BIG);
        }

        if (isCorrectSmall(correctNumStr, answerNumStr)) {
            System.out.println(MESSAGE_OF_NUMBER_IS_TOO_SMALL);
        }
        System.out.println();
    }

    private static String getMessageOfHowDistance(String correctNumStr,
            String answerNumStr) {

        int distance = calcDistance(correctNumStr, answerNumStr);

        for (int i = 0; i < DISTANCE_TYPES.length; i++) {
            if (distance <= DISTANCE_TYPES[i]) {
                return DISTANCE_TYPE_MESSAGES[i];
            }
        }
        return OUT_DISTANCE_MESSAGE;
    }

    private static int calcDistance(String correctNumStr, String answerNumStr) {
        int correctNum = Integer.parseInt(correctNumStr);
        int answerNum = Integer.parseInt(answerNumStr);
        return Math.abs(correctNum - answerNum);
    }

    private static boolean isCorrect(String correctNumStr,
            String answerNumStr) {

        return answerNumStr.equals(correctNumStr);
    }

    private static boolean isCorrectBig(String correctNumStr,
            String answerNumStr) {

        return Integer.parseInt(answerNumStr) < Integer.parseInt(correctNumStr);
    }

    private static boolean isCorrectSmall(String correctNumStr,
            String answerNumStr) {

        return Integer.parseInt(answerNumStr) > Integer.parseInt(correctNumStr);
    }

    private static String recieveAnswerNumStr(int count, int correctDigit) {

        String inputtedNumStr = recieveStr();

        if (isNumber(inputtedNumStr)) {

            if (!isInputCorrectDigit(inputtedNumStr)) {
                showMessageOfInvalidInputForNumber();
                return recieveAnswerNumStr(count, correctDigit);
            }
            return inputtedNumStr;

        }
        showMessageOfInvalidInputForNumber();
        return recieveAnswerNumStr(count, correctDigit);
    }

    private static boolean isNumber(String inputtedNumStr) {
        try {
            Integer.parseInt(inputtedNumStr);

        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static String recieveStr() {
        return STDIN.nextLine();
    }

    private static void showTryCount(int count) {
        System.out.format("%d 回目: ", count);
    }

    private static boolean isInputCorrectDigit(String answerNumStr) {

        return answerNumStr.length() <= CORRECT_DIGIT;
    }

    private static void showMessageOfInvalidInputForNumber() {
        System.out.format("%d 桁の数字で入力してください %n", CORRECT_DIGIT);
    }

    private static void showStartMessages(int digit, int times) {

        System.out.println("0 ～ 9 までの数字を組み合わせた");
        System.out.format("%d 桁までの数字を当ててみてね。%n", digit);
        System.out.println();
        System.out.format("答えられるのは %d 回までだよ。", times);
        System.out.println();
    }

    private static boolean isOverMaxChallengeTimes(int challengeTimesCounter) {

        return challengeTimesCounter > MAX_CHALLENGE_TIMES;
    }
}
