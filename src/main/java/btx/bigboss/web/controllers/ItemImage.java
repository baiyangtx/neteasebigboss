package btx.bigboss.web.controllers;

import btx.bigboss.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by BaiyangTX on 2017/1/31.
 */
@Controller
public class ItemImage {

    @Value("${bigboss.imgpath}" )
    private String imgSavePath = "D:/bigboss" ;


    @RequestMapping("/itemimage/{imageid}")
    public void image(
            @PathVariable("imageid") String imageid ,
            HttpServletResponse res
    ) throws IOException {
        File file = new File(imgSavePath + File.separator + imageid) ;
        if(!file.exists()){
            throw new ResourceNotFoundException();
        }

        res.setHeader("content-type","application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + imageid );
        FileInputStream fis=new FileInputStream(file);
        res.setContentLengthLong(file.length());

        OutputStream out = res.getOutputStream() ;
        byte[] buff = new byte[1024] ;
        int bytes;
        while ( (bytes=fis.read(buff)) != -1 ){
            out.write(buff,0,bytes);
        }
        out.close();
        fis.close();
    }
}
