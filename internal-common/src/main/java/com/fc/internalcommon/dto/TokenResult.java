package com.fc.internalcommon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResult {

    private String phone;

    private String identity;
}
