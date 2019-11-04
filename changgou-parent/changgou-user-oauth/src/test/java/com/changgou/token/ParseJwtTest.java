package com.changgou.token;

import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

/*****
 * @Author: www.itheima
 * @Date: 2019/7/7 13:48
 * @Description: com.changgou.token
 *  使用公钥解密令牌数据
 ****/
public class ParseJwtTest {

    /***
     * 校验令牌
     */
    @Test
    public void testParseToken(){
        //令牌
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6IlJPTEVfVklQLFJPTEVfVVNFUiIsIm5hbWUiOiJpdGhlaW1hIiwiaWQiOiIxIn0.a_g7G-TsaGhKn9G_MG8DdCXi42hNuZaMHHtq88Icz3tnU0KuHPA98sAeLd7zCe3upJspKGWs3_HVWUmHctC3Yt_nyuut_5HDxgqTsHur8l28ns1yyoPBUIJrZFKma_hT_47DORm2OeinQXR3chDhRIF_35XD2Zh9Or3WMwLSCvxH7jgbWbyD2HJwhQ4FNBDIH2D1eHm67zJULdAugckcCzHpqvjp_OTxQUKhNlOI2mcDIf0JDy9Wk62v-ToF4ZbsANvnTzQNjNiNrzN24yy21ZvDmDr_KJPHaKO7CBoqQ1qlpPuwaM-pmpV3oyz4fFEA2vJ425O-dfj23tcADeT5hg";

        //公钥
        String publickey ="-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArrkxaLarn5wqLZT1XxzM+fRPmEqshNUmjEnzOmZ46gsfDsPeOIq7S4q3Vj784BH9gQydS8b7adEWmYxdrVPLHwteKAsn/6WeKs+PHw1ZP53Gy+/lCML2zTgKUkCN5GtW2K0x72bcv5wE7eA11WkHRe7iDIgK+0Ri/GU1WbW/2EPVKAZFS+H9FJL63NeU07dS7q8ZGZXkv3dhcE0ghPIzmYNEKI0x86AW7tnPK+bDH98G6SZOmTa+ugD7fEP6dMYLhTvCZhKuAcI/RIMiYPcbC/2jDJdQC9xryVSmHrElcHvph7YR/dXCpMMwfOHoEcFlWp9r98T6T2Nu7uB813NwwQIDAQAB-----END PUBLIC KEY-----";

        //校验Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));

        //获取Jwt原始内容 载荷
        String claims = jwt.getClaims();
        System.out.println(claims);
        //jwt令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }
}
