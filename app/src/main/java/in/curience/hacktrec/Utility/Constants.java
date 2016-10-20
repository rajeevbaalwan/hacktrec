package in.curience.hacktrec.Utility;


import com.squareup.okhttp.MediaType;

/**
 * Created by Brekkishhh on 24-08-2016.
 */
public class Constants {

    public static final String IS_SENDED = "sended";
    public static final String IS_RECEIVED = "received";
    public static final String SOCKETS_SERVER = "http://192.168.43.121:3000/";
    public static final int SENDER = 0;
    public static int MODE = 0;
    public static final int RECEIVED = 1;
    public static final String TYPE_MESSAGE_TEXT  = "text_message";
    public static final String TYPE_MESSAGE_IMAGE = "image_message";
    public static final String TYPE_MESSAGE_VIDEO  = "video_message";
    public static final int RQ_GALLERY_IMAGE = 574;
    public static final String EVENT_SEND_MESSAGE = "send_message";
    public static final String EVENT_RECEIVE_MESSAGE = "receive_message";
    public static final String EVENT_GET_MENU = "menu_request";
    public static final String EVENT_MENU_RESPONSE = "menu_response";
    public static final String EVENT_SEND_ORDER = "send_order";
    public static final String EVENT_PAYMENT_REQUEST = "user_payment_request";
    public static final MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String APP_ID = "de63a08b";
    public static final String API_KEY = "9d459e7736fba48ae331b52869bf1beb";
    public static final String ZOMATO_API_KEY = "b8e14f8451c9f9813135a7193b3793bb";
    public static final String EVENT_ORDER_COMPLETED = "order_completed";




}
