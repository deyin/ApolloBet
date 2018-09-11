package dylan.io.apollobet.models;

import java.util.Arrays;
import java.util.List;

public enum OddsType {

    // Total goals
    GOAL_ZERO("s0", "0球"),
    GOAL_ONE("s1", "1球"),
    GOAL_TWO("s2", "2球"),
    GOAL_THREE("s3", "3球"),

    GOAL_FOUR("s4", "4球"),
    GOAL_FIVE("s5", "5球"),
    GOAL_SIX("s6", "6球"),
    GOAL_SEVEN_PLUS("07", "7+球"),

    // 1 x 2 odds win/draw/lose
    LOSE("a", "负"),
    DRAW("d", "平"),
    WIN("h", "胜"),

    // spread odds win/draw/lose
    SPREAD_LOSE("sa", "让负"),
    SPREAD_DRAW("sd", "让平"),
    SPREAD_WIN("sh", "让胜"),

    // HALF FULL
    HF_WIN_WIN("hh", "胜/胜"),
    HF_WIN_DRAW("hd", "胜/平"),
    HF_WIN_LOSE("ha", "胜/负"),

    HF_DRAW_WIN("dh", "平/胜"),
    HF_DRAW_DRAW("dd", "平/平"),
    HF_DRAW_LOSE("da", "平/负"),

    HF_LOSE_WIN("ah", "负/胜"),
    HF_LOSE_DRAW("ad", "负/平"),
    HF_LOSE_LOSE("aa", "负/负"),

    // Score
    SCORE_WIN_ONE_ZERO("0100", "1:0"),
    SCORE_WIN_TWO_ZERO("0200", "2:0"),
    SCORE_WIN_TWO_ONE("0201", "2:1"),
    SCORE_WIN_THREE_ZERO("0300", "3:0"),
    SCORE_WIN_THREE_ONE("0301", "3:1"),

    SCORE_WIN_THREE_TWO("0302", "3:2"),
    SCORE_WIN_FOUR_ZERO("0400", "4:0"),
    SCORE_WIN_FOUR_ONE("0401", "4:1"),
    SCORE_WIN_FOUR_TWO("0402", "4:2"),
    SCORE_WIN_FIVE_ZERO("0500", "5:0"),

    SCORE_WIN_FIVE_ONE("0501", "5:1"),
    SCORE_WIN_FIVE_TWO("0502", "5:2"),
    SCORE_WIN_OTHERS("-1-h", "胜其它"),

    SCORE_DRAW_ZERO("0000", "0:0"),
    SCORE_DRAW_ONE("0101", "1:1"),
    SCORE_DRAW_TWO("0202", "2:2"),
    SCORE_DRAW_THREE("0303", "3:3"),
    SCORE_DRAW_OTHERS("-1-d", "平其它"),

    SCORE_LOSE_ZERO_ONE("0001", "0:1"),
    SCORE_LOSE_ZERO_TWO("0002", "0:2"),
    SCORE_LOSE_ONE_TWO("0102", "1:2"),
    SCORE_LOSE_ZERO_THREE("0003", "0:3"),
    SCORE_LOSE_ONE_THREE("0103", "1:3"),

    SCORE_LOSE_TWO_THREE("0203", "2:3"),
    SCORE_LOSE_ZERO_FOUR("0004", "0:4"),
    SCORE_LOSE_ONE_FOUR("0104", "1:4"),
    SCORE_LOSE_TWO_FOUR("0204", "2:4"),
    SCORE_LOSE_ZERO_FIVE("0005", "0:5"),

    SCORE_LOSE_ONE_FIVE("0105", "1:5"),
    SCORE_LOSE_TWO_FIVE("0205", "2:5"),
    SCORE_LOSE_OTHERS("-1-a", "负其它"),

    ;

    public static List<OddsType> getNoneSpreadWDLTypes() {
        return Arrays.asList(new OddsType[]{
          WIN,
          DRAW,
          LOSE
        });
    }

    public static List<OddsType> getSpreadWDLTypes() {

        return Arrays.asList(new OddsType[]{
                SPREAD_WIN,
                SPREAD_DRAW,
                SPREAD_LOSE
        });
    }


    public static List<OddsType> getScoreTypes() {
        return Arrays.asList(new OddsType[]{
                SCORE_WIN_ONE_ZERO,
                SCORE_WIN_TWO_ZERO,
                SCORE_WIN_TWO_ONE,
                SCORE_WIN_THREE_ZERO,
                SCORE_WIN_THREE_ONE,

                SCORE_WIN_THREE_TWO,
                SCORE_WIN_FOUR_ZERO,
                SCORE_WIN_FOUR_ONE,
                SCORE_WIN_FOUR_TWO,
                SCORE_WIN_FIVE_ZERO,

                SCORE_WIN_FIVE_ONE,
                SCORE_WIN_FIVE_TWO,
                SCORE_WIN_OTHERS,

                SCORE_DRAW_ZERO,
                SCORE_DRAW_ONE,
                SCORE_DRAW_TWO,
                SCORE_DRAW_THREE,
                SCORE_DRAW_OTHERS,

                SCORE_LOSE_ZERO_ONE,
                SCORE_LOSE_ZERO_TWO,
                SCORE_LOSE_ONE_TWO,
                SCORE_LOSE_ZERO_THREE,
                SCORE_LOSE_ONE_THREE,

                SCORE_LOSE_TWO_THREE,
                SCORE_LOSE_ZERO_FOUR,
                SCORE_LOSE_ONE_FOUR,
                SCORE_LOSE_TWO_FOUR,
                SCORE_LOSE_ZERO_FIVE,

                SCORE_LOSE_ONE_FIVE,
                SCORE_LOSE_TWO_FIVE,
                SCORE_LOSE_OTHERS,
        });
    }

    public static List<OddsType> getTotalGoalsTypes() {
        return Arrays.asList(new OddsType[] {
                GOAL_ZERO,
                GOAL_ONE,
                GOAL_TWO,
                GOAL_THREE,

                GOAL_FOUR,
                GOAL_FIVE,
                GOAL_SIX,
                GOAL_SEVEN_PLUS,
        });
    }

    public static List<OddsType> getHalfFullTypes() {
        return Arrays.asList(new OddsType[] {
                // HALF FULL
                HF_WIN_WIN,
                HF_WIN_DRAW,
                HF_WIN_LOSE,

                HF_DRAW_WIN,
                HF_DRAW_DRAW,
                HF_DRAW_LOSE,

                HF_LOSE_WIN,
                HF_LOSE_DRAW,
                HF_LOSE_LOSE,
        });
    }

    private String code;
    private String description;

    OddsType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static OddsType fromCode(String code) {
        switch (code) {
            case "s0":
                return GOAL_ZERO;
            case "s1":
                return GOAL_ONE;
            case "s2":
                return GOAL_TWO;
            case "s3":
                return GOAL_THREE;
            case "s4":
                return GOAL_FOUR;
            case "s5":
                return GOAL_FIVE;
            case "s6":
                return GOAL_SIX;
            case "s7":
                return GOAL_SEVEN_PLUS;

            case "sh":
                return SPREAD_WIN;
            case "sd":
                return SPREAD_DRAW;
            case "sa":
                return SPREAD_LOSE;

            case "h":
                return WIN;
            case "d":
                return DRAW;
            case "a":
                return LOSE;

            case "hh":
                return HF_WIN_WIN;
            case "hd":
                return HF_WIN_DRAW;
            case "ha":
                return HF_WIN_LOSE;

            case "dh":
                return HF_DRAW_WIN;
            case "dd":
                return HF_DRAW_DRAW;
            case "da":
                return HF_DRAW_LOSE;

            case "ah":
                return HF_LOSE_WIN;
            case "ad":
                return HF_LOSE_DRAW;
            case "aa":
                return HF_LOSE_LOSE;

            case "0100":
                return SCORE_WIN_ONE_ZERO;
            case "0200":
                return SCORE_WIN_TWO_ZERO;
            case "0201":
                return SCORE_WIN_TWO_ONE;
            case "0300":
                return SCORE_WIN_THREE_ZERO;
            case "0301":
                return SCORE_WIN_THREE_ONE;
            case "0302":
                return SCORE_WIN_THREE_TWO;
            case "0400":
                return SCORE_WIN_FOUR_ZERO;
            case "0401":
                return SCORE_WIN_FOUR_ONE;
            case "0402":
                return SCORE_WIN_FOUR_TWO;
            case "0500":
                return SCORE_WIN_FIVE_ZERO;
            case "0501":
                return SCORE_WIN_FIVE_ONE;
            case "0502":
                return SCORE_WIN_FIVE_TWO;
            case "-1-h":
                return SCORE_WIN_OTHERS;

            case "0000":
                return SCORE_DRAW_ZERO;
            case "0101":
                return SCORE_DRAW_ONE;
            case "0202":
                return SCORE_DRAW_TWO;
            case "0303":
                return SCORE_DRAW_THREE;
            case "-1-d":
                return SCORE_DRAW_OTHERS;

            case "0001":
                return SCORE_LOSE_ZERO_ONE;
            case "0002":
                return SCORE_LOSE_ZERO_TWO;
            case "0102":
                return SCORE_LOSE_ONE_TWO;
            case "0003":
                return SCORE_LOSE_ZERO_THREE;
            case "0103":
                return SCORE_LOSE_ONE_THREE;
            case "0203":
                return SCORE_LOSE_TWO_THREE;
            case "0004":
                return SCORE_LOSE_ZERO_FOUR;
            case "0104":
                return SCORE_LOSE_ONE_FOUR;
            case "0204":
                return SCORE_LOSE_TWO_FOUR;
            case "0005":
                return SCORE_LOSE_ZERO_FIVE;
            case "0105":
                return SCORE_LOSE_ONE_FIVE;
            case "0205":
                return SCORE_LOSE_TWO_FIVE;
            case "-1-a":
                return SCORE_LOSE_OTHERS;
            default:
                throw new IllegalArgumentException("Invalid code: " + code);
        }
    }

} // end enum OddsType