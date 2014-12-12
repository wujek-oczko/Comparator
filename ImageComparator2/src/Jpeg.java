import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by marcinja on 26/11/2014.
 */
public class Jpeg {

    String image_name;
    String class_name;
    String test_name;
    String user_login;
    String browser_shortcut;
    String timestamp;
    String status;
    String full_path;
    String build_ID;
    String env_ID_Env;
    String run_ID_Run;

    public Jpeg() {

    }

    public Jpeg(File file) throws IOException {
        String path = file.getCanonicalPath();
        String parent = file.getParent();

        String[] parts = parent.split("\\\\");

        Pattern p = Pattern.compile("(?:.*)\\\\(.*)\\((.*)\\)\\.jpg");
        Matcher m = p.matcher(path);
        m.matches();
//        System.out.println("Full path " + m.group(0)); //full path
//        System.out.println("image name " + m.group(1)); //image name
//        System.out.println("time stamp " + m.group(2)); //also timestamp

        Pattern p1 = Pattern.compile("(.*)_(.*)_(.*)_(.*)");
        Matcher m1 = p1.matcher(m.group(1));
        m1.matches();
//        System.out.println("Class name " + m1.group(1)); //class
//        System.out.println("Test name " + m1.group(2)); //test
//        System.out.println("User login " + m1.group(3)); //login
//        System.out.println("Browser " + m1.group(4)); //browse

        String environment = parts[parts.length -4].replace("MSd_","");
//        System.out.println("Environment " + environment); //environment

        String build_number = parts[parts.length -5];
        Pattern pattern = Pattern.compile("(.*)_(.*)_(.*)_(.*)");
        Matcher bn = pattern.matcher(build_number);
        bn.matches();
//        System.out.println("Test run " + bn.group(4)); //run testow

        this.image_name = m.group(1);
        this.class_name = m1.group(1);
        this.test_name = m1.group(2);
        this.user_login = m1.group(3);
        this.browser_shortcut = m1.group(4);
        this.timestamp = m.group(2);
        this.status = "NEW";
        this.full_path = m.group(0);
        this.build_ID = "1.4.1.1777389";
        this.env_ID_Env = environment;
        this.run_ID_Run = bn.group(4);

    }


//    public static void main(String[] args) throws IOException {
//        Jpeg jpeg = new Jpeg(new File("C:\\Users\\marcinja\\Desktop\\doTestow - Copy (2)\\20141018233554_dcadmin@RTNIV12-VM3_4540_30\\MSd_VER\\MSd-1191\\FF\\0\\HelpHCPTest_helpHCPNavigateToIFU_hcpgben2_CH(2014-12-03 16-07-18).jpg"));
//    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public void setBrowser_shortcut(String browser_shortcut) {
        this.browser_shortcut = browser_shortcut;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFull_path(String full_path) {
        this.full_path = full_path;
    }

    public void setBuild_ID(String build_ID) {
        this.build_ID = build_ID;
    }

    public void setEnv_ID_Env(String env_ID_Env) {
        this.env_ID_Env = env_ID_Env;
    }

    public void setRun_ID_Run(String run_ID_Run) {
        this.run_ID_Run = run_ID_Run;
    }


}