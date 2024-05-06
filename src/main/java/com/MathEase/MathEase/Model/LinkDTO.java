package com.MathEase.MathEase.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LinkDTO {

    private Long linkId;
    private String link;
    private String url;

}
