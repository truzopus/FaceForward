import java.io.File;


public class WebcamIO {

    int COUNT = 1;

    public WebcamIO() {

    }

    public File getImage() {
        File image = null;
        try {


            File dir = new File("/Users/alansmacbook/Desktop/BizHacks-2020/resources");
            String cmd = "imagesnap 0.png";

            Process test = Runtime.getRuntime().exec(cmd,null,dir);
            test.waitFor();
            image = new File("../resources/0.png");


            COUNT++;
//            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Snap Error");
            throw new Error("You must allow the webcam to work!");
        }
        return image;
    }

    public int getCount(){
        return COUNT-1;
    }
}
