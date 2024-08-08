package demo.jjboard.controller.form;

import lombok.Data;

@Data
public class BoardCond {

    private String name;

    private String memberName;

    private Integer hitCount;

    private Integer recommendation;

    private Integer notRecommendation;

    public BoardCond() {
    }

    public BoardCond(String name, String memberName, Integer hitCount, Integer recommendation, Integer notRecommendation) {
        this.name = name;
        this.memberName = memberName;
        this.hitCount = hitCount;
        this.recommendation = recommendation;
        this.notRecommendation = notRecommendation;
    }
}
