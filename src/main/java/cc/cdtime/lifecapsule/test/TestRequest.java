package cc.cdtime.lifecapsule.test;

import lombok.Data;

@Data
public class TestRequest {
    private String secretKey;
    private String content;
    private String iv;
}
