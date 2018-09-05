package dylan.io.apollobet.models;

public enum OddsType {

    // Total goals
    GOAL_ZERO("00", "0球"),
    GOAL_ONE("01", "1球"),
    GOAL_TWO("02", "2球"),
    GOAL_THREE("03", "3球"),

    GOAL_FOUR("04", "4球"),
    GOAL_FIVE("05", "5球"),
    GOAL_SIX("06", "6球"),
    GOAL_SEVEN_PLUS("07", "7+球"),

    // 1 x 2 odds win/draw/lose
    LOSE("14", "负"),
    DRAW("15", "平"),
    WIN("16", "胜"),

    // spread odds win/draw/lose
    SPREAD_LOSE("10", "让负"),
    SPREAD_DRAW("11", "让平"),
    SPREAD_WIN("13", "让胜"),

    // HALF FULL
    HF_WIN_WIN("20", "胜/胜"),
    HF_WIN_DRAW("21", "胜/平"),
    HF_WIN_LOSE("22", "胜/负"),

    HF_DRAW_WIN("23", "平/胜"),
    HF_DRAW_DRAW("24", "平/平"),
    HF_DRAW_LOSE("25", "平/负"),

    HF_LOSE_WIN("26", "负/胜"),
    HF_LOSE_DRAW("27", "负/平"),
    HF_LOSE_LOSE("28", "负/负"),

    // Score
    SCORE_WIN_ONE_ZERO("30", "1:0"),
    SCORE_WIN_TWO_ZERO("31", "2:0"),
    SCORE_WIN_TWO_ONE("32", "2:1"),
    SCORE_WIN_THREE_ZERO("33", "3:0"),
    SCORE_WIN_THREE_ONE("34", "3:1"),

    SCORE_WIN_THREE_TWO("35", "3:2"),
    SCORE_WIN_FOUR_ZERO("36", "4:0"),
    SCORE_WIN_FOUR_ONE("37", "4:1"),
    SCORE_WIN_FOUR_TWO("38", "4:2"),
    SCORE_WIN_FIVE_ZERO("39", "5:0"),

    SCORE_WIN_FIVE_ONE("40", "5:1"),
    SCORE_WIN_FIVE_TWO("41", "5:2"),
    SCORE_WIN_OTHERS("42", "胜其它"),

    SCORE_DRAW_ZERO("43", "0:0"),
    SCORE_DRAW_ONE("44", "1:1"),
    SCORE_DRAW_TWO("45", "2:2"),
    SCORE_DRAW_THREE("46", "3:3"),
    SCORE_DRAW_OTHERS("47", "平其它"),

    SCORE_LOSE_ZERO_ONE("48", "0:1"),
    SCORE_LOSE_ZERO_TWO("49", "0:2"),
    SCORE_LOSE_ONE_TWO("50", "1:2"),
    SCORE_LOSE_ZERO_THREE("51", "0:3"),
    SCORE_LOSE_ONE_THREE("52", "1:3"),

    SCORE_LOSE_TWO_THREE("53", "2:3"),
    SCORE_LOSE_ZERO_FOUR("54", "0:4"),
    SCORE_LOSE_ONE_FOUR("55", "1:4"),
    SCORE_LOSE_TWO_FOUR("56", "2:4"),
    SCORE_LOSE_ZERO_FIVE("57", "0:5"),

    SCORE_LOSE_ONE_FIVE("58", "1:5"),
    SCORE_LOSE_TWO_FIVE("59", "2:5"),
    SCORE_LOSE_OTHERS("60", "负其它"),;

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
            case "00":
                return GOAL_ZERO;
            case "01":
                return GOAL_ONE;
            case "02":
                return GOAL_TWO;
            case "03":
                return GOAL_THREE;
            case "04":
                return GOAL_FOUR;
            case "05":
                return GOAL_FIVE;
            case "06":
                return GOAL_SIX;
            case "07":
                return GOAL_SEVEN_PLUS;

            case "13":
                return SPREAD_WIN;
            case "11":
                return SPREAD_DRAW;
            case "10":
                return SPREAD_LOSE;

            case "16":
                return WIN;
            case "15":
                return DRAW;
            case "14":
                return LOSE;

            case "20":
                return HF_WIN_WIN;
            case "21":
                return HF_WIN_DRAW;
            case "22":
                return HF_WIN_LOSE;

            case "23":
                return HF_DRAW_WIN;
            case "24":
                return HF_DRAW_DRAW;
            case "25":
                return HF_DRAW_LOSE;

            case "26":
                return HF_LOSE_WIN;
            case "27":
                return HF_LOSE_DRAW;
            case "28":
                return HF_LOSE_LOSE;

            case "30":
                return SCORE_WIN_ONE_ZERO;
            case "31":
                return SCORE_WIN_TWO_ZERO;
            case "32":
                return SCORE_WIN_TWO_ONE;
            case "33":
                return SCORE_WIN_THREE_ZERO;
            case "34":
                return SCORE_WIN_THREE_ONE;
            case "35":
                return SCORE_WIN_THREE_TWO;
            case "36":
                return SCORE_WIN_FOUR_ZERO;
            case "37":
                return SCORE_WIN_FOUR_ONE;
            case "38":
                return SCORE_WIN_FOUR_TWO;
            case "39":
                return SCORE_WIN_FIVE_ZERO;
            case "40":
                return SCORE_WIN_FIVE_ONE;
            case "41":
                return SCORE_WIN_FIVE_TWO;
            case "42":
                return SCORE_WIN_OTHERS;

            case "43":
                return SCORE_DRAW_ZERO;
            case "44":
                return SCORE_DRAW_ONE;
            case "45":
                return SCORE_DRAW_TWO;
            case "46":
                return SCORE_DRAW_THREE;
            case "47":
                return SCORE_DRAW_OTHERS;

            case "48":
                return SCORE_LOSE_ZERO_ONE;
            case "49":
                return SCORE_LOSE_ZERO_TWO;
            case "50":
                return SCORE_LOSE_ONE_TWO;
            case "51":
                return SCORE_LOSE_ZERO_THREE;
            case "52":
                return SCORE_LOSE_ONE_THREE;
            case "53":
                return SCORE_LOSE_TWO_THREE;
            case "54":
                return SCORE_LOSE_ZERO_FOUR;
            case "55":
                return SCORE_LOSE_ONE_FOUR;
            case "56":
                return SCORE_LOSE_TWO_FOUR;
            case "57":
                return SCORE_LOSE_ZERO_FIVE;
            case "58":
                return SCORE_LOSE_ONE_FIVE;
            case "59":
                return SCORE_LOSE_TWO_FIVE;
            case "60":
                return SCORE_LOSE_OTHERS;
            default:
                throw new IllegalArgumentException("Invalid code: " + code);
        }
    }
} // end enum OddsType