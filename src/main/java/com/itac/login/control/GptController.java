package com.itac.login.control;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/gpt")
@RequiredArgsConstructor
public class GptController {
    @PostMapping("/chatting")
    public String chatting(Principal principal, HttpServletRequest request, HttpServletResponse response)  throws Exception {
        String category = request.getParameter("category");
        String text = request.getParameter("text");
        if(text.getBytes().length >=1200){
            return "최대 입력 글자수를 초과하였습니다.";
        }
        
        String userid = request.getParameter("userid");

        String getResponseString = "";
        String postURL = "http://218.159.116.193:8899/chatgpt/chatform2";

        HttpPost post = new HttpPost(postURL);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("category", category));
        params.add(new BasicNameValuePair("text", text));
        params.add(new BasicNameValuePair("userid", userid));

        UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, "UTF-8");
        post.setEntity(ent);

        HttpClient client = new DefaultHttpClient();
        HttpResponse responsePOST = client.execute(post);

        BufferedReader reader = new BufferedReader(new  InputStreamReader(responsePOST.getEntity().getContent()), 2048);

        if (responsePOST != null) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(" line : " + line);
                sb.append(line);
            }
            getResponseString = sb.toString();
        }

        return getResponseString;
    }

}


