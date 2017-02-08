package btx.bigboss.utils;

/**
 * Created by BaiyangTX on 2017/2/6.
 */
public class NetworkUtils {

    public static boolean isUrl(String url ){
        return !url.isEmpty() && url.startsWith("http");
    }
}
