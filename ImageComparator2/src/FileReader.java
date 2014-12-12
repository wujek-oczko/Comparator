import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by marcinja on 26/11/2014.
 */
public class FileReader {

    public static final String PATH_1 = "C:\\Users\\Marcin\\Desktop\\moajpraca\\doTestow - Copy - Copy\\20141018233554_dcadmin@RTNIV12-VM3_4540_30";
//    public static final String PATH_2 = "C:\\Users\\marcinja\\Desktop\\doTestow - Copy - Copy\\20141107124936_dcadmin@RTNIV12-VM3_17772_62";

    public static void main (String args[]) throws IOException {

        List<Jpeg> listOfModelImages = DbConnector.selectFromModelImages();

        List<File> listOfLoadedFiles = getFiles(PATH_1);
        List<Jpeg> jpegs= convertToJpeg(listOfLoadedFiles);
//        jpegPrinter(jpegs1);

//        filePrinter(listOfFiles1);
//        filePrinter(listOfFiles2);

        //porownanie nazw
        List<Jpeg> insertSet = imageComparison4(listOfModelImages, jpegs);

        insertJpegToDB(insertSet);

//        for(PairOfPaths pairOfPaths : result){
//            System.out.print(pairOfPaths.filename1 +"  "+ pairOfPaths.filename2 + " ");
//            System.out.println(Comparator2.compareImages(pairOfPaths.path1, pairOfPaths.path2, 0));
//        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//METODY

    private static List<Jpeg> imageComparison4(List<Jpeg> listOfModelImages, List<Jpeg> jpegs) throws IOException {
        List<PairOfPaths3> result = getPairOfPaths3(listOfModelImages, jpegs);

        Boolean b;
        String str;
        BufferedWriter bw = new BufferedWriter(new FileWriter("file.html"));
        bw.write("<html><head><title>Papcie</title></head><body><font size =\"6\"> Report created by Ultra Image Comparator (R) </font><table border =\"1\" >");
        for(PairOfPaths3 pairOfPaths : result){

            b = Comparator2.compareImages(pairOfPaths.jpeg1.full_path, pairOfPaths.jpeg2.full_path, 0);
            str = String.valueOf(b);
            if(str.equals("false")) {
                System.out.println("if");

                bw.write("<tr bgcolor=\"#FF0000\"><td><a href=\"" +  pairOfPaths.jpeg1.full_path +  "\" >" + pairOfPaths.jpeg1.image_name +"</a></td>  " +
                        "<td><a href=\"" +  pairOfPaths.jpeg2.full_path +  "\" >"+ pairOfPaths.jpeg2.image_name + "</a></td> ");
                str = "<font color=\"blue\">" + str +"</font>";

                int index = jpegs.indexOf(pairOfPaths.jpeg2);
                jpegs.get(index).setStatus("DIFFERENT");

            }else {
                System.out.println("els");
                bw.write("<tr><td><a href=\"" +  pairOfPaths.jpeg1.full_path +  "\" >" + pairOfPaths.jpeg1.image_name +"</a></td>  " +
                        "<td><a href=\"" +  pairOfPaths.jpeg2.full_path +  "\" >"+ pairOfPaths.jpeg2.image_name + "</a></td> ");
                str = "<font color=\"green\">" + str +"</font>";

                int index = jpegs.indexOf(pairOfPaths.jpeg2);
                jpegs.get(index).setStatus("IDENTICAL");

            }
            bw.write("<td><b>" + str + "</b></td></tr>");
            bw.newLine();
        }
        bw.write("</table></body></html>");
        bw.close();

        return jpegs;
    }

    private static List<PairOfPaths3> getPairOfPaths3(List<Jpeg> listOfFiles1, List<Jpeg> listOfFiles2) {
        List<PairOfPaths3> result = new ArrayList<>();
        for(Jpeg file1 : listOfFiles1){
            for(Jpeg file2 : listOfFiles2){
                if(file1.image_name.equals(file2.image_name)){
                    result.add(new PairOfPaths3(file1, file2));
                }
            }
        }
        return result;
    }

    public static class PairOfPaths3 {
        public Jpeg jpeg1;
        public Jpeg jpeg2;
        public PairOfPaths3(Jpeg jpeg1, Jpeg jpeg2){
            this.jpeg1 = jpeg1;
            this.jpeg2 = jpeg2;
        }
    }

    public static List<String> insertJpegToDB(List<Jpeg> jpegs){
        List<String> inserts = new ArrayList<>();
        for (Jpeg jpeg : jpegs){
            inserts.add("INSERT INTO loaded_images VALUES(\n" +
//                            "loaded_images_id_seq.nextval, \n" +
                            "NULL, \n" +
                            "'" + jpeg.image_name + "', \n" +
                            "'" + jpeg.class_name + "', \n" +
                            "'" + jpeg.test_name + "', \n" +
                            "'" + jpeg.user_login + "', \n" +
                            "(SELECT id_browser FROM browser WHERE browser_shortcut = '" + jpeg.browser_shortcut + "'), \n" +
                            "STR_TO_DATE('" + jpeg.timestamp + "', '%Y-%m-%d %H-%i-%S'), \n" +
                            "'" + jpeg.status + "', \n" +
                            "'" + jpeg.full_path + "', \n" +
                            "'" + jpeg.build_ID + "', \n" +
                            "(SELECT id_environment FROM environment WHERE env_shortcut = '" + jpeg.env_ID_Env + "'), \n" +
                            "(SELECT id_run FROM run WHERE run_number = " + jpeg.run_ID_Run + "))");
        }
        //this code is only for help debuging
        for (String insert : inserts) {
            System.out.println(insert);
//            DbConnector.insertSqlStatement(insert);
        }
        ///////////////////////////////////////
        DbConnector.insertSqlStatements(inserts);
        return inserts;

    }

    public static List<Jpeg> convertToJpeg(List<File> listOfFiles) throws IOException {
        List<Jpeg> helper = new ArrayList<Jpeg>();

        for(File iterator : listOfFiles){
            Jpeg jpeg = new Jpeg(iterator);
            helper.add(jpeg);
        }
        return (helper);
    }


    private static List<File> getFiles(String path) {
        List<File> zwracane = new ArrayList<>();//Wynik

        File folder = new File(path);
        File[] fList = folder.listFiles();      //Pomocnik do kroczenia po folderach
        for (File file : fList) {
            if (file.isFile() && file.getName().endsWith(".jpg") && !file.getName().contains("tear_down")) {
                zwracane.add(file);
            } else if (file.isDirectory()) {
                zwracane.addAll(getFiles(file.getPath()));
            }
        }
        return zwracane;
    }


    public static class ImageAndPath {
        public String path;
        public String filename;
        public ImageAndPath(String filename, String path ){
            this.path = path;
            this.filename = filename;
        }
    }

    private static void filePrinter(List<File> listOfFiles) {
        for (File file : listOfFiles) {
            if (file.isFile()) {
                System.out.println(file.getName().replaceAll("\\((.*?)\\)" , ""));
//                System.out.println(file.getParent());
                System.out.println(file.getPath());
            }
        }
    }

    private static void jpegPrinter(List<Jpeg> listOfFiles) {
        for (Jpeg file : listOfFiles) {
                System.out.println(file.image_name);
                System.out.println(file.full_path);
        }
    }

}
