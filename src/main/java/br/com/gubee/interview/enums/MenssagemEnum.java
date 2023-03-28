package br.com.gubee.interview.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MenssagemEnum {
    NOT_FOUND("No record was found with this parameter"),
    INTERNAL_ERROR("There was an error trying to perform the operation");


    private final String description;
}