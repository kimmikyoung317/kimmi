package greenart.festival.member.OAuth;

import greenart.festival.member.entity.Social;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class NaverUserDetails implements OAuth2UserInfo{

    private Map<String, Object> attr;

    @Override
    public String getProviderId() {
        return ((Map)attr.get("response")).get("sub").toString();
    }

    @Override
    public Social getProvider() {
        return Social.NAVER;
    }

    @Override
    public String getEmail() {
        System.out.println(attr);
        return ((Map)attr.get("response")).get("email").toString();
    }

    @Override
    public String getName() {
        return ((Map)attr.get("response")).get("name").toString();
    }
}
