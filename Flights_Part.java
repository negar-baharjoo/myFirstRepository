package AirLineWithFiles;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.Scanner;

public class Flights_Part {
    int index = 0 ;

    RandomAccessFile flights = new RandomAccessFile("flights.dat" , "rw") ;

    String[][] randomFlights; // لیست پروازهای رندوم بعداز خواندن از فایل ، درین ماتریس قرار میگیرد

    Random radooooom = new Random() ;

    String[] finalFlight ;//پروازی که قابلیت خریده شدن را دارد( بعد از سرچ) درن ارایه قرار میگیرد

    private String[] cities = new String[]{"tehran" , "shiraz" , "kerman", "isfahan" , "yazd" , "mashhad"} ;

    private String[] days = new String[]{"saturday" , "sunday" , "monday", "tuesday"} ;

    private String[] times = new String[]{"8" , "10" , "18" , "20"} ;

    Scanner inputFlight = new Scanner(System.in) ;

    public Flights_Part() throws IOException { // با فراخوانی شدن کانستراکتور، لیست رندوم در فایل نوشته میشود تا در طول برنامه ثابت باقی بماند

        writeInfoOfFlightsInFlights();
    }

    public RandomAccessFile getFlights() {
        return flights;
    }

    public void setFlights(RandomAccessFile flights) {
        this.flights = flights;
    }

    public String[][] getRandomFlights() {
        return randomFlights;
    }

    public void setRandomFlights(String[][] randomFlights) throws IOException {
        this.randomFlights = randomFlights ;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String[] getFinalFlight() {
        return finalFlight;
    }

    public void setFinalFlight(String[] finalFlight) {
        this.finalFlight = finalFlight;
    }

    public void writeInfoOfFlightsInFlights() throws IOException { // براس ساخت لیست رندوم و ثبت در فایل
        flights.seek(0);
        String id ;
        String from ;
        String to ;
        String day ;
        String time ;

        for (int i = 0; i < 10 ; i++) { // 10 بار روند رو تکرار میکنم چون میخوام 10 تا پرواز داشته باشیم

            id = "0" + String.valueOf(radooooom.nextInt(899) + 100) ;
            flights.writeChars(fixSize(id));
            from = cities[radooooom.nextInt(6)] ;
            flights.writeChars(fixSize(from));
            to = cities[radooooom.nextInt(6)] ;
            while (from.equals(to)){
                to = cities[radooooom.nextInt(6)] ;
            }
            flights.writeChars(fixSize(to));
            day = days[radooooom.nextInt(4)] ;
            flights.writeChars(fixSize(day));
            time = times[radooooom.nextInt(4)] ;
            flights.writeChars(fixSize(time));
        }
    }

    public String fixSize(String name){// رساندن سایز رشته ها به 20
        String str= name;
        if(name.length()< 20){
            for (int i = 0 ; i < 20 - name.length() ; i++) {
                str+=" ";
            }
            return str;
        }else {
            return name.substring(0,20);
        }
    }

    public String exitFromFix() throws IOException {// حذف اسپیس های اضافه شده
        String newStr = "" ;
        for (int i = 0; i < 20 ; i++) {
            newStr += flights.readChar() ;
        }
        return newStr.trim() ;
    }

    public String[][] readInfoOfFlightsFromFlights() throws IOException {// خواندن رکورد از روی فایل
        String[][] newMat = new String[10][5] ;
        for (int i = 0; i < 10; i++) { // ماتریسی با هرخانه با مقدار " " تعریف میکنیم
            for (int j = 0; j < 5; j++) {
                newMat[i][j] = " " ;
            }
        }
        flights.seek(0);
        for (int i = 0; i < 10 ; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 20; k++) {
                    newMat[i][j] += flights.readChar() ;// فایل را خوتنده و در ماتریس تعریف شده در متد میریزیم
                }
                newMat[i][j] = newMat[i][j].trim() ;// اسپیس ها را حذف میکنیم ( همون اسپیس هایی که برای رساندن سایز به 20 استفاده شدن)
            }
        }
        this.randomFlights = newMat ;// اماتریس شناخته شده در کل کلاس، ماتریس randomFlights است پس ماتریس پر شده را در این ماتریس قرار میدهیم تا در هرجایی از کلاس و حتی در دیگر کلاس ها
        //مورد استفاده قرار گیرند
        return randomFlights ;
    }
    public void showList() throws IOException { // برای نمایش پرواز ها
        System.out.println(">>>>>>---------- list flights ----------<<<<<<");
        System.out.println();
        // چاپ سر لیست های مرتبط به هر ستون
        System.out.printf("%19s %19s %19s %19s %19s","--- ID ---", "--- FROM ---" , "--- TO ---" , "--- DAY ---" , "--- TIME ---");
        System.out.println("\n");
        randomFlights = readInfoOfFlightsFromFlights() ;// خواندن از روی فایل
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.printf("%20s" ,randomFlights[i][j]);
            }
            System.out.println();
        }
    }
    public void searchFlights() throws IOException { // برای سرچ
        // در هر مرحله پارامترهای مورد نظر چک میشوند
        int counter = 0 ;
        finalFlight = new String[5] ;
        this.randomFlights = readInfoOfFlightsFromFlights() ; // رکورد را از فایل میخونیم
        System.out.println(">>>>>> Search flights <<<<<<");
        System.out.println();
        System.out.println("Enter your flight's info to check ;)");
        System.out.println("From :");
        String from = inputFlight.next() ;
        for (int i = 0; i < 10 ; i++) {
            if (randomFlights[i][1].equals(from)){
                counter++ ;
                // اگر پرواز مطابق با مبدا مد نظر کاربر موجود باشد به مقدار counter یکی اضافه میشود
                System.out.printf("%20s %20s %20s %20s %20s" , randomFlights[i][0]
                        , randomFlights[i][1] , randomFlights[i][2] , randomFlights[i][3]
                        , randomFlights[i][4]);
                System.out.println();
            }
        }
        if (counter != 0) {// اگر counter صفر باشد ینی پروازی با این اطلاعات وجود ندارد پس متغیری را با مقداری خاص برمیگردانیم تا برنامه در مسیر درست قرار گیرد
            counter = 0;
            System.out.println("To :");
            String to = inputFlight.next();
            for (int i = 0; i < 10; i++) {
                if (randomFlights[i][1].equals(from)) {
                    if (randomFlights[i][2].equals(to)) {
                        counter++;
                        System.out.printf("%20s %20s %20s %20s %20s", randomFlights[i][0]
                                , randomFlights[i][1], randomFlights[i][2], randomFlights[i][3]
                                , randomFlights[i][4]);
                        System.out.println();
                    }

                }
            }
            if (counter != 0) {
                counter = 0;
                System.out.println("Day of departure :");
                String day = inputFlight.next();
                for (int i = 0; i < 10; i++) {
                    if (randomFlights[i][1].equals(from)) {
                        if (randomFlights[i][2].equals(to)) {
                            if (randomFlights[i][3].equals(day)) {
                                counter++;
                                System.out.printf("%20s %20s %20s %20s %20s", randomFlights[i][0]
                                        , randomFlights[i][1], randomFlights[i][2], randomFlights[i][3]
                                        , randomFlights[i][4]);
                                System.out.println();
                            }
                        }
                    }
                }
                if (counter != 0) {
                    counter = 0;
                    System.out.println("Time of departure :");
                    String time = inputFlight.next();
                    for (int i = 0; i < 10; i++) {
                        if (randomFlights[i][1].equals(from)) {
                            if (randomFlights[i][2].equals(to)) {
                                if (randomFlights[i][3].equals(day)) {
                                    if (randomFlights[i][4].equals(time)) {
                                        // این پرواز قابل خریدن است پس ان را در ارایه ی finalFlight سیو میکنیم تا در صورت تمایل کاربر به خرید، ان را در فایل یوزرز سیو کنیم
                                        counter++;
                                        finalFlight[0] = randomFlights[i][0];
                                        finalFlight[1] = randomFlights[i][1];
                                        finalFlight[2] = randomFlights[i][2];
                                        finalFlight[3] = randomFlights[i][3];
                                        finalFlight[4] = randomFlights[i][4];
                                        // چاپ پرواز نهایی
                                        System.out.printf("%20s %20s %20s %20s %20s", randomFlights[i][0]
                                                , randomFlights[i][1], randomFlights[i][2], randomFlights[i][3]
                                                , randomFlights[i][4]);
                                        System.out.println();
                                        index = 5 ;
                                    }
                                }
                            }
                        }
                    }
                    System.out.println();
                    System.out.println(">>>>>>>>>----------------------------------------<<<<<<<<<<<");
                }else{
                    //در هر مرحله در صورت وجود نداشتن پارامتر مدنظر کاربر، از متد خارج شده و پیامی مبنی بر وجود نداشتن پرواز نمایش میدهیم
                    System.out.println("this fight is not available !");
                    index = -5 ;
                }
            }else{
                System.out.println("this fight is not available !");
                index = -5 ;
            }
        }else{
            System.out.println("this fight is not available !");
            index = -5 ;
        }
    }
}
