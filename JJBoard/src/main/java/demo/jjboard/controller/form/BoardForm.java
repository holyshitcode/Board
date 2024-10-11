package demo.jjboard.controller.form;

import lombok.Data;

@Data
public class BoardForm {
    private Long boardId;
    private String boardTitle;
    private String boardWriter;
    private String boardContent;
    private String boardGeneralChat;

}
