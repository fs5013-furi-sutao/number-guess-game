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
    private static final String[] DISTANCE_TYPE_MESSAGES = { "あとほんの少し", "あと少し",
            "それより", "まだまだ", "もっと", };
    private static final String OUT_DISTANCE_MESSAGE = "まだかなり";
    private static final String MESSAGE_OF_NUMBER_IS_TOO_SMALL = "小さい数字だよ";
    private static final String MESSAGE_OF_NUMBER_IS_TOO_BIG = "大きい数字だよ";

    // 表示メッセージ設定
    private static final String BLANK = "";
    private static final String MESSAGE_FORMAT_FOR_SHOW_CORRECT = "[TEST 表示]: 正解 %s %n";
    private static final String MESSAGE_FORMAT_FOR_NOT_CORRECT = "残念！！ 正解は %s でした！ %n";
    private static final String MESSAGE_FORMAT_FOR_MATCH_CORRECT_IN_DEFINED_TIMES = "すごい！！ %d 回で当てられちゃった！";
    private static final String MESSAGE_FORMAT_FOR_SHOW_TRY_COUNT = "%d 回目: ";
    private static final String MESSAGE_FOR_REQUIRED_NUMBER_RANGE = "0 ～ 9 までの数字を組み合わせた";
    private static final String MESSAGE_FORMAT_FOR_INPUT_IN_DIFINED_DIGITS = "%d 桁の数字で入力してください %n";
    private static final String MESSAGE_FORMAT_FOR_INPUT_NUMS_IN_DEFINED_DIGITS = "%d 桁までの数字を当ててみてね。%n";
    private static final String MESSAGE_FORMAT_FOR_ALLOWED_TRY_CONT = "答えられるのは %d 回までだよ。";

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
        String generatedNumStr = BLANK;

        for (int i = 0; i < digit; i++) {
            randomNum = RANDOM.nextInt(RANDOM_NUM_RANGE);
            generatedNumStr += randomNum;
        }
        return generatedNumStr;
    }

    private static String recieveAnswerNumStr(int count, int correctDigit) {
        String inputtedNumStr = recieveInputtedNumStr();

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

    private static String recieveInputtedNumStr() {
        return STDIN.nextLine();
    }

    private static int calcDistance(String correctNumStr, String answerNumStr) {
        int correctNum = Integer.parseInt(correctNumStr);
        int answerNum = Integer.parseInt(answerNumStr);
        return Math.abs(correctNum - answerNum);
    }

    private static boolean isNumber(String str) {
        try {
            parseToInt(str);

        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static boolean isInputCorrectDigit(String answerNumStr) {
        return answerNumStr.length() <= CORRECT_DIGIT;
    }

    private static boolean isCorrect(String correctNumStr,
            String answerNumStr) {
        return answerNumStr.equals(correctNumStr);
    }

    private static boolean isCorrectBig(String correctNumStr,
            String answerNumStr) {
        return parseToInt(answerNumStr) < parseToInt(correctNumStr);
    }

    private static boolean isCorrectSmall(String correctNumStr,
            String answerNumStr) {
        return parseToInt(answerNumStr) > parseToInt(correctNumStr);
    }

    private static int parseToInt(String str) {
        return Integer.parseInt(str);
    }

    private static void showTryCount(int count) {
        System.out.format(MESSAGE_FORMAT_FOR_SHOW_TRY_COUNT, count);
    }

    private static void showMessageOfInvalidInputForNumber() {
        System.out.format(MESSAGE_FORMAT_FOR_INPUT_IN_DIFINED_DIGITS,
                CORRECT_DIGIT);
    }

    private static void showStartMessages(int digit, int times) {
        show(MESSAGE_FOR_REQUIRED_NUMBER_RANGE);
        System.out.format(MESSAGE_FORMAT_FOR_INPUT_NUMS_IN_DEFINED_DIGITS,
                digit);
        show(BLANK);
        System.out.format(MESSAGE_FORMAT_FOR_ALLOWED_TRY_CONT, times);
        show(BLANK);
    }

    private static void showCorrect(boolean isTest, String numStr) {

        if (isTest)
            System.out.format(MESSAGE_FORMAT_FOR_SHOW_CORRECT, numStr);
    }

    private static void showResultOfNotCorrect(String correctNumStr) {

        System.out.format(MESSAGE_FORMAT_FOR_NOT_CORRECT, correctNumStr);
    }

    private static void showResultOfMatchCorrect(int challengeTimesCounter) {

        System.out.format(MESSAGE_FORMAT_FOR_MATCH_CORRECT_IN_DEFINED_TIMES,
                challengeTimesCounter);
    }

    private static void showJudgeBigOrSmall(String correctNumStr,
            String answerNumStr) {
        if (isCorrect(correctNumStr, answerNumStr))
            return;

        showWithNoBreaks(getMessageOfHowDistance(correctNumStr, answerNumStr));

        if (isCorrectBig(correctNumStr, answerNumStr)) {
            show(MESSAGE_OF_NUMBER_IS_TOO_BIG);
        }

        if (isCorrectSmall(correctNumStr, answerNumStr)) {
            show(MESSAGE_OF_NUMBER_IS_TOO_SMALL);
        }
        show(BLANK);
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

    private static void show(String message) {
        System.out.println(message);
    }

    private static void showWithNoBreaks(String message) {
        System.out.print(message);
    }

    private static boolean isOverMaxChallengeTimes(int challengeTimesCounter) {
        return challengeTimesCounter > MAX_CHALLENGE_TIMES;
    }
}
