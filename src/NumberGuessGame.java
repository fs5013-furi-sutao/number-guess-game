import java.util.Random;
import java.util.Scanner;

/**
 * 数当てゲーム
 * 
 * 数当てゲームのサンプルコードです。
 * クラスを使用しないバージョンです。
 */
public class NumberGuessGame {

    private static final boolean IS_TEST = true;
    private static final Random RANDOM = new Random();
    private static final Scanner STDIN = 
            new Scanner(System.in);
    private static final int CORRECT_DIGIT = 3;
    private static final int MAX_CHALLENGE_TIMES = 10;
    private static final int NEAR_DISTANCE_RANGE = 10;

    public static void main(String[] args) {

        String generatedCorrectNumStr;
        int challengeTimesCounter = 1;

        generatedCorrectNumStr = 
                generateCorrectNumStr(CORRECT_DIGIT);
        
        showCorrect(IS_TEST, generatedCorrectNumStr);
        
        showStartMessages(
                CORRECT_DIGIT, MAX_CHALLENGE_TIMES);
            
        String answerNumStr;
        boolean isCorrectInChallengeTimes = false;

        while (isNotOverMaxChallengeTimes(
                challengeTimesCounter)) {

            answerNumStr = recieveAnswerNumStr(
                challengeTimesCounter, CORRECT_DIGIT);

            showJudgeAnswer(
                generatedCorrectNumStr, answerNumStr);

            if (isCorrectEqualsAnswer(
                    generatedCorrectNumStr, answerNumStr)) {

                showResultOfMatchCorrect(
                        challengeTimesCounter);
                    
                isCorrectInChallengeTimes = true;
                break;
            }

            challengeTimesCounter++;
        }

        if (!isCorrectInChallengeTimes) {
            showResultOfNotCorrect(generatedCorrectNumStr);
        }
    }
    
    private static String generateCorrectNumStr(int digit) {
        int randomNum = 0;
        String generatedNumStr = "";

        for (int i = 0; i < digit; i++) {

            randomNum = RANDOM.nextInt(10);
            generatedNumStr += randomNum;
        }
        return generatedNumStr;
    }

    private static void showCorrect(
            boolean isTest, String numStr) {
            
        if (isTest)
            System.out.format("[TEST 表示]: 正解 %s %n", 
                numStr);
    }

    private static void showResultOfNotCorrect(
            String correctNumStr) {
            
        System.out.format(
            "残念！！ 正解は %s でした！ %n", correctNumStr);
    }

    private static void showResultOfMatchCorrect(
            int challengeTimesCounter) {
        
        System.out.format(
            "すごい！！ %d 回で当てられちゃった！",
            challengeTimesCounter);
    }

    private static boolean isCorrectEqualsAnswer(
            String correctNumStr, String answerNumStr) {
            
        return answerNumStr.equals(correctNumStr);
    }

    private static void showJudgeAnswer(
            String correctNumStr, String answerNumStr) {
     
        if (isCorrectBigButNear(correctNumStr, answerNumStr)) {
            System.out.println(
                "かなり近い！ もっと大きい数字だよ \n");
            return;
        }
        if (isCorrectBig(correctNumStr, answerNumStr)) {
            System.out.println("もっと大きい数字だよ");
        }

        if (isCorrectSmallButNear(correctNumStr, answerNumStr)) {
            System.out.println(
                "かなり近い！ もっと小さい数字だよ \n");
            return;        
        }
        if (isCorrectSmall(correctNumStr, answerNumStr)) {
            System.out.println("もっと小さい数字だよ");
        }
        System.out.println();
    }

    private static boolean isCorrectBigButNear(
            String correctNumStr, String answerNumStr) {
            
        return isCorrectBig(correctNumStr, answerNumStr) 
                && Integer.parseInt(correctNumStr) 
                        - Integer.parseInt(answerNumStr) 
                        <= NEAR_DISTANCE_RANGE;
    }
    
    private static boolean isCorrectSmallButNear(
            String correctNumStr, String answerNumStr) {
            
        return isCorrectSmall(correctNumStr, answerNumStr) 
                && Integer.parseInt(answerNumStr) 
                        - Integer.parseInt(correctNumStr)
                        <= NEAR_DISTANCE_RANGE;
    }

    private static boolean isCorrectBig(
            String correctNumStr, String answerNumStr) {
            
        return Integer.parseInt(answerNumStr) 
                < Integer.parseInt(correctNumStr);
    }

    private static boolean isCorrectSmall(
            String correctNumStr, String answerNumStr) {

        return Integer.parseInt(answerNumStr)
                > Integer.parseInt(correctNumStr);
    }

    private static String recieveAnswerNumStr(
            int count, int correctDigit) {
            
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

    private static boolean isNotInputCorrectDigit(
            String answerNumStr) {
            
        return answerNumStr.length() != CORRECT_DIGIT;
    }

    private static void showMessageOfInvalidInputForNumber() {
        System.out.format(
            "%d 桁の数字で入力してください %n", CORRECT_DIGIT);
    }

    private static void showStartMessages(
            int digit, int times) {
            
        System.out.println("0 ～ 9 までの数字を組み合わせた");
        
        System.out.format("%d 桁の数字を当ててみてね。%n",
            digit);
        
        System.out.println();
        
        System.out.format(
            "答えられるのは %d 回までだよ。", times);
        
        System.out.println();
    }

    private static boolean isNotOverMaxChallengeTimes(
            int challengeTimesCounter) {
            
        return challengeTimesCounter <= MAX_CHALLENGE_TIMES;
    }
}
