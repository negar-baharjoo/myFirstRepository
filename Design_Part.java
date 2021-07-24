package AirLineWithFiles;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Design_Part {

    int index2 = 0 ;
    Scanner inputDe = new Scanner(System.in); // برای گرفتن اطلاعات از کاربر

    // ساخت سه نمونه از کلاس یوزر به نام های مذکور
    Users_Part userOfSite_Login2 ;
    Users_Part userOfSite_Login ;
    Users_Part userOfSite ;

    Flights_Part flightsOfSite ; // نمونه از کلاس فلایتس

    String userin3 ;
    String passin3 ;
    int indexInFile ;

    //کانستراکتور (اکتیویوت هایی از کلاس که فقط میخواهم یه بار ساخته شوند درین قسمت نیو میشوند )
    public Design_Part() throws IOException {

       flightsOfSite = new Flights_Part() ;
       userOfSite = new Users_Part() ;
       userOfSite_Login = new Users_Part() ;

    }
// متدی برای نمایش منوی اولیه که شامل لاگین و ثبت نام نیشود
    public void welcome_menu() throws IOException {

        String input  ;
        System.out.println(">>>>>>---------- Welcome to our site ----------<<<<<<");
        System.out.println("_ User panel :");
        System.out.println();
        System.out.println("_ Enter #1 to Register ;)");
        System.out.println("_ Enter #2 to login ;)");

        input = inputDe.next() ; // با گرفتن اطلاعات مربوطه برنامه را در مسیر درست قرار میدهیم
        switch (input){
            case "1" : // یعنی کاربر میخواهد ثبت نام کند
                register_Part();
                break;
            case "2" :// یعنی کاربر میخواهد وارد اکانتی که قبلا ثبت کرده بشود
                login_Part();
                break;
            default :// اگر کاربر ورودی غیر از 1 و 2 داد
                System.out.println("---------- input is not acceptable ! Try again !! ----------");
                System.out.println();
                welcome_menu();
        }
    }
    public void register_Part() throws IOException { // متدی برای ثبت نام

// ابتدا رمز ها را از کاربر میگیرم و فایل یوزرز رو چک میکنم تا رمزها از قبل انتخاب نشده باشند
        int c = 0 ;
        System.out.println(">>>>>>---------- Register new user ----------<<<<<<");
        System.out.println("_ Enter your username :");
        String inUser = inputDe.next() ;
        System.out.println("_ Enter your password :");
        String inPass = inputDe.next() ;
// مرحله چک کردن
        for (int i = 0; i < userOfSite.getUsers().length() / 2080 ; i++) {
           userOfSite.getUsers().seek(i * 2080);
           userOfSite.readInfoFromFileOfUsers();
           if (userOfSite.getUserName().equals(inUser)){
               if (userOfSite.getPassWord().equals(inPass)){
                   // اگر مطابق با رمزهای از پیش ثبت شده بود، به کاربر پیامی مینی بر تکرار روند ارائه میشود
                   System.out.println();
                   System.out.println(">>>>>>---- These passwords are not available for you !----<<<<<<");
                   System.out.println(">>>>>>---- Take another ;) ----<<<<<<");
                   System.out.println();
                   register_Part();
                   break;
               }
           }
        }
        // اگر رمزها از قبل ثبت نشده بودند، کاربر جدید را در فایل ذخیره میکنم و برنامه در جهت درست قرار میگیرد
        Users_Part userOfSite_Login2 = new Users_Part(inUser , inPass) ;
        userin3 = inUser ;
        passin3 = inPass ;
        userOfSite = userOfSite_Login2 ;
        userOfSite_Login = userOfSite_Login2 ;
        userOfSite.writeInfoInFileOfUsers();
        System.out.println(">>>>>>---- you signed in successfully !----<<<<<<");
        System.out.println();
        guidance_Panel();

    }
    public void login_Part() throws IOException {// متدی برای لاگین کاربر از قبل ثبت شذه
// ابتدا رمزهای مورد نظر کاربر از طریق کلاس اسکنر از او گرفته میشوند تا چک شود که ایا همچین کاربری داریم یا خیر
        int counter = 0 ;
        String inUserName ;
        String inPassWord ;
        System.out.println(">>>>>>----------- login part ----------<<<<<<");
        System.out.println("_ Enter your username that have choosen in register part :");
        inUserName = inputDe.next() ;
        System.out.println("_ Enter your password that have choosen in register part :");
        inPassWord = inputDe.next() ;
        // مرحله چک کردن
        for (int i = 0; i < userOfSite.getUsers().length()/ 2080 ; i++) {
            userOfSite.getUsers().seek(i * 2080);
            userOfSite.readInfoFromFileOfUsers();
            if (userOfSite.getUserName().equals(inUserName)){
                if (userOfSite.getPassWord().equals(inPassWord)){
                    counter++ ;
                    // اگر همچین کاربری در فایل ما وجود داشت به مقدار متغیر counter یکی اضافه میشود
                }
            }
        }
        if (counter == 0){ // اگر counter صفر باشد ینی کاربری با این خصوصیات ثبت نام نکرده بوده پس پیامی مبنی بر این، چاپ میشود
            System.out.println(">>>>>>---- you are not registered !!----<<<<<<");
            welcome_menu();
        }else{ // اگر counter صفر نباشد ینی کاربر ما وجود دارد و برنامه در مسیر درست قرار میگیرد
            System.out.println(">>>>>>>>>>--- welcome to our site dear user :) ---<<<<<<<<<<");
            Users_Part userOfSite_Login = new Users_Part(inUserName , inPassWord) ;
            userOfSite = userOfSite_Login ;
            userin3 = inUserName ;
            passin3 = inPassWord ;
            System.out.println();
            guidance_Panel();
        }

    }
    public void guidance_Panel() throws IOException { // متدی برای پنل اصلی
// ابتدا ، نمایش منو
        String input ;
        System.out.println(">>>>>>---- welcome to guidance panel ----<<<<<<");
        System.out.println("_ Enter #1 to let us show you list of all flights ");
        System.out.println("_ Enter #2 to search flights");
        System.out.println("_ Enter #3 to let us show you your flights");
        System.out.println("_ Enter #4 to log out");
        System.out.println();
        System.out.println("Do you want to back to the first menu ? Enter #0 !!");
        System.out.println(">>>>>>-----------------------------------<<<<<<");

        input = inputDe.next() ;
        switch (input){
            case "1": // کاربر تقاضای نمایش لیست پرواز ها را دارد
                flightsOfSite.showList(); // متد مربوطه در کلاسی که در کانستراکتور نیو کرده ایم
                System.out.println("press 0 to go to guidance panel ;)");
                String a = inputDe.next() ;
                switch (a){
                    case "0" :
                        guidance_Panel();
                        break;
                    default:
                        System.out.println("try again :( ");
                        guidance_Panel();
                }
                break;
            case "2" :// کاربر تقاضای سرچ پرواز را دارد
                flightsOfSite.searchFlights();
                if (flightsOfSite.getIndex() == -5){ // وقتی مقدار این متغیر -5 باشد ینی پرواز مدنظر وجود ندارد
                    System.out.println();
                    System.out.println(">>>>>>------------------------------------------------<<<<<<");
                    System.out.println();
                    guidance_Panel();
                }else{
                    System.out.println("------ press + to buy // press 0 to go to guidance panel ------");
                    String in2 = inputDe.next() ;
                    menu2(in2);
                }
                break;
            case "3" :// کاربر تقاضای دیدن لیستی از پرواز هایی که خریده را دارد
            //طرز کار : کاربری که الان میخواهد لیست پرواز های خود را ببیند در مرحله قبل یا لاگین کرده و یا ثبت نام، پس در این دو مرحله اطلاعات وارد شده را در دو استرینگ متمایز میگذاریم
            //حال در فایل باید جست و جو کرد تا کاربری با رمزهایی که در دو استرینگ ذخیره کردیم را پیدا کنیم ، ان را بخوانیم و ماتریس مربوط به پرواز های خریداری شده را برای کاربر به نمایش
            //بگذاریم.
            // متغیر counter31 اگر صفر باقی ماند ینی کاربر تا به حال پروازی خریداری نکرده پس پیامی مبنی برین ، چاپ میکنیم

                int counter31 = 0 ;
                userin3 = userOfSite.getUserName() ;
                passin3 = userOfSite.getPassWord() ;
                for (int i = 0; i < userOfSite.getUsers().length() / 2080 ; i++) {
                    userOfSite.getUsers().seek(i * 2080);
                    userOfSite.readInfoFromFileOfUsers();
                    if (userOfSite.getUserName().equals(userin3)){
                        if (userOfSite.getPassWord().equals(passin3)){
                            for (int j = 0; j < 10; j++) {
                                if (userOfSite.getReserved_Flights()[j][1].equals("@") == false){
                                    counter31 ++ ;
                                    for (int k = 0; k < 5; k++) {
                                        System.out.printf("%20s" , userOfSite.getReserved_Flights()[j][k]);
                                    }
                                    System.out.println();
                                }
                            }
                        }
                    }

                }
                if (counter31 == 0){
                    System.out.println(">>>>>>---- you don't have any reserved flights :( ----<<<<<<");
                    System.out.println("\n");
                }
                    guidance_Panel();
                break;
            case "4" :// کاربر تقاضای لاگ اوت دارد
                welcome_menu();
                break;
            case "0" :// در هر مرحله با فشردن 0 به به منوی اولیه میرود
                welcome_menu();
                break;
            default :
                System.out.println("your input is not acceptable !!!!");
                guidance_Panel();
        }
    }
    public void menu2(String in) throws IOException { // این متد زمانی فراخوانی میشود که بعد از سرچ پرواز، کاربر یا باید پرواز را با فشردن + بخرد یا با فشردن 0 به منوی راهنما برود
        int indexOfPlus = -1 ;
        switch (in){
            case "0" :
                guidance_Panel();
                break;
            case "+" :
                // طرز کار : فایل یوزرز را میخوانیم تا رکوردی که هم خوانی با رمزهای مراحل لاگین یا ثبت نام دارد را پیدا کنیم
                // وقتی رکورد پیدا شد جای ان در فایل را به خاطر میسپاریم و از حلقه بیرون می اییم
                //به صورت پیشفرض خانه های ماتریس مربوط به پروازهای خریداری شده با @ پر میشود
                // پس از خانه ای شروع به پر کردن ماتریس میگنیم که با @ پر شده باشد
                // همچنین برای نمایش پروازهای خریداری شده ، خانه هایی را نمایش میدهیک مه با @ پر نشده باشد

                for (int i = 0; i < userOfSite.getUsers().length() / 2080 ; i++) {
                    userOfSite.getUsers().seek(i * 2080);
                    userOfSite.readInfoFromFileOfUsers();
                    if (userOfSite.getUserName().equals(userin3)){
                        if (userOfSite.getPassWord().equals(passin3)){
                            index2 = i * 2080 ;
                            break;
                        }
                    }
                }
                for (int i = 0; i < 10; i++) {
                    if (userOfSite.getReserved_Flights()[i][1].equals("@") == false){
                        continue;
                    }else{
                        indexOfPlus = i ;
                        break;
                    }
                }
                int r1 = index2 + 80 + (indexOfPlus * 200) ;
                userOfSite.getUsers().seek(r1) ;
                for (int i = 0; i < 5 ; i++) {
                    userOfSite.getUsers().writeChars(userOfSite.fixSize(flightsOfSite.getFinalFlight()[i]));
                }
                guidance_Panel();
                break;
            default :
                System.out.println("i can't understand :( ");
                System.out.println("try again !!!") ;
                guidance_Panel();
        }
    }

}
