package btx.bigboss.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by BaiyangTX on 2017/2/6.
 */
public class FileUtils {


    public static String handleUploadImageFile(MultipartFile file, int userid , String imgSavePath)
            throws IOException {
        String originalFilename = file.getOriginalFilename() ;
        String filename = userid + "" + System.currentTimeMillis() + originalFilename ;
        filename = MD5Utils.string2MD5(filename) ;

        File savePath = new File(imgSavePath);
        if (!savePath.exists()){
            if(!savePath.mkdirs() ){
                throw new IllegalStateException("mkdir for " + imgSavePath + " failed") ;
            }
        }

        File imgfile = new File(imgSavePath + File.separator + filename );
        file.transferTo(imgfile);

        return filename ;
    }


}
