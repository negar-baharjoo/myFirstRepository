package AirLineWithFiles;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Users_Part {
    private String userName;//40  file.seek(0)
    private String passWord;//40  file.seek(40)
    private String[][] reserved_Flights = new String[10][5];//5 * 10 = 50 _ 50 * 40 = 2000  file.seek(80)
    private RandomAccessFile users ;

    public Users_Part() throws FileNotFoundException {// در کانستراکتور ماتریس را با مقدار پبشفرض @ پر میکنم

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                reserved_Flights[i][j] = "@";
            }
        }
        users = new RandomAccessFile("users.dat", "rw");// در کانستراکتور نیو میکنیم
    }

    public Users_Part(String userName, String passWord) throws FileNotFoundException { // کانستراکتوری دیگر
        this.userName = userName;
        this.passWord = passWord;
        this.users = new RandomAccessFile("users.dat", "rw");
        String[][] reserved = new String[10][5];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                reserved[i][j] = "@";
            }
        }
        this.reserved_Flights = reserved;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String[][] getReserved_Flights() {
        return reserved_Flights;
    }

    public void setReserved_Flights(String[][] reserved_Flights) {
        this.reserved_Flights = reserved_Flights;
    }

    public RandomAccessFile getUsers() {
        return users;
    }

    public void setUsers(RandomAccessFile users) {
        this.users = users;
    }

    public void writeInfoInFileOfUsers() throws IOException {// متدی برای نوشتن رکورد ها روی فایل
        users.seek(users.length());
        users.writeChars(fixSize(this.userName));
        users.writeChars(fixSize(this.passWord));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                users.writeChars(fixSize("@"));
            }
        }
    }

    public String fixSize(String name) {
        while (name.length() < 20) {
            name += " ";
        }
        return name;
    }

    public String exitFromFix() throws IOException {
        String name = "";
        for (int i = 0; i < 20; i++) {
            name += users.readChar();
        }
        return name.trim();
    }

    public void readInfoFromFileOfUsers() throws IOException {// متدی برای خواندن رکورد از روی فایل
        String[][] matrix = new String[10][5];
        this.userName = exitFromFix();
        this.passWord = exitFromFix();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = exitFromFix();
            }
        }
        this.reserved_Flights = matrix;
    }

}