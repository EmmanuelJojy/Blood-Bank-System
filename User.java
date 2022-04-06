import java.io.*;

public class User implements Serializable {
    String userid;
    String password;

    public User(String userid, String password) {
        this.userid = userid;
        this.password = password;
    }

    public String toString() {
        return "Userid = " + this.userid + ", Password = " + this.password;
    }

    public void serialize() {
        try {
            ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream("serial.ser"));
            outStream.writeObject(this);
            outStream.close();
        } catch (Exception e) {
            // System.out.println("# SerializeException - " + e);
            // System.exit(0);
        }
    }

    public static User DeSerialize() {
        User user = null;
        try {
            ObjectInputStream inStream = new ObjectInputStream(new FileInputStream("serial.ser"));
            user = (User) inStream.readObject();
            inStream.close();
        } catch (FileNotFoundException e) {
            // Skip DeSerialization
        } catch (Exception e) {
            // System.out.println("# SerializeException - " + e);
            // System.exit(0);
        }
        return user;
    }
}
