package com.ticlivetest.control;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import com.ticlivetest.entity.image.Image;
import com.ticlivetest.entity.image.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    HttpServletRequest request;

    @GetMapping("/modal/{id}")
    public String modal1(@PathVariable int id){
        return "test/modal"+id;
    }

    @GetMapping("/user")
    @ResponseBody
    public ResponseEntity<Principal> user(){
        Principal principal = request.getUserPrincipal();
        return principal!=null?
            ResponseEntity.status(HttpStatus.OK).body(principal):
            ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/upload")
    public String uploadTestForm(){
        return "test/postForm";
    }
    @PostMapping("/upload")
    public String uploadTest(@RequestPart(name = "sendingImage") Image image,@RequestPart String b_title ){

        System.out.println("로그 시작");
        log.info("image : "+image);
        log.info("title : "+b_title);
        System.out.println("로그 끝");

        Image saved = imageRepository.save(image);
        log.info("저장된 이미지 : "+saved);


        // PMemberVO user_vo = (PMemberVO) session.getAttribute("id");
		// if(user_vo==null) {
		// 	return "redirect:login_form";
		// }
		
		// vo.setB_ip(request.getRemoteAddr());
		
		// String webPath = "/resources/upload/board";
		// String savePath = request.getServletContext().getRealPath(webPath);
		
		// PMemberVO Logined_vo = (PMemberVO)session.getAttribute("id");
		// vo.setM_idx(Logined_vo.getM_idx());
		
		// MultipartFile file = vo.getB_file();
		// String filename = "no_file";
		
		// //파일처리
		// if(!file.isEmpty() || file == null) {
		// 	filename = file.getOriginalFilename();
			
		// 	File saveFile = new File(savePath,filename);
		// 	if(!saveFile.exists()) {
		// 		saveFile.mkdirs();
		// 	}else {
		// 		//동일파일명 방지
		// 		long time = System.currentTimeMillis();
				
		// 		int pointidx = filename.indexOf(".");
		// 		String b = filename.substring(pointidx,filename.length());
		// 		filename = filename.substring(0,pointidx);
				
		// 		filename = String.format("%s_%d",filename, time)+b;
				
		// 		saveFile = new File(savePath,filename);
		// 	}
		// 	try {
		// 		file.transferTo(saveFile);
		// 	} catch (Exception e) {
		// 		e.printStackTrace();
        // }

        return "test/postForm";
    }

}
