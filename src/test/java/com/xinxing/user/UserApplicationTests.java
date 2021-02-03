package com.xinxing.user;

import cn.hutool.json.JSONUtil;
import com.xinxing.user.dto.LoginDto;
import com.xinxing.user.dto.TokenDto;
import com.xinxing.user.dto.UserDto;
import com.xinxing.user.service.UserService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.Parameters;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
class UserApplicationTests {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private UserService userService;
    private String email = "123@163.com";
    private String password = "12345678t";
    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)).build();
    }
    @Test
    public void heartbeatTest() throws Exception {
        this.mockMvc.perform(get("/heartbeat")).andExpect(status().isOk())
                .andDo(document("heartbeat"));
    }
    @Test
    public void userTest() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setFirst("xing");
        userDto.setLast("xin");
        userDto.setEmail(email);
        userDto.setPassword(password);
        this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(JSONUtil.toJsonStr(userDto)))
                .andExpect(status().isOk())
                .andDo(document("create-user"));

        LoginDto dto = new LoginDto(email,password);
        this.mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON).content(JSONUtil.toJsonStr(dto)))
                .andExpect(status().isOk())
                .andDo(document("user-login"));

        MultiValueMap<String, String> params = new Parameters();
        params.add("token","259512974573044736");
        this.mockMvc.perform(get("/user").params(params)).andExpect(status().isOk())
                .andDo(document("user"));

        TokenDto token = new TokenDto("259512974573044736");
        this.mockMvc.perform(post("/user/logout").content(JSONUtil.toJsonStr(token)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andDo(document("user-logout"));
    }


}
