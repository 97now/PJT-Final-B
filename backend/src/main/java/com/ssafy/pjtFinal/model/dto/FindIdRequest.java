package com.ssafy.pjtFinal.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FindIdRequest {
    private String findBy;
    private String userNickName;
    private String userData;
}
